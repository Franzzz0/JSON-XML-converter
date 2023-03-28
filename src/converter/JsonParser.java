package converter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser implements Converter{
    private final String SIMPLE_VALUE = "(\"[\\w _.-]*\"|null|true|false|(?:\\d+\\.)?\\d+)";
    private final String SIMPLE_KEY = "\"((?:\\w+\\.)*[\\w _]*)\"";
    private final String OPTIONAL_PREFIX_KEY = "\"([@#]?(?:\\w+\\.)*[\\w _]*)\"";
    private final String PREFIX_KEY = "[@#](?:\\w+\\.)*[\\w _]+";
    private final String VALUE_KEY = "#((?:\\w+\\.)*[\\w _]+)";
    private final String ATTRIBUTE_KEY = "@((?:\\w+\\.)*[\\w _]+)";
    private final String EMPTY_BRACKETS = "\\{\\s*}|\\[\\s*]";

    private final Pattern simpleValue = Pattern
            .compile("\\{\\s*" + SIMPLE_VALUE + "\\s*}|\\{\\s*}|" + SIMPLE_VALUE);
    private final Pattern simpleKeyValue = Pattern
            .compile("\\{\\s*" + SIMPLE_KEY + ":\\s?" + SIMPLE_VALUE + "\\s*}");
    private final Pattern nodePattern = Pattern.compile(OPTIONAL_PREFIX_KEY + ":\\s?(?:" + SIMPLE_VALUE + "|([{\\[]))");
    private final Pattern arrayElementPattern = Pattern.compile("(" + SIMPLE_VALUE + ",?|([{\\[]))");

    @Override
    public String convert(String toConvert) {
        Element root = parse(toConvert);
        return ElementObjectParser.getXml(root);
    }

    private Element parse(String toConvert) {
        Element root = new Element("");
        recursiveParse(root, toConvert, toConvert.startsWith("["));
        return root;
    }

    private void recursiveParse(Element root, String content, boolean isArray) {
        if (isArray) content = content.substring(1);
        if (content.matches(simpleValue.pattern())) {
            root.setValue(getSimpleValue(content));
            return;
        }
        if (content.matches(simpleKeyValue.pattern())) {
            root.addChild(getSimpleElement(content, root));
        } else {
            Map<String, String> subKeys = new LinkedHashMap<>();
            int index = 0;
            while (true) {
                Matcher matcher = isArray ? arrayElementPattern.matcher(content) : nodePattern.matcher(content);
                if (matcher.find()) {
                    String key = isArray ? String.format("element(%d)", index) : matcher.group(1);
                    String subContent = matcher.group(2) != null
                            ? matcher.group(2)
                            : getSubContent(content.substring(matcher.start(3)));
                    subKeys.put(key, subContent);

                    int cutIndex = (Math.max(matcher.start(3), matcher.start(2))) + subContent.length();
                    content = content.substring(cutIndex);
                    index++;
                    continue;
                }
                break;
            }

            subKeys = correctKeys(subKeys, root);
            if (hasParentAttributes(subKeys.keySet())) {
                for (String key : subKeys.keySet()) {
                    if (key.matches(ATTRIBUTE_KEY)) {
                        root.addAttribute(formatAttribute(key, subKeys.get(key)));
                    } else if (key.matches(VALUE_KEY)) {
                        String value = subKeys.get(key);
                        boolean isSubContentArray = value.startsWith("[");
                        if (value.matches(SIMPLE_VALUE)) {
                            root.setValue(value);
                        } else {
                            recursiveParse(root, value, isSubContentArray);
                        }
                    }
                }
            } else {
                for (String key : subKeys.keySet()) {
                    String subContent = subKeys.get(key);
                    boolean isSubContentArray = subContent.startsWith("[");
                    Element element = new Element(root, key, isSubContentArray);
                    recursiveParse(element, subContent, isSubContentArray);
                    root.addChild(element);
                }
            }
        }
    }

    private static String formatAttribute(String key, String value) {
        value = value.equals("null") ? "\"\"" : value.matches("\".*\"") ? value : String.format("\"%s\"", value);
        return String.format("%s = %s", key.substring(1), value);
    }

    private boolean hasParentAttributes(Set<String> subKeys) {
        return !subKeys.isEmpty() && subKeys.stream().allMatch(k -> k.matches(PREFIX_KEY));
    }

    private Map<String, String> correctKeys(Map<String, String> map, Element root) {
        boolean attributes = map.keySet().stream().allMatch(k -> k.matches(PREFIX_KEY))
                && map.keySet().stream().anyMatch(k -> k.matches(VALUE_KEY));

        for (String key : map.keySet()) {
            if (key.matches("#.+") && !key.equals("#" + root.getName())
                    || key.matches("@.+") && !map.get(key).matches(SIMPLE_VALUE + "|" + EMPTY_BRACKETS)) {
                attributes = false;
            }
        }
        Map<String, String> correctedMap = new LinkedHashMap<>();

        for (String key : map.keySet()) {
            if (!key.matches("[@#]|")
                    && (!key.matches("[@#][\\w _]+")
                    || !map.containsKey(key.substring(1)))
            ) {
                if (!attributes) {
                    String newKey = key.replaceAll("[@#]", "");
                    correctedMap.put(newKey, map.get(key));
                } else {
                    String value = map.get(key);
                    if (value.matches(EMPTY_BRACKETS)) value = "";
                    correctedMap.put(key, value);
                }
            }
        }
        return correctedMap;
    }

    private String getSubContent(String toConvert) {
        int openBracket = 0;
        int closedBracket = 0;
        boolean isArray = toConvert.startsWith("[");
        Pattern bracket = Pattern.compile(isArray ? "[\\[\\]]" : "[{}]");
        Matcher matcher = bracket.matcher(toConvert);

        while (matcher.find()) {
            switch (matcher.group()) {
                case "{", "[" -> openBracket++;
                case "}", "]" -> closedBracket++;
            }
            if (openBracket == closedBracket) {
                return toConvert.substring(0, matcher.end());
            }
        }
        return toConvert;
    }

    private Element getSimpleElement(String content, Element root) {
        Matcher matcher = simpleKeyValue.matcher(content);
        if (matcher.matches()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            return new Element(root, key, value, false);
        }
        return null;
    }

    private String getSimpleValue(String content) {
        Matcher matcher = simpleValue.matcher(content);
        if (matcher.find()) {
            String value = (matcher.group(1) == null) ?
                    ((matcher.group(2) == null) ?
                            "\"\""
                            : matcher.group(2))
                    : matcher.group(1);
            return (value.equals("null") || value.matches("\".*\"")) ? value : String.format("\"%s\"", value);
        }
        return null;
    }
}
