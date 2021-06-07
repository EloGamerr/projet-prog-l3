package fr.prog.tablut.controller.game.ia.old.Heuristique;

import fr.prog.tablut.model.game.CellContent;

public abstract class Heuristique {
	public int heuristique(CellContent[][] grille, boolean toursDesBlancs) {
		return toursDesBlancs ? heuristique(grille) : -heuristique(grille);
	}

	protected abstract int heuristique(CellContent[][] grille);
}