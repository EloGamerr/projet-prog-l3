package fr.prog.tablut.model.game.player;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.GamePage;

public abstract class Player {
	private List<Point> ownedCells = new ArrayList<>();
	/**
	 * @param gamePage 
	 * @return True if we should repaint the window after the play
	 */
	public abstract boolean play(Game game, GamePage gamePage);
	
	public List<Point> getOwnedCells() {
		return this.ownedCells;
	}
}
