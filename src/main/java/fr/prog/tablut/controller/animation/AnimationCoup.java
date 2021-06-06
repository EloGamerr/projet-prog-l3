package fr.prog.tablut.controller.animation;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.view.pages.game.GamePage;

public class AnimationCoup {
	Movement mov;
	GamePage view;
	double progres, vitesseAnim;
	boolean isInAnim;
	
	public AnimationCoup(Play c, GamePage gamePage) {
		vitesseAnim = 0.05;
		this.view = gamePage;
		mov = c.getMovement();
		isInAnim = gamePage.isInAnim();
	}


	public void update_anim() {
		progres += vitesseAnim;
		
        if(progres > 1)
			progres = 1;

        int fromC = view.getXCoordFromCol(mov.fromC);
        int fromL = view.getYCoordFromRow(mov.fromL);
        int toC = view.getXCoordFromCol(mov.toC);
        int toL = view.getYCoordFromRow(mov.toL);
        
			
        int dC =  (int)(fromC - (fromC - toC) * progres);
        int dL =  (int)(fromL - (fromL - toL) * progres);

        view.update_anim(dL, dC, mov.fromL, mov.fromC);
	}

	public void stop_anim() {
        Game.getInstance().move(mov.fromL, mov.fromC, mov.toL, mov.toC);
        view.stop_anim();
	}

	public boolean isOver() {
		return progres >= 1;
	}

	public void check_anim() {
		if(isOver())
			stop_anim();
		else
			update_anim();	
	}
	
	public void startAnim() {
		if(Game.getInstance().canMove(mov.fromL, mov.fromC, mov.toL, mov.toC))
			update_anim();
	}
}
