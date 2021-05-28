package fr.prog.tablut.model.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import fr.prog.tablut.model.Loader;

/**
 * stores the configuration of a Window
 */
public class WindowConfig {
    protected final Loader loader = new Loader();

    // project name
    public String projectName = "";

    // window dimension
    public int width = 0;
    public int height = 0;

    // possible resolutions of the window
    // we keep basic HD screen resolutions
    public int resolutions[][] = {
        { 640,  360 },
        { 1024, 576 },
        { 1280, 720 },
        { 1600, 900 },
        { 1920, 1080 }
    };

    // Component style for something in the window
    // it can be the window itself, a button, personalized buttons etc...
    public HashMap<String, ComponentStyle> components = new HashMap<>() {{
        put("window", new ComponentStyle());
        put("button", new ComponentStyle());
        put("greenButton", new ComponentStyle());
        put("redButton", new ComponentStyle());
    }};
    
    /**
     * Default constructor
     */
    public WindowConfig() {

    }

    /**
     * Loads configuration from a file
     * @param filepath The file path to the config file to load
     * @throws ParseException
     */
    public WindowConfig(String filepath) throws ParseException {
        setConfig(filepath);
    }

    /**
     * Loads configuration from JSONObject
     * @param json The JSONObject
     */
    public WindowConfig(JSONObject json) {
        setConfig(json);
    }

    /**
     * Copies the configuration from another WindowConfig
     * @param config Another WindowConfig to copy its configuration
     */
    public WindowConfig(WindowConfig config) {
        setConfig(config);
    }

    /**
     * convert the file at given path to a JSONObject and loads its configuration
     * @param filepath The file path
     * @throws ParseException
     */
    public void setConfig(String filepath) throws ParseException {
        setConfig(loader.getJSON(filepath));
    }

    /**
     * Copies the configuration from another WindowConfig
     * @param config The WindowConfig to copy
     */
    public void setConfig(WindowConfig config) {
        components = new HashMap<>();

        for(Map.Entry<String, ComponentStyle> item : config.components.entrySet()) {
            String key = item.getKey();
            ComponentStyle value = item.getValue();

            components.put(key, new ComponentStyle(value));
        }
    }

    /**
     * 
     * <p>Loads the configuration from a JSONObject.</p>
     * <p>The JSONObject must have some keys :</p>
     * <pre>
     * {
     *  "window": {},
     *  "components": {}
     * }
     * </pre>
     * <p>It will check for each components and for the window if it has the two fields
     * "background" and "color", then create a ComponentStyle for it in this case.</p>
     * <p>For the window, it will also check for its size. If it does not fit the screen size
     * or basic resolutions, then it will resize the window to not overflow the screen and fit
     * the required dimension.</p>
     * @param json
     */
    public void setConfig(JSONObject json) {
        JSONObject o;

        if(!json.isNull("project")) {
            o = json.getJSONObject("project");

            if(!o.isNull("name")) {
                projectName = o.getString("name");
            }
        }

        // window section
        if(!json.isNull("window")) {
            getComponentStyleFromJSONObject(json, "window");
            o = json.getJSONObject("window");

            // screen dimensions
            Dimension winDim = Toolkit.getDefaultToolkit().getScreenSize();
            int w = (int)winDim.getWidth();
            int h = (int)winDim.getHeight();

            // check if it has dimension keys
            if(!o.isNull("width") && !o.isNull("height")) {
                width = o.getInt("width");
                height = o.getInt("height");

                // if it overflows the screen, check for available resolutions
                // that fit the screen
                if(width > w || height > h) {
                    int l = resolutions.length - 1;
                    int i = l;

                    if(i > -1) {
                        int res[] = resolutions[i];

                        while(i > -1 && (w < res[0] || h < res[1])) {
                            res = resolutions[--i];
                        }

                        width = res[0];
                        height = res[1];
                    }
                }
            }
        }
        else {
            System.out.println("[Warning] WindowConfig::setConfig : window section not found in the json.");
        }

        // ensure to not be below the minimum required dimension
        int minX = 1, minY = 1;

        if(resolutions.length > 0) {
            minX = resolutions[0][0];
            minY = resolutions[0][1];
        }

        if(width < minX || height < minY) {
            width = minX;
            height = minY;
        }


        // components section
        if(!json.isNull("components")) {
            o = json.getJSONObject("components");

            Iterator<?> keys = o.keys();

            // iterate through components
            while(keys.hasNext()) {
                String key = (String)keys.next();
                String keyName = key.substring(0, 1).toUpperCase() + key.substring(1);

                // get a component
                if(o.get(key) instanceof JSONObject) {
                    getComponentStyleFromJSONObject(o, key);

                    JSONObject component = o.getJSONObject(key);
                    Iterator<?> subkeys = component.keys();

                    // get a stylized form of the component / not the default
                    while(subkeys.hasNext()) {
                        String subkey = (String)subkeys.next();
                        
                        if(component.get(subkey) instanceof JSONObject) {
                            getComponentStyleFromJSONObject(component, subkey, subkey + keyName);
                        }
                    }
                }
            }
        }
        else {
            System.out.println("[Warning] WindowConfig::setConfig : components section not found in the json.");
        }
    }

    /**
     * <p>Check if a given JSONObject has the required fields to create its ComponentStyle.</p>
     * <p>If then, it will append this newly created ComponentStyle to the WindowConfig.components</p>
     * @param json The JSONObject
     * @param key The component key and name
     */
    protected void getComponentStyleFromJSONObject(JSONObject json, String key) {
        getComponentStyleFromJSONObject(json, key, key);
    }

    /**
     * <p>Check if a given JSONObject has the required fields to create its ComponentStyle.</p>
     * <p>If then, it will append this newly created ComponentStyle to the WindowConfig.components</p>
     * @param json The JSONObject
     * @param key The component key
     * @param componentName The component name to set
     */
    protected void getComponentStyleFromJSONObject(JSONObject json, String key, String componentName) {
        if(json.isNull(key))
            return;

        JSONObject o = json.getJSONObject(key);

        if(o.isNull("background") || o.isNull("color"))
            return;
        
        Color bg, col;

        bg = loader.getColorFromArray(o.getJSONArray("background"));
        col = loader.getColorFromArray(o.getJSONArray("color"));

        setComponentStyle(componentName, new ComponentStyle(bg, col));
    }

    /**
     * Modifies or creates a ComponentStyle in its components HashMap.
     * @param name The component's name
     * @param component The component's object
     */
    protected void setComponentStyle(String name, ComponentStyle component) {
        if(components.containsKey(name)) {
            components.get(name).set(component);
        }

        else {
            components.put(name, component);
        }
    }
}
