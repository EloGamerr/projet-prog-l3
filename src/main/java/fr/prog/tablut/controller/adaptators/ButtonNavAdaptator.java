package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.GlobalWindow;

public class ButtonNavAdaptator implements ActionListener{
	private final GlobalWindow globalWindow;
	private final String dest;
	
	public ButtonNavAdaptator(GlobalWindow globalWindow, String dest) {
		// TODO Auto-generated constructor stub
		this.globalWindow = globalWindow;
		this.dest = dest;
	}
	
	 @Override
    public void actionPerformed(ActionEvent e) {
        globalWindow.changeWindow(dest);
    }
	
}
