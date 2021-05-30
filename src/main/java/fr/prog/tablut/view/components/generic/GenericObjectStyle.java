package fr.prog.tablut.view.components.generic;

import java.awt.Color;

import fr.prog.tablut.model.window.Style;
import fr.prog.tablut.view.GlobalWindow;

public abstract class GenericObjectStyle {
    protected static Style styleRef;
	protected static GlobalWindow globalWindow;
    
    protected String name = "object";

    public static void setStyle(Style styleRef) {
        GenericObjectStyle.styleRef = styleRef;
    }

    public static Style getStyle() {
        return GenericObjectStyle.styleRef;
    }

    /**
	 * Defines the main window
	 * @param globalWin The main window
	 */
    public static void setGlobalWindow(GlobalWindow globalWin) {
		GenericObjectStyle.globalWindow = globalWin;
	}

	/**
	 * Returns the main window
	 * @return The main window
	 */
	public static GlobalWindow getGlobalWindow() {
		return GenericObjectStyle.globalWindow;
	}

    public static Color getProp(String componentName, String property) {
        if(GenericObjectStyle.styleRef.has(componentName) && GenericObjectStyle.styleRef.get(componentName).hasProperty(property))
            return GenericObjectStyle.styleRef.get(componentName).get(property);
        
        return null;
    }
}
