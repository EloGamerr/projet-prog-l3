package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.GlobalWindow;

public class ButtonNewGameAdaptator implements ActionListener{
	private final GlobalWindow globalWindow;
	
	public ButtonNewGameAdaptator(GlobalWindow globalWindow) {
		// TODO Auto-generated constructor stub
		this.globalWindow = globalWindow;
	}
	
	 @Override
	    public void actionPerformed(ActionEvent e) {
	        globalWindow.changeWindow("GameWindow");
	    }
	
}
