package fr.prog.tablut.view.generic;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.view.GlobalWindow;


public class GenericButton extends JButton{
	
	static public final ActionListener QUIT = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
	};
	static private GlobalWindow globalWindow;
	
	static public void setGlobalWindow(GlobalWindow globalWin) {
		GenericButton.globalWindow = globalWin;
	}
	
	static public GlobalWindow getGlobalWindow() {
		return GenericButton.globalWindow;
	}
	
	static public ButtonNavAdaptator GO (WindowName dest) {
		return new ButtonNavAdaptator(GenericButton.globalWindow, dest);
	}
	
	static public Dimension DIMENTION() {
		return new Dimension(250,30);
	}
	
	public GenericButton(String text) {
		super(text);
	}
	
	public GenericButton(String text, ActionListener actionListener) {
		super(text);
		this.addActionListener(actionListener);
	}
	
	public GenericButton(String text, Dimension defaultSize) {
		super(text);
		this.setPreferredSize(defaultSize);
	}
	
	public GenericButton(String text, ActionListener actionListener, Dimension defaultSize) {
		super(text);
		this.addActionListener(actionListener);
		this.setPreferredSize(defaultSize);
	}
}
