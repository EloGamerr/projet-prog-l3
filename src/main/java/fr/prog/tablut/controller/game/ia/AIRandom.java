package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.controller.animation.AnimationCoup;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.Play;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.GamePage;

import java.util.List;
import java.util.Random;



@Deprecated
public class AIRandom extends AIPlayer {
    private Random random;
    AnimationCoup anim;
    Couple<Integer, Integer> fromCell = new Couple<Integer, Integer>(null, null);
    Couple<Integer, Integer> toCell = new Couple<Integer, Integer>(null, null);
    boolean isInAnim;
    public AIRandom() {
        this.random = new Random();
    }

    @Override
    public boolean play(Game game, GamePage gamePage) {
    	isInAnim = false;//gamePage.getGridWindow().getGridView().isInAnim();
    	
        if(isInAnim) {
    		anim.check_anim();
    		return true;
    	}
    	else {
    		List<Couple<Integer, Integer>> accesibleCells;
        	do {
        		List<Couple<Integer, Integer>> ownedCells = this.getOwnedCells();

            	fromCell = ownedCells.get(random.nextInt(ownedCells.size()));

            	accesibleCells = game.getAccessibleCells(fromCell.getFirst(), fromCell.getSecond());
        	} while(accesibleCells.isEmpty());
        
        	toCell = accesibleCells.get(random.nextInt(accesibleCells.size()));
        	
        	anim =  new AnimationCoup(new Play(new Movement(fromCell.getFirst(), fromCell.getSecond(), toCell.getFirst(), toCell.getSecond())), gamePage);
        	anim.startAnim();
        	
        	return true;
    	}
    }
    
    
    
    
    @Override
    public String toString() {
    	return "AIRandom";
    }

}

