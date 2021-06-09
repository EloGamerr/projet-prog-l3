package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericButton;

/**
* Adaptateur pour les actions d'undo et de redo du jeu
*/
public class ButtonUndoRedoAdaptator extends ActionAdaptator<GenericButton> {
	private final GameController gameController;

	public ButtonUndoRedoAdaptator(GenericButton button, GameController gameController) {
		super(button);
		this.gameController = gameController;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		//On choisit l'action a faire en fonction du nom du bouton
		if(entity.getName().equals("undo-button"))
			gameController.undo();
        
		else if(entity.getName().equals("redo-button"))
			gameController.redo();
	}
}