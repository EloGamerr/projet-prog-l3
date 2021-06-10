package fr.prog.tablut.view.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import fr.prog.tablut.model.Loader;

/**
 * stores the configuration of a Window
 */
public class WindowConfig {
    // project name
    public String projectName = "";

    // possible resolutions of the window
    // we keep basic HD screen resolutions
    public final int[][] resolutions = {
        { 640,  360 },
        { 1024, 576 },
        { 1280, 720 },
        { 1600, 900 },
        { 1920, 1080 }
    };

    // window dimension
    public int windowWidth = resolutions[1][0];
    public int windowHeight = resolutions[1][1];

    final Style style = new Style();
    
    /**
     * Default constructor
     */
    public WindowConfig() {

    }

    /**
     * Loads configuration from a file
     * @param filepath The file path to the config file to load
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
    @SuppressWarnings("CopyConstructorMissesField")
    public WindowConfig(WindowConfig config) {
        setConfig(config);
    }

    /**
     * convert the file at given path to a JSONObject and loads its configuration
     * @param filepath The file path
     */
    public void setConfig(String filepath) throws ParseException {
        setConfig(Loader.getJSON(filepath));
    }

    /**
     * Copies the configuration from another WindowConfig
     * @param config The WindowConfig to copy
     */
    public void setConfig(WindowConfig config) {
        style.reset();

        for(Map.Entry<String, ComponentStyle> item : config.style.entrySet()) {
            String key = item.getKey();
            ComponentStyle value = item.getValue();

            style.set(key, new ComponentStyle(value));
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
     */
    @SuppressWarnings("ConstantConditions")
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
                windowWidth = o.getInt("width");
                windowHeight = o.getInt("height");

                // if it overflows the screen, check for available resolutions
                // that fit the screen
                if(windowWidth > w || windowHeight > h) {
                    int i = resolutions.length - 1;

                    if(i > -1) {
                        int[] res = resolutions[i];

                        while(i > -1 && (w < res[0] || h < res[1])) {
                            res = resolutions[--i];
                        }

                        windowWidth = res[0];
                        windowHeight = res[1];
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

        if(windowWidth < minX || windowHeight < minY) {
            windowWidth = minX;
            windowHeight = minY;
        }


        // components section
        if(!json.isNull("components")) {
            o = json.getJSONObject("components");

            Iterator<?> keys = o.keys();

            // iterate through components
            while(keys.hasNext()) {
                String key = (String)keys.next();

                // get a component
                if(o.get(key) instanceof JSONObject) {
                    getComponentStyleFromJSONObject(o, key);

                    JSONObject component = o.getJSONObject(key);
                    Iterator<?> subkeys = component.keys();

                    // get a stylized form of the component / not the default
                    while(subkeys.hasNext()) {
                        String subkey = (String)subkeys.next();

                        if(subkey.charAt(0) != '.' && subkey.charAt(0) != ':')
                            continue;

                        String subName = key + subkey;
                        
                        if(component.get(subkey) instanceof JSONObject) {
                            getComponentStyleFromJSONObject(component, subkey, subName);

                            // pseudo-classes
                            JSONObject subComponent = component.getJSONObject(subkey);
                            Iterator<?> subsubkeys = subComponent.keys();

                            // get a stylized form of the component / not the default
                            while(subsubkeys.hasNext()) {
                                String subsubkey = (String)subsubkeys.next();
                                String subsubName = subName + subsubkey;
                                
                                if(subsubkey.charAt(0) == ':' && subComponent.get(subsubkey) instanceof JSONObject) {
                                    getComponentStyleFromJSONObject(subComponent, subsubkey, subsubName);
                                }
                            }
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

        Iterator<?> it = o.keys();
        boolean hasAtLeastOneProp = false;
        ComponentStyle cpnt = new ComponentStyle();
        
        while(it.hasNext()) {
            String k = (String)it.next();

            if(cpnt.hasProperty(k)) {
                hasAtLeastOneProp = true;
                Color color = Loader.getColorFromArray(o.getJSONArray(k));
                cpnt.set(k, color);
            }
        }

        if(hasAtLeastOneProp)
            setComponentStyle(componentName, cpnt);
    }

    /**
     * Modifies or creates a ComponentStyle in its components HashMap.
     * @param name The component's name
     * @param component The component's object
     */
    protected void setComponentStyle(String name, ComponentStyle component) {
        style.set(name, component);
    }

    /**
     * Returns either the config's stylesheet has a component or not
     * @param componentName The ComponentStyle to check its existence
     * @return The existence of the component
     */
    public boolean hasComp(String componentName) {
        return style.has(componentName);
    }

    /**
     * Returns the ComponentStyle object of a given component name
     * @param componentName The component name
     * @return The ComponentStyle object
     */
    public ComponentStyle getComp(String componentName) {
        return style.get(componentName);
    }

    /**
     * Returns the config's stylesheet
     * @see Style
     * @return The stylesheet
     */
    public Style getStyle() {
        return style;
    }
}
