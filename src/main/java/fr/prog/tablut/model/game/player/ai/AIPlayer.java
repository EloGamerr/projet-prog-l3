package fr.prog.tablut.model.game.player.ai;

import java.awt.Point;

import java.util.Random;

import fr.prog.tablut.controller.animation.AnimationMove;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.view.pages.game.GamePage;


public abstract class AIPlayer extends Player {
	protected final Random random;
	private AnimationMove anim;
    protected boolean lockPlay;

	public AIPlayer(PlayerEnum playerEnum, PlayerTypeEnum type) {
		super(playerEnum, type);
        lockPlay = false;
		random = new Random();
	}

    /**
     * Returns either the animation is processing
     * @param game
     * @param isInAnim
     * @return
     */
    public boolean checkAnim(Game game) {
        if(anim != null) {
            // animation not ended
			if(!anim.tick())
                return true;

            anim = null;
		}

		return false;
    }

    public Movement play(Game game) {
        return null;
    }

	/**
	 *
	 * @return True if the child class should play, false otherwise (if an animation is running)
	 */
	@Override
	public boolean play(Game game, Point from, Point to) {
		return false;
	}

    public boolean lockThread() {
        return !lockPlay && (lockPlay = true);
    }

    public boolean unlockThread() {
        return lockPlay && !(lockPlay = false);
    }

	public void createAnimation(Point fromCell, Point toCell, GamePage gamePage) {
		this.anim = new AnimationMove(new Movement(fromCell.x,fromCell.y, toCell.x, toCell.y), gamePage);
		anim.start();
	}
}
