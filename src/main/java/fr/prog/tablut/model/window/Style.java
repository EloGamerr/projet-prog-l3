package fr.prog.tablut.model.window;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

/**
 * A stylesheet manager.
 * @see ComponentStyle
 */
public class Style {
    // Component style for something in the window
    // it can be the window itself, a button, personalized buttons etc...
    private HashMap<String, ComponentStyle> components;

    /**
     * Default constructor.
     * <p>Initializes all rules</p>
     * @see ComponentStyle
     */
    public Style() {
        reset();
    }

    /**
     * Returns either it has a given component or not
     * @param component The component to check its existence
     * @return Either the component exists or not
     */
    public boolean has(String component) {
        return components.containsKey(component);
    }

    /**
     * Returns the ComponentStyle object of the given component name
     * @param component The component to recover
     * @return The ComponentStyle object
     */
    public ComponentStyle get(String component) {
        return components.get(component);
    }

    /**
     * Sets the componentStyle for a given component
     * @param componentName The component name
     * @param component The componentStyle object to set
     */
    public void set(String componentName, ComponentStyle component) {
        String name = (componentName.charAt(0) == '.')? componentName.substring(1) : componentName;
        components.put(name, component);
    }

    /**
     * Sets every component to their default value
     */
    public void reset() {
        components = new HashMap<String, ComponentStyle>(){{
            put("window", new ComponentStyle());
            put("button", new ComponentStyle());
            put("button:hover", new ComponentStyle());
            put("button.red", new ComponentStyle());
            put("button.green", new ComponentStyle());
            put("button.green:disabled", new ComponentStyle());
            put("description", new ComponentStyle());
            put("area", new ComponentStyle());
            put("input", new ComponentStyle());
            put("button.home", new ComponentStyle());
            put("label", new ComponentStyle());
            put("redHover", new ComponentStyle());
            put("greenHover", new ComponentStyle());
            put("button.load", new ComponentStyle());
            put("button.load:selected", new ComponentStyle());
            put("table", new ComponentStyle());
            put("table.th", new ComponentStyle());
            put("table.td", new ComponentStyle());
        }};
    }

    /**
     * Get componentStyle entrySet
     * @see ComponentStyle
     * @return THe entrySet<String, ComponentStyle>
     */
    public Set<Entry<String, ComponentStyle>> entrySet() {
        return components.entrySet();
    }
}
