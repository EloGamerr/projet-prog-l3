package fr.prog.tablut.model.window;

import java.awt.Color;

public class ComponentStyle {
    public Color background = new Color(255, 255, 255);
    public Color color = new Color(0, 0, 0);

    /**
     * Default constructor
     */
    public ComponentStyle() {}

    /**
     * Creates a ComponentStyle with given background and color
     * @param bg The background color
     * @param clr The foreground color
     */
    public ComponentStyle(Color bg, Color clr) {
        set(bg, clr);
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
    public void set(Color bg, Color clr) {
        background = bg;
        color = clr;
    }

    /**
     * Copies the style of another component
     * @param copy The ComponentStyle to copy
     */
    public void set(ComponentStyle copy) {
        set(copy.background, copy.color);
    }
}
