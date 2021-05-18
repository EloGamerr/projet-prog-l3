package fr.prog.tablut.view.generic;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class GenericButton extends JPanel{
	private final JButton button;
	
	public GenericButton(String text) {
		this.button = new JButton(text);
		addButton();
	}
	
	public GenericButton(String text, ActionListener actionListener) {
		this.button = new JButton(text);
		this.button.addActionListener(actionListener);
		addButton();
	}
	
	public GenericButton(String text, Dimension defaultSize) {
		this.button = new JButton(text);
		this.button.setPreferredSize(defaultSize);
		addButton();
	}
	
	public GenericButton(String text, ActionListener actionListener, Dimension defaultSize) {
		this.button = new JButton(text);
		this.button.addActionListener(actionListener);
		this.button.setPreferredSize(defaultSize);
		addButton();
	}
	
	private void addButton() {
		this.add(button);
	}
}
