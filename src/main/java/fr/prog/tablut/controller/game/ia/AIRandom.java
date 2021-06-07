package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.pages.game.GamePage;

/**
* IA totalement aléatoire. Aucun choix n'est plus rentable qu'un autre
 */
public class AIRandom extends AIMinMax {
    public AIRandom(PlayerEnum playerEnum) {
        super(playerEnum);
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
        	anim = new AnimationCoup(new Play(new Movement(fromCell.x,fromCell.y, toCell.x, toCell.y)), gamePage);

        	anim.startAnim();

        	return true;
    	}
    }

    @Override
    public double evaluation(Simulation simulation, PlayerEnum playerEnum) {
        // On renvoie une valeur constante, tous les coups auront donc une heuristique égale et le coup sera choisi aléatoirement
        return 0;
    }

    @Override
    public String toString() {
    	return "AIRandom";
    }
}
