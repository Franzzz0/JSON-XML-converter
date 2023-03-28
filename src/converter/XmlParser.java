package converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements Converter{

    private final Pattern nodePattern = Pattern.compile("<(\\w*)(\\s+([^</>]*=[^</>]*)*)?>(?:</\\1>" +
            "|((?:(?!</?\\1>).)+|(?:(?:(?!</?\\1>).)*<\\1>(?:(?!</?\\1>).)*</\\1>(?:(?!</?\\1>).)*)*)</\\1>)" +
            "|<(\\w*)(\\s+([^</>]*=[^</>]*)*)?/>");
    @Override
    public String convert(String toConvert) {
        Element root = parse(toConvert);
        return ElementObjectParser.getJson(root);
    }

    private Element parse(String toConvert) {
        Element root = new Element("");
        Matcher matcher = nodePattern.matcher(toConvert);
        while (matcher.find()) {
            recursiveParse(root, matcher.group());
            toConvert = toConvert.replace(matcher.group(), "");
        }
        return root;
    }

    private void recursiveParse(Element root, String content) {
        Matcher matcher = nodePattern.matcher(content);
        Element element;
        while(matcher.find()) {
            String name;
            String subContent;
            if (matcher.group(1) == null) {
                name = matcher.group(5);
                subContent = null;
            } else {
                name = matcher.group(1);
                subContent = matcher.group(4) == null ? "" : matcher.group(4);
            }
            List<String> attributes = getAttributes(matcher.group(2) == null ? matcher.group(6) : matcher.group(2));

            if (subContent == null || subContent.matches("[^<>]*")) {
                element = new Element(root, name, subContent, false);
            } else {
                element = new Element(root, name, false);
                recursiveParse(element, subContent);
            }
            for (String attribute : attributes) {
                element.addAttribute(attribute);
            }
            root.addChild(element);
        }
    }

    private List<String> getAttributes(String group) {
        List<String> attributes = new ArrayList<>();
        if (group == null) return attributes;
        Pattern attributesPattern = Pattern.compile("\\s+(\\w*)\\s*=\\s*(\"[\\w.]*\"|'[\\w.]*')");
        Matcher matcher = attributesPattern.matcher(group);

        while (matcher.find()) {
            attributes.add(String.format("%s = %s"
                    , matcher.group(1)
                    , matcher.group(2).replaceAll("'", "\"")));
        }
        return attributes;
    }
}
