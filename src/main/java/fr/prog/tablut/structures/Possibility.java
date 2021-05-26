package fr.prog.tablut.structures;

public class Possibility {
	public Couple<Integer,Integer> currentCase; // Les coordon�e de la case � tester
	public Couple<Integer,Integer> targetCase; // Toutes les cases accessibles depuis la case courante
	
	/**
	 * @param currentCase : Les coordon�e de la case � tester
	 * @param grid : La description du jeu
	 */
	public Possibility(Couple<Integer,Integer> currentCase, Couple<Integer,Integer> targetCase) {
		// TODO Auto-generated constructor stub
		this.currentCase = currentCase;
		this.targetCase = targetCase;
	}
}
