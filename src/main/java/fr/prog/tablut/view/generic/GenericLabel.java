package fr.prog.tablut.view.generic;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GenericLabel extends JPanel{
	private final JLabel label;
	public GenericLabel(String text,String fontFamily, int size) {
		this.label = new JLabel(text);
		this.label.setFont(new Font(fontFamily, Font.PLAIN, size));
		this.add(label);
	}
	
	public GenericLabel(String text, int size) {
		this.label = new JLabel(text);
		this.label.setFont(new Font("Farro", Font.PLAIN, size));
		this.add(label);
	}
}
