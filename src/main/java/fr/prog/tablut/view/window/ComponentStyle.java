package fr.prog.tablut.view.window;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A component storing color style rules
 * @see Color
 */
public class ComponentStyle {
    protected final HashMap<String, Color> properties = new HashMap<String, Color>() {{
        put("background", new Color(255, 255, 255));
        put("borderColor", new Color(0, 0, 0));
        put("color", new Color(0, 0, 0));
    }};

    /**
     * Default constructor
     */
    public ComponentStyle() {}

    /**
     * Creates a ComponentStyle with given background and color
     * @param bg The background color
     * @param clr The foreground color
     */
    public ComponentStyle(Color bg, Color clr, Color borderClr) {
        set(bg, clr, borderClr);
    }

    /**
     * Copies the style of another component
     * @param copy The ComponentStyle to copy
     */
    public ComponentStyle(ComponentStyle copy) {
        set(copy);
    }

    /**
     * Sets the background and foreground color
     * @param bg The background color
     * @param clr The foreground color
     */
    public void set(Color bg, Color clr, Color borderClr) {
        set("background", bg);
        set("color", clr);
        set("borderColor", borderClr);
    }

    /**
     * Copies the style of another component
     * @param copy The ComponentStyle to copy
     */
    public void set(ComponentStyle copy) {

        for (String prop : properties.keySet()) {
            set(prop, copy.get(prop));
        }
    }

    /**
     * Sets the given color value to the given property if the property exists.
     * @param property The property to change
     * @param value The value to set
     */
    public void set(String property, Color value) {
        if(hasProperty(property))
            properties.put(property, value);
    }

    /**
     * Returns either it has the given property or not
     * @param property The property to check its existence
     * @return Either the property exists or not
     */
    public boolean hasProperty(String property) {
        return properties.containsKey(property);
    }

    /**
     * Returns the value of the given property if it exists, black color otherwise
     * @param property The property to get its value
     * @return The property's value
     */
    public Color get(String property) {
        if(hasProperty(property))
            return properties.get(property);
        return new Color(0, 0, 0);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        if(properties.size() > 0) {
            int i = 0;
            for(Entry<String, Color> prop : properties.entrySet()) {
                str.append("\n  ").append(prop.getKey()).append(": ").append(prop.getValue().toString().replace("java.awt.Color", ""));
                if(++i < properties.size())
                    str.append(",");
            }
            str.append("\n");
        }

        str.append("}");

        return str.toString();
    }
}
