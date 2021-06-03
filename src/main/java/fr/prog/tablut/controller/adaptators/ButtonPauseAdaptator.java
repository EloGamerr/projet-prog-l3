package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.right.RightSideGame;

public class ButtonPauseAdaptator extends ActionAdaptator<GenericButton> {
	RightSideGame sideGame;
	
	public ButtonPauseAdaptator(GenericButton button, RightSideGame sideGame) {
		super(button);
		this.sideGame = sideGame;
	}
	
	@Override
	public void process(ActionEvent e) {
		sideGame.getGameController().pause();
		sideGame.togglePauseButton(Game.getInstance().isPaused());
	}
}