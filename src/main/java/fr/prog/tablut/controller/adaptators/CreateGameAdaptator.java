package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.newGame.SelectionPlayerForm;

/**
* Adaptateur pour créer une nouvelle game
*/
public class CreateGameAdaptator  extends ActionAdaptator<GenericButton> {
    SelectionPlayerForm gameSettings;

	public CreateGameAdaptator(GenericButton button, SelectionPlayerForm gameSettings) {
		super(button);
        this.gameSettings = gameSettings;
	}
	
	@Override
	public void process(ActionEvent e) {
		PlayerTypeEnum attacker = gameSettings.getPlayerType1();
		PlayerTypeEnum defender = gameSettings.getPlayerType2();
		String attackerName = gameSettings.getAttackerName();
		String defenderName = gameSettings.getDefenderName();

		//Si les inputs des joueurs sont vide, on les remplaces par des noms génériques
        if(attackerName.replace(" ", "").length() == 0) attackerName = "Joueur 1";
        if(defenderName.replace(" ", "").length() == 0) defenderName = "Joueur 2";
		
		Game.getInstance().start(attacker,defender, attackerName, defenderName);
	}
}