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
        String items[] = {
            "area", "button", "button.dark", "button.green", "button.home", "button.load",
            "button.load:selected", "button.red", "chat", "chat.timing", "chat.red", "chat.blue",
            "chat.yellow", "description", "greenHover", "input", "label", "label.light",
            "label.darker", "redHover", "select.item", "select.selected", "table", "table.td",
            "table.th", "title", "title.light", "window"
        };

        components = new HashMap<String, ComponentStyle>();
        
        for(int i=0; i < items.length; i++) {
            components.put(items[i], new ComponentStyle());
        }
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
