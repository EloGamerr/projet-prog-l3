package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.right.RightSideGame;

public class ButtonSaveAdaptator extends ActionAdaptator<GenericButton> {
	RightSideGame sideGame;
	
	public ButtonSaveAdaptator(GenericButton button, RightSideGame sideGame) {
		super(button);
		this.sideGame = sideGame;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		sideGame.getGameController().save();
	}
}
