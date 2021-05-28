package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.GlobalWindow;

public class ButtonNavAdaptator implements ActionListener {
	private final GlobalWindow globalWindow;
	private final WindowName dest;
	
	public ButtonNavAdaptator(GlobalWindow globalWindow, WindowName dest) {
		this.globalWindow = globalWindow;
		this.dest = dest;
	}
	
	 @Override
    public void actionPerformed(ActionEvent e) {
        globalWindow.changeWindow(dest);
    }
}
