package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.controller.animation.AnimationCoup;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.pages.game.GamePage;

import java.awt.*;
import java.util.Random;

public abstract class AIPlayer extends Player {
	protected final Random random;
	private AnimationCoup anim;

	public AIPlayer(PlayerEnum playerEnum) {
		super(playerEnum);

		random = new Random();
	}

	/**
	 *
	 * @return True if the child class should play, false otherwise (if an animation is running)
	 */
	@Override
	public boolean play(Game game, GamePage gamePage) {
		boolean isInAnim = gamePage.isInAnim();

		if(isInAnim && anim != null) {
			anim.check_anim();
			return false;
		}

		return true;
	}

	public void updateAnim(Point fromCell, Point toCell, GamePage gamePage) {
		this.anim = new AnimationCoup(new Movement(fromCell.x,fromCell.y, toCell.x, toCell.y), gamePage);
		anim.startAnim();
	}
}
