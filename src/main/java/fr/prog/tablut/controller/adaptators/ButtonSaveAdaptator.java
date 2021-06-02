package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

public class ButtonSaveAdaptator extends ActionAdaptator<GenericButton> {
	GameInterfaceSide leftSideGame;
	
	public ButtonSaveAdaptator(GenericButton button, GameInterfaceSide leftSideGame) {
		super(button);
		this.leftSideGame = leftSideGame;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		Game.getInstance().getGameSaver().save();
	}
}
