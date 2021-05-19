package fr.prog.tablut.view.generic;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class GenericButton extends JButton{
	
	public GenericButton(String text) {
		super(text);
		//addButton();
	}
	
	public GenericButton(String text, ActionListener actionListener) {
		super(text);
		this.addActionListener(actionListener);
		//addButton();
	}
	
	public GenericButton(String text, Dimension defaultSize) {
		super(text);
		this.setPreferredSize(defaultSize);
		//addButton();
	}
	
	public GenericButton(String text, ActionListener actionListener, Dimension defaultSize) {
		super(text);
		this.addActionListener(actionListener);
		this.setPreferredSize(defaultSize);
		//addButton();
	}
}
