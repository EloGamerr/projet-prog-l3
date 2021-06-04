package fr.prog.tablut.view.components.generic;

/**
 * A component that extends a GenericPanel just to put a label inside.
 * <p>It's to differenciate a label than a text.</p>
 * @see GenericLabel
 * @see GenericPanel
 */
public class GenericText extends GenericPanel {
    protected GenericLabel label;

    /**
     * Creates a GenericText with given text and the default text's font size.
     * @param text The text to set
     */
    public GenericText(String text) {
        this(text, 15);
    }
    
    /**
     * Creates a GenericText with given text and at the given font size.
     * @param text The text to set
     * @param fontSize The font size to apply on the text
     */
    public GenericText(String text, int fontSize) {
        super();
        label = new GenericLabel(text, fontSize);
        init();
    }

    /**
     * Initializes the label (GenericLabel) and disable the default background drawing
     * @see GenericLabel
     */
    private void init() {
        add(label);
    }

    /**
     * Sets the text
     * @param text The text to set
     */
    public void setText(String text) {
        label.setText(text);
    }
}
