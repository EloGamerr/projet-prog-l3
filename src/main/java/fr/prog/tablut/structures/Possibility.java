package fr.prog.tablut.structures;

import java.awt.Point;

public class Possibility {
	public Point currentCase; // Les coordonées de la case à tester
	public Point targetCase; // Toutes les cases accessibles depuis la case courante
	
	/**
	 * @param currentCase : Les coordonées de la case à tester
	 * @param grid : La description du jeu
	 */
	public Possibility(Point currentCase, Point targetCase) {
		this.currentCase = currentCase;
		this.targetCase = targetCase;
	}
}
