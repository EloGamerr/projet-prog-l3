package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.controller.game.Elagage;
import fr.prog.tablut.controller.game.Simulation;
import fr.prog.tablut.controller.game.ia.Heuristique.Heuristique;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.structures.Couple;

public class IA extends Player{
	
	private Heuristique h;
	private Elagage e;
	private int r;

	public IA(Heuristique h, Elagage e, int r) {
	    this.h = h;
	    this.e = e;
	    this.r = r;
	}
	
	@Override
	public boolean play(Game game) {
		try {
			Movement m = joue(new Simulation(game.getGrid()), (game.getPlayingPlayerEnum() == PlayerEnum.DEFENDER), r).getFirst();
			game.move(m.fromL, m.fromC, m.toL, m.toC);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private Couple<Movement, Integer> joue(Simulation s, boolean tourDesBlancs, int r) {
	    //List<Movement> pc = e.elage(s.coupsJouable(toursDesBlancs));
	    //pour tout m dans pc
	    //on prend renvoie m de meilleur heuritique si r = 0
	    //on renvoie joue(grille (avec m simuler), !toursDesBlancs, r-1) avec .getFirst le plus petit
	    //(ne pas oublier d'annuler m apres simulation du coup (deplacer puis re déplacer))
		 
		return null;
	}
	
}
