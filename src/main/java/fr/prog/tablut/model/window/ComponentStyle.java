package fr.prog.tablut.model.window;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ComponentStyle {
    protected HashMap<String, Color> properties = new HashMap<>() {{
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
        Iterator<String> it = properties.keySet().iterator();

        while(it.hasNext()) {
            String prop = it.next();
            set(prop, copy.get(prop));
        }
    }

    public void set(String property, Color value) {
        if(hasProperty(property))
            properties.put(property, value);
    }

    public boolean hasProperty(String property) {
        return properties.containsKey(property);
    }

    public Color get(String property) {
        if(hasProperty(property))
            return properties.get(property);
        return new Color(0, 0, 0);
    }

    @Override
    public String toString() {
        String str = "{";

        if(properties.size() > 0) {
            int i = 0;
            for(Entry<String, Color> prop : properties.entrySet()) {
                str += "\n  " + prop.getKey() + ": " + prop.getValue().toString().replace("java.awt.Color", "");
                if(++i < properties.size())
                    str += ",";
            }
            str += "\n";
        }

        str += "}";

        return str;
    }
}
