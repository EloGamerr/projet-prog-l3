package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;

/**
* Adaptateur pour les boutons de chargement de partie
*/
public class ButtonLoadAdaptator extends ActionAdaptator<GenericButton> {
	private final SavedGamesPanel savedGamesPanel;
	private final int index;
	
	/**
	* @param button Le component bouton
	* @param index L'index du bouton dans le tableau
	* @param savedGamesPanel Le panel de sauvegarde
	*/
	public ButtonLoadAdaptator(GenericButton button, int index, SavedGamesPanel savedGamesPanel) {
		super(button);
		this.savedGamesPanel = savedGamesPanel;
		this.index = index;
	}
	
	@Override
	public void process(ActionEvent e) {
		//Si le bouton est cliqué par l'utilisateur on va déclencher l'action du panel de sauvegarde
		savedGamesPanel.select(entity, index);
	}
}
