package fr.prog.tablut.structures;

public class Possibility {
	public Couple<Integer,Integer> currentCase; // Les coordonées de la case à tester
	public Couple<Integer,Integer> targetCase; // Toutes les cases accessibles depuis la case courante
	
	/**
	 * @param currentCase : Les coordonées de la case à tester
	 * @param grid : La description du jeu
	 */
	public Possibility(Couple<Integer,Integer> currentCase, Couple<Integer,Integer> targetCase) {
		this.currentCase = currentCase;
		this.targetCase = targetCase;
	}
}
