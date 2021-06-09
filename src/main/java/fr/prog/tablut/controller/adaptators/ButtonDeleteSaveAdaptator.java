package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;

/**
* Classe servant a gérer les boutons pour supprimer les sauvegardes
*/
public class ButtonDeleteSaveAdaptator extends ActionAdaptator<GenericButton> {
	private final SavedGamesPanel savedGamesPanel;
    private final JPanel saveButton;
	private final int index;
	
	/**
	* @param button Le component bouton
	* @param index L'index du bouton dans le tableau
	* @param savedGamesPanel Le panel de sauvegarde
	*/
	public ButtonDeleteSaveAdaptator(GenericButton button, int index, JPanel saveButton, SavedGamesPanel savedGamesPanel) {
		super(button);
		this.savedGamesPanel = savedGamesPanel;
        this.saveButton = saveButton;
		this.index = index;
	}
	
	@Override
	public void process(ActionEvent e) {
		//Si on réussi à supprimer le fichier de sauvegarde on peut supprimer la ligne dans le pannel de sauvegarde
		if(GameSaver.getInstance().delete(index)) {

			//Si la sauvegarde supprimée était celle séléctionnée, on désactive le bouton de jeu pour ne pas démarrer sans sauvegarde
            if(savedGamesPanel.getSelectedIndex() == index)
                savedGamesPanel.disableConfirmButton();

			//On supprime le bouton de sauvegarde
            savedGamesPanel.deleteSave(saveButton);
        }
	}
}
