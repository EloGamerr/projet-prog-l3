package fr.prog.tablut.view.components;

import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericText;

/**
 * A component that extends GenericText, with title properties.
 * @see GenericText
 */
public class Title extends GenericText {
    AbstractBorder border = new EmptyBorder(0, 0, 20, 0);

    /**
     * Default constructor.
     * <p>Creates a title with given text, and default font size for a title.</p>
     * @param text The title's text
     */
    public Title(String text) {
        super(text, 100);
		setBorder(border);
    }

    /**
     * Creates a title with given text, and given font size.
     * @param text The title's text
     * @param fontSize The title's font size
     */
    public Title(String text, int fontSize) {
        super(text, fontSize);
		setBorder(border);
    }
}
