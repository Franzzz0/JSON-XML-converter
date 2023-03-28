package converter;

import java.util.List;
import java.util.Objects;

public class ElementObjectParser {

    public static String getJson(Element root) {
        return buildJson(root, "", true, false);
    }

    public static String getXml(Element root) {
        return buildXml(root, "");
    }

    private static String buildXml(Element element, String tabs) {
        StringBuilder attributes = new StringBuilder();
        String name = element.getName();
        String value = element.getValue();
        if (value != null) {
            value = value.replaceAll("\"", "");
        }
        element.getAttributes().forEach(a -> attributes.append(" ").append(a));
        if (name.isEmpty()) {
            if (element.getChildren().size() > 1) {
                name = "root";
            } else {
                return buildXml(element.getChildren().get(0), tabs);
            }
        }
        if (element.isLeaf()) {
            if (Objects.equals(value, "null")) {
                return String.format("%s<%s%s/>", tabs, name, attributes);
            } else {
                return String.format("%s<%s%s>%s</%s>", tabs, name, attributes, value, name);
            }
        } else {
            StringBuilder string = new StringBuilder(String.format("%s<%s%s>", tabs, name, attributes));
            element.getChildren().forEach(e -> string.append("\n").append(buildXml(e, tabs + "\t")));
            string.append(String.format("\n%s</%s>", tabs, name));
            return string.toString();
        }
    }

    private static String buildJson(Element element, String tabs, boolean isLast, boolean isArrayElement) {
        String name = element.getName();
        String value = element.getValue();
        StringBuilder string = new StringBuilder(String.format("%s", tabs));
        if (!isArrayElement) {
            string.append(String.format("\"%s\": ", name));
        }
        if (name == null || name.isBlank()) {
            string.delete(0, string.length() - 1);
        }

        if (element.isArray() && element.getAttributes().size() == 0) {
            string.append("[");
            appendChildren(element, tabs, string, true);
        } else if (element.getAttributes().size() == 0) {
            if (element.isLeaf()) {
                string.append(value);
            } else {
                string.append("{");
                appendChildren(element, tabs, string, false);
            }
        } else {
            string.append("{");
            appendAttributes(element, tabs, string);
            if (element.isLeaf()) {
                string.append(String.format("%n%s\t\"#%s\": %s%n%s}", tabs, name, value, tabs));
            } else {
                boolean isArray = element.isArray();
                String openBracket = isArray ? "[" : "{";
                string.append(String.format("%n%s\t\"#%s\": %s", tabs, name, openBracket));
                appendChildren(element, tabs + "\t", string, isArray);
                string.append(String.format("%n%s}", tabs));
            }
        }
        if (!isLast) string.append(",");
        return string.toString();
    }

    private static void appendChildren(Element element, String tabs, StringBuilder string, boolean isArray) {
        List<Element> children = element.getChildren();
        for (int i = 0; i < children.size(); i++) {
            string.append(String.format("%n%s",
                    buildJson(children.get(i), tabs + "\t", i == children.size() - 1, isArray)));
        }
        string.append(String.format("%n%s%s", tabs, isArray ? "]" : "}"));
    }

    private static void appendAttributes(Element element, String tabs, StringBuilder string) {
        element.getAttributes().forEach(e -> {
            String[] attribute = e.split("=");
            String attributeFormatted = String.format("\"@%s\": %s", attribute[0].trim(), attribute[1].trim());
            string.append(String.format("%n%s\t%s,", tabs, attributeFormatted));
        });
    }
}
