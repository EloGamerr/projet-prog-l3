package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPannel;

public class ButtonToggleAdaptator implements ActionListener {
	
	private final GenericButton button;
	private final SavedGamesPannel buttonTogglePannel;
	
	public ButtonToggleAdaptator(GenericButton button, SavedGamesPannel buttonTogglePannel) {
		this.button = button;
		this.buttonTogglePannel = buttonTogglePannel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonTogglePannel.selected(button);
	}
}
