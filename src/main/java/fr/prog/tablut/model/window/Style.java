package fr.prog.tablut.model.window;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public class Style {
    // Component style for something in the window
    // it can be the window itself, a button, personalized buttons etc...
    private HashMap<String, ComponentStyle> components;

    public Style() {
        reset();
    }

    public boolean has(String component) {
        return components.containsKey(component);
    }

    public ComponentStyle get(String component) {
        return components.get(component);
    }

    public void set(String componentName, ComponentStyle component) {
        String name = (componentName.charAt(0) == '.')? componentName.substring(1) : componentName;
        components.put(name, component);
    }

    public void reset() {
        components = new HashMap<>(){{
            put("window", new ComponentStyle());
            put("button", new ComponentStyle());
            put("button.red", new ComponentStyle());
            put("button.green", new ComponentStyle());
            put("button:hover", new ComponentStyle());
            put("button.green:hover", new ComponentStyle());
            put("button.red:hover", new ComponentStyle());
            put("label", new ComponentStyle());
        }};
    }

    public Set<Entry<String, ComponentStyle>> entrySet() {
        return components.entrySet();
    }
}
