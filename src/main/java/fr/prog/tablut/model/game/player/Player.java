package fr.prog.tablut.model.game.player;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.GamePage;

public abstract class Player {
	private List<Couple<Integer, Integer>> ownedCells = new ArrayList<>();
	/**
	 * @param gamePage 
	 * @return True if we should repaint the window after the play
	 */
	public abstract boolean play(Game game, GamePage gamePage);
	
	public List<Couple<Integer, Integer>> getOwnedCells() {
		return this.ownedCells;
	}
}
