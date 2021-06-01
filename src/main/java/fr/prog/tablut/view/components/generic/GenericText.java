package fr.prog.tablut.view.components.generic;

import javax.swing.JPanel;

/**
 * A component that extends a JPanel just to put a label inside.
 * <p>It's to differenciate a label than a text.</p>
 * @see GenericLabel
 * @see JPanel
 */
public class GenericText extends JPanel {
    protected GenericLabel label;
    protected int fontSize = 15;

    /**
     * Creates a GenericText with given text and the default text's font size.
     * @param text The text to set
     */
    public GenericText(String text) {
        label = new GenericLabel(text, fontSize);
        init();
    }
    
    /**
     * Creates a GenericText with given text and at the given font size.
     * @param text The text to set
     * @param fontSize The font size to apply on the text
     */
    public GenericText(String text, int fontSize) {
        label = new GenericLabel(text, fontSize);
        init();
    }

    /**
     * Initializes the label (GenericLabel) and disable the default background drawing
     * @see GenericLabel
     */
    private void init() {
        add(label);
		setOpaque(false);
    }

    /**
     * Sets the text
     * @param text The text to set
     */
    public void setText(String text) {
        label.setText(text);
    }
}
