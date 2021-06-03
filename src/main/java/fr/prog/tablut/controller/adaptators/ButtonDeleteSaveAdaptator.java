package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;

public class ButtonDeleteSaveAdaptator extends ActionAdaptator<GenericButton> {
	private final SavedGamesPanel savedGamesPanel;
    private final JPanel saveButton;
	private final int index;
	
	public ButtonDeleteSaveAdaptator(GenericButton button, int index, JPanel saveButton, SavedGamesPanel savedGamesPanel) {
		super(button);
		this.savedGamesPanel = savedGamesPanel;
        this.saveButton = saveButton;
		this.index = index;
	}
	
	@Override
	public void process(ActionEvent e) {
		if(GameSaver.getInstance().delete(index)) {
            if(savedGamesPanel.getSelectedIndex() == index)
                savedGamesPanel.disableConfirmButton();

            savedGamesPanel.deleteSave(saveButton);
        }
	}
}
