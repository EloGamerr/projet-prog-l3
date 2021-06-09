package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.ActionEvent;

import fr.prog.tablut.controller.adaptators.ActionAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericButton;

public class ButtonUndoRedoAdaptator extends ActionAdaptator<GenericButton> {
	GameController gameController;

	public ButtonUndoRedoAdaptator(GenericButton button, GameController gameController) {
		super(button);
		this.gameController = gameController;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		if(entity.getName() == "undo-button")
			gameController.undo();
        
		else if(entity.getName() == "redo-button")
			gameController.redo();
	}
}