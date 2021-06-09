package fr.prog.tablut.view.components;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericObjectStyle;

/**
 * A component that extends JLabel, with title properties.
 * @see JLabel
 */
public class Title extends JLabel {

    /**
     * Default constructor.
     * <p>Creates a title with given text, and default font size for a title.</p>
     * @param text The title's text
     */
    public Title(String text) {
        this(text, 100);
    }

    /**
     * Creates a title with given text, and given font size.
     * @param text The title's text
     * @param fontSize The title's font size
     */
    public Title(String text, int fontSize) {
        super(text);
        AbstractBorder border = new EmptyBorder(0, 0, 20, 0);
        setBorder(border);
        setFont(new Font("Farro", Font.BOLD, fontSize));
        setForeground(GenericObjectStyle.getProp("title", "color"));
    }
}