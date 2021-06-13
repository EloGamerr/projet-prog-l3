package fr.prog.tablut.controller.animation;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.view.pages.game.GamePage;

public class AnimationMove {
	private final Movement mov;
	private final GamePage view;
	private double progress = 0;
	private final double animSpeed;

	/**
	 * Animation of the AI plays
	 * @param gamePage the page of the current game
	 * @param movement the movement choosed by the AI
	 */
	public AnimationMove(Movement movement, GamePage gamePage) {
		animSpeed = 0.05;
		this.view = gamePage;
		mov = movement;
	}

	/**
	 * Start the animation if the move is autorised
 	 */
	public void start() {
		if(Game.getInstance().canMove(mov.getFromC(), mov.getFromL(), mov.getToC(), mov.getToL()))
			update();
	}

    /**
	 * Stops the animation
 	 */
	public void stop() {
		view.stopAnimation();
	}

	/**
	 * Verify if the animation is over, continue if not
 	 */
	public boolean tick() {
		if(hasEnded()) {
            stop();
            return true;
        }

		return update();
	}

	/**
	 * Permits to update the current animation
     * @return Either the animation has ended or not in this current tick
 	 */
	private boolean update() {
        if(hasEnded())
            return true;
        
		progress += animSpeed;

        if(progress > 1.0)
			progress = 1.0;

        int fromC = view.getXCoordFromCol(mov.getFromC());
        int fromL = view.getYCoordFromRow(mov.getFromL());
        int toC = view.getXCoordFromCol(mov.getToC());
        int toL = view.getYCoordFromRow(mov.getToL());

        int dC =  (int)(fromC - (fromC - toC) * progress);
        int dL =  (int)(fromL - (fromL - toL) * progress);

        view.updateAnimation(new Point(dC, dL), mov.getFrom(), mov.getTo()); // Repaint

        return hasEnded();
	}

	/**
	 * Vérifie si l'animation est terminée
 	 */
	public boolean hasEnded() {
		return progress >= 1.0;
	}
}
