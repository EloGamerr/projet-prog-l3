package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.game.GameWindow;

public class ButtonNavAdaptator implements ActionListener{
	private final GlobalWindow globalWindow;
	private final WindowName dest;
	
	public ButtonNavAdaptator(GlobalWindow globalWindow, WindowName dest) {
		// TODO Auto-generated constructor stub
		this.globalWindow = globalWindow;
		this.dest = dest;
	}
	
	 @Override
    public void actionPerformed(ActionEvent e) {
        globalWindow.changeWindow(dest);
    }
	
}
