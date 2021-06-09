package fr.prog.tablut.controller.animation;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.view.pages.game.GamePage;

/**
* Animation des coups joués par l'IA 
 * prends en compte une vitesse d'animation 
 * nécéssite en paramètre le mouvement choisie par l'IA et la page de jeu actuelle
 */
public class AnimationCoup {
	Movement mov;
	GamePage view;
	double progres, vitesseAnim;
	
	public AnimationCoup(Movement movement, GamePage gamePage) {
		vitesseAnim = 0.05;
		this.view = gamePage;
		mov = movement;
	}
	/*
	* Commence l'animation si le move est autorisé
 	*/
	public void startAnim() {
		if(Game.getInstance().canMove(mov.getFromC(), mov.getFromL(), mov.getToC(), mov.getToL()))
			update_anim();
	}
	/*
	* Vérifie l'état de l'animation en cours
 	*/
	public void check_anim() {
		if(isOver())
			stop_anim();
		else
			update_anim();	
	}
	/*
	* Permet d'update la position de l'animation en cours
 	*/
	public void update_anim() {
		progres += vitesseAnim;
		
        if(progres > 1)
			progres = 1;

        int fromC = view.getXCoordFromCol(mov.getFromC());
        int fromL = view.getYCoordFromRow(mov.getFromL());
        int toC = view.getXCoordFromCol(mov.getToC());
        int toL = view.getYCoordFromRow(mov.getToL());
        
        int dC =  (int)(fromC - (fromC - toC) * progres);
        int dL =  (int)(fromL - (fromL - toL) * progres);

        view.update_anim(new Point(dC, dL), mov.getFrom(), mov.getTo()); // Ici on fait le repaint avec la nouvelle frame d'animation
	}

	/*
	* Termine l'animation en cours
 	*/
	public void stop_anim() {
		view.stop_anim(); // met à jours les variables de la vue servant à l'animation
        Game.getInstance().move(mov.getFromC(), mov.getFromL(), mov.getToC(), mov.getToL()); // met à jour le jeu en jouant le coup pour le modele
	}

	/*
	* Vérifie si l'animation est terminée
 	*/
	public boolean isOver() {
		return progres >= 1;
	}

	
	
}
