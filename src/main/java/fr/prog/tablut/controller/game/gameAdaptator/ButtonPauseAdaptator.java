package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.ActionEvent;

import fr.prog.tablut.controller.adaptators.ActionAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericButton;

public class ButtonPauseAdaptator extends ActionAdaptator<GenericButton> {
	private final GameController gameController;
	
	public ButtonPauseAdaptator(GenericButton button, GameController gameController) {
		super(button);
		this.gameController = gameController;
	}
	
	@Override
	public void process(ActionEvent e) {
		gameController.pause();
	}
}