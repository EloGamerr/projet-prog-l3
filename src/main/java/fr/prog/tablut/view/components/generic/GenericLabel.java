package fr.prog.tablut.view.components.generic;

import java.awt.Font;

import javax.swing.JLabel;

public class GenericLabel extends JLabel {
	public GenericLabel(String text, int size) {
		super(text);
		init(null, size);
	}

	public GenericLabel(String text, String fontFamily, int size) {
		super(text);
		init(fontFamily, size);
	}

	private void init(String fontFamily, int size) {
		if(fontFamily == null)
			fontFamily = "Farro";
		
		setFont(new Font(fontFamily, Font.PLAIN, size));
        setForeground(GenericObjectStyle.getProp("label", "color"));
	}
}
