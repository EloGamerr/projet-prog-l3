package fr.prog.tablut.view.components.generic;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * A component that extends a JLabel, a basic label
 * @see JLabel
 */
public class GenericLabel extends JLabel {
	/**
	 * Creates a generic label with given text at given size
	 * @param text The text of the label
	 * @param size The font size
	 */
	public GenericLabel(String text, int size) {
		super(text);
		init(null, size);
	}

	/**
	 * Creates a generic label with given text at given size with given font family
	 * @param text The text of the label
	 * @param fontFamily The font family
	 * @param size The font size
	 */
	public GenericLabel(String text, String fontFamily, int size) {
		super(text);
		init(fontFamily, size);
	}

	/**
	 * Initializes the label with the given font family and given size.
	 * <p>Also sets the foreground color.</p>
	 * @param fontFamily The font family
	 * @param size The font size
	 */
	private void init(String fontFamily, int size) {
		if(fontFamily == null)
			fontFamily = "Farro";

		setFont(new Font(fontFamily, Font.PLAIN, size));
        setForeground(GenericObjectStyle.getProp("label", "color"));
	}
}
