package fr.prog.tablut.controller.animation;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.pages.game.sides.center.board.GridWindow;

public class AnimationCoup {
	Movement mov;
	GamePage view;
	GridWindow gridWindow;
	double progres, vitesseAnim;
	boolean isInAnim;
	
	public AnimationCoup(Play c, GamePage  gamePage) {
		vitesseAnim = 0.05;
		this.view = gamePage;
		this.gridWindow = view.getGridWindow();
		mov = c.getMovement();
		isInAnim = gamePage.getGridWindow().getGridView().isInAnim();
	}


	public void update_anim() {
		progres += vitesseAnim;
		
        if(progres > 1)
			progres = 1;
			
        int dC =  (int) (gridWindow.getXCoordFromCol(mov.fromC) - ((gridWindow.getXCoordFromCol(mov.fromC) - gridWindow.getXCoordFromCol(mov.toC))*(progres)));
        int dL =  (int) (gridWindow.getYCoordFromRow(mov.fromL) - ((gridWindow.getYCoordFromRow(mov.fromL) - gridWindow.getYCoordFromRow(mov.toL))*(progres)));
        
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
