package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

public class ButtonPauseAdaptator extends ActionAdaptator<GenericButton> {
	GameInterfaceSide sideGame;
	
	public ButtonPauseAdaptator(GenericButton button, GameInterfaceSide sideGame) {
		super(button);
		this.sideGame = sideGame;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		if(!Game.getInstance().isPaused())
			Game.getInstance().setPaused(true);
		else
			Game.getInstance().setPaused(false);
	}
}