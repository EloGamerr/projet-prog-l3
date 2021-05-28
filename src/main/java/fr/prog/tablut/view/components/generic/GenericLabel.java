package fr.prog.tablut.view.components.generic;

import java.awt.Font;

import javax.swing.JLabel;

public class GenericLabel extends JLabel {
	
	public GenericLabel(String text,String fontFamily, int size) {
		super(text);
		setFont(new Font(fontFamily, Font.PLAIN, size));
	}
	
	public GenericLabel(String text, int size) {
		super(text);
		setFont(new Font("Farro", Font.PLAIN, size));
	}
}
