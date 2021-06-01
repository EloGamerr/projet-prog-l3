package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;

public class ButtonToggleAdaptator implements ActionListener {
	
	private final GenericButton button;
	private final SavedGamesPanel savedGamesPanel;
	private int index;
	
	public ButtonToggleAdaptator(GenericButton button, int index, SavedGamesPanel savedGamesPanel) {
		this.button = button;
		this.savedGamesPanel = savedGamesPanel;
		this.index = index;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		savedGamesPanel.selected(button, index);
	}
}
