package converter;

import java.util.ArrayList;
import java.util.List;

public class Element {
    private final String name;
    private final String path;
    private String value;
    private final List<String> attributes;
    private final List<Element> children;
    private boolean isArray;

    public Element(Element root, String name, String value, boolean isArray) {
        this.name = name.matches("element\\(\\d+\\)") ? "element" : name;
        this.path = (root == null || root.getPath().equals("")) ? name : root.getPath() + ", " + name;
        this.value = formatValue(value);
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        this.isArray = isArray;
    }

    public Element(Element root, String name, Boolean isArray) {
        this(root, name, "", isArray);
    }

    public Element(String name) {
        this(null, name, null, false);
    }

    public boolean isArray() {
        return isArray;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }

    public void addChild(Element child) {
        this.children.add(child);
        this.isArray = this.children.size() > 1
                && this.children.stream().allMatch(c -> c.getName().equals(child.getName()));
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<Element> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!this.path.isEmpty()) {
            stringBuilder.append("Element:\n");
            stringBuilder.append(String.format("path = %s%n", this.path));
            if (isLeaf()) {
                stringBuilder.append(String.format("value = %s%n", this.value));
            }
            if (this.attributes.size() > 0) {
                stringBuilder.append("attributes:\n");
                for (String attribute : attributes) {
                    stringBuilder.append(attribute).append("\n");
                }
            }
            stringBuilder.append("\n");
        }
        for (Element element : children) {
            stringBuilder.append(element.toString());
        }
        return stringBuilder.toString();
    }

    private String formatValue(String value) {
        if (value != null) {
            value = value.matches("\".*\"|null") ? value : String.format("\"%s\"", value);
        }
        return value;
    }
}
