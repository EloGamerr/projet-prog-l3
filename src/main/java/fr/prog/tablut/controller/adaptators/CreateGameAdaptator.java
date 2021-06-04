package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.newGame.SelectionPlayer;

public class CreateGameAdaptator  extends ActionAdaptator<GenericButton> {
    SelectionPlayer gameSettings;

	public CreateGameAdaptator(GenericButton button, SelectionPlayer gameSettings) {
		super(button);
        this.gameSettings = gameSettings;
	}
	
	@Override
	public void process(ActionEvent e) {
		// créé une instance
		Game.getInstance().start(gameSettings.getPlayerType1(), gameSettings.getPlayerType2());
	}
}