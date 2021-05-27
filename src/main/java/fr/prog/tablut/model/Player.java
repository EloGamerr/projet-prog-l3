package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.structures.Couple;

public abstract class Player {
	private List<Couple<Integer, Integer>> ownedCells = new ArrayList<>();
	/**
	 * @return True if we should repaint the window after the play
	 */
	public abstract boolean play(Game game);
	
	public List<Couple<Integer, Integer>> getOwnedCells() {
		return this.ownedCells;
	}
}
