package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;


public class ButtonRestartAdaptator extends ActionAdaptator<GenericButton> {
	GameController gameController;
	
	public ButtonRestartAdaptator(GenericButton button, GameController gameController) {
		super(button);
		this.gameController = gameController;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		gameController.restart();
	}
}
