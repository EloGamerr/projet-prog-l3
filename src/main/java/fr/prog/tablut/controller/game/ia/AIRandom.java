package fr.prog.tablut.controller.game.ia;

import java.awt.Point;

import fr.prog.tablut.controller.animation.AnimationCoup;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.view.pages.game.GamePage;

import java.util.List;
import java.util.Random;



@Deprecated
public class AIRandom extends AIPlayer {
    private Random random;
    AnimationCoup anim;
    Point fromCell = null;
    Point toCell = null;
    boolean isInAnim;
    public AIRandom() {
        this.random = new Random();
    }

    @Override
    public boolean play(Game game, GamePage gamePage) {
    	isInAnim = gamePage.isInAnim();
    	
        if(isInAnim) {
    		anim.check_anim();
    		return true;
    	}
    	else {
    		List<Point> accesibleCells;

        	do {
        		List<Point> ownedCells = this.getOwnedCells();

            	fromCell = ownedCells.get(random.nextInt(ownedCells.size()));

            	accesibleCells = game.getAccessibleCells(fromCell.x,fromCell.y);
        	} while(accesibleCells.isEmpty());
        
        	toCell = accesibleCells.get(random.nextInt(accesibleCells.size()));
        	
        	anim =  new AnimationCoup(new Play(new Movement(fromCell.x,fromCell.y, toCell.x, toCell.y)), gamePage);
        	anim.startAnim();
        	
        	return true;
    	}
    }
    
    
    
    
    @Override
    public String toString() {
    	return "AIRandom";
    }

}

