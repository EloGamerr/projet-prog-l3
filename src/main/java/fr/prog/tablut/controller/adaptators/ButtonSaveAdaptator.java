package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

public class ButtonSaveAdaptator extends ActionAdaptator<GenericButton> {
	GameInterfaceSide sideGame;
	
	public ButtonSaveAdaptator(GenericButton button, GameInterfaceSide sideGame) {
		super(button);
		this.sideGame = sideGame;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		if(e.getSource() == sideGame.getComponent(0)) 
			Game.getInstance().getGameSaver().newSave();	
		if(e.getSource() == sideGame.getComponent(1))
			Game.getInstance().getGameSaver().save();
	}
}
