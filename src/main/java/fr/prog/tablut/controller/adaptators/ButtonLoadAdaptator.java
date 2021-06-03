package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;

public class ButtonLoadAdaptator extends ActionAdaptator<GenericButton> {
	private final SavedGamesPanel savedGamesPanel;
	private final int index;
	
	public ButtonLoadAdaptator(GenericButton button, int index, SavedGamesPanel savedGamesPanel) {
		super(button);
		this.savedGamesPanel = savedGamesPanel;
		this.index = index;
	}
	
	@Override
	public void process(ActionEvent e) {
		savedGamesPanel.select(entity, index);
	}
}
