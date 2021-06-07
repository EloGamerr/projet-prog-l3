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
	protected Random random;
	private AnimationCoup anim;
	private boolean isInAnim;
	private Point fromCell = null;
	private Point toCell = null;

	public AIPlayer(PlayerEnum playerEnum) {
		super(playerEnum);

		random = new Random();
	}

	/**
	 *
	 * @param game
	 * @param gamePage
	 * @return True if the child class should play, false otherwise (if an animation is running)
	 */
	@Override
	public boolean play(Game game, GamePage gamePage) {
		isInAnim = gamePage.isInAnim();

		if(isInAnim && anim != null) {
			anim.check_anim();
			return false;
		}

		return true;
	}

	public void updateAnim(Point fromCell, Point toCell, GamePage gamePage) {
		this.fromCell = fromCell;
		this.toCell = toCell;
		this.anim = new AnimationCoup(new Movement(fromCell.x,fromCell.y, toCell.x, toCell.y), gamePage);
		anim.startAnim();
	}
}
