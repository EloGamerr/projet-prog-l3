package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;

import java.util.Random;

public abstract class AIPlayer extends Player {
	protected Random random;
	public AIPlayer(PlayerEnum playerEnum) {
		super(playerEnum);

		random = new Random();
	}
}
