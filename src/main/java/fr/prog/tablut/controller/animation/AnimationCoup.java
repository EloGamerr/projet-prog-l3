package fr.prog.tablut.controller.animation;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.view.pages.game.GamePage;

/**
 * Animation of the AI plays 
 * @param animSpeed the speed of the animation
 * @param gamePage the page of the current game
 * @param movement the movement choosed by the AI
 */
public class AnimationCoup {
	private final Movement mov;
	private final GamePage view;
	private double progress;
	private final double animSpeed;
	
	public AnimationCoup(Movement movement, GamePage gamePage) {
		animSpeed = 0.05;
		this.view = gamePage;
		mov = movement;
	}
	/*
	* Start the animation if the move is autorised
 	*/
	public void startAnim() {
		if(Game.getInstance().canMove(mov.getFromC(), mov.getFromL(), mov.getToC(), mov.getToL()))
			update_anim();
	}
	/*
	* Verify if the animation is over, continue if not
 	*/
	public void check_anim() {
		if(isOver())
			stop_anim();
		else
			update_anim();	
	}
	/*
	* Permits to update the current animation
 	*/
	public void update_anim() {
		progress += animSpeed;
		
        if(progress > 1)
			progress = 1;

        int fromC = view.getXCoordFromCol(mov.getFromC());
        int fromL = view.getYCoordFromRow(mov.getFromL());
        int toC = view.getXCoordFromCol(mov.getToC());
        int toL = view.getYCoordFromRow(mov.getToL());
        
        int dC =  (int)(fromC - (fromC - toC) * progress);
        int dL =  (int)(fromL - (fromL - toL) * progress);

        view.update_anim(new Point(dC, dL), mov.getFrom(), mov.getTo()); // Repaint
	}

	/*
	* Stops the animation
 	*/
	public void stop_anim() {
		view.stop_anim();
        Game.getInstance().move(mov.getFromC(), mov.getFromL(), mov.getToC(), mov.getToL()); // met à jour le jeu en jouant le coup pour le modele
	}

	/*
	* Vérifie si l'animation est terminée
 	*/
	public boolean isOver() {
		return progress >= 1;
	}

	
	
}
