package fr.prog.tablut.view.components.generic;

import java.awt.Color;

import fr.prog.tablut.model.window.Style;
import fr.prog.tablut.view.GlobalWindow;

/**
 * static abstract class for all Generic components.
 * <p>It stores the global window reference, and the stylesheet reference</p>
 * @see Style
 * @see GlobalWindow
 */
public abstract class GenericObjectStyle {
    protected static Style styleRef;
	protected static GlobalWindow globalWindow;
    
    /**
     * Sets the stylesheet reference
     * @see Style
     * @param styleRef The stylesheet reference
     */
    public static void setStyle(Style styleRef) {
        GenericObjectStyle.styleRef = styleRef;
    }

    /**
     * Returns the stylesheet object reference
     * @see Style
     * @return The stylesheet object
     */
    public static Style getStyle() {
        return GenericObjectStyle.styleRef;
    }

    /**
	 * Defines the main window
     * @see GlobalWindow
	 * @param globalWin The main window
	 */
    public static void setGlobalWindow(GlobalWindow globalWin) {
		GenericObjectStyle.globalWindow = globalWin;
	}

	/**
	 * Returns the main window
     * @see GlobalWindow
	 * @return The main window
	 */
	public static GlobalWindow getGlobalWindow() {
		return GenericObjectStyle.globalWindow;
	}

    /**
     * Returns the property's value of a ComponentStyle in the Style stylesheet.
     * <p>If it does not exist, returns null</p>
     * @see ComponentStyle
     * @see Style
     * @param componentName
     * @param property
     * @return
     */
    public static Color getProp(String componentName, String property) {
        if(GenericObjectStyle.styleRef.has(componentName) && GenericObjectStyle.styleRef.get(componentName).hasProperty(property))
            return GenericObjectStyle.styleRef.get(componentName).get(property);
        
        return null;
    }
}
