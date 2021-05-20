package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.generic.GenericButton;
import fr.prog.tablut.view.load.ButtonTogglePannel;

public class ButtonToggleAdaptator implements ActionListener{
	
	private final GenericButton button;
	private final ButtonTogglePannel buttonTogglePannel;
	
	public ButtonToggleAdaptator(GenericButton button, ButtonTogglePannel buttonTogglePannel) {
		this.button = button;
		this.buttonTogglePannel = buttonTogglePannel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonTogglePannel.selected(button);
	}
}
