package fr.prog.tablut.model.game.player;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.GamePage;

public abstract class Player implements Cloneable {
	private List<Point> ownedCells;
	private PlayerEnum playerEnum;

	public Player(PlayerEnum playerEnum) {
		this.playerEnum = playerEnum;
		this.ownedCells = new ArrayList<>();
	}

	/**
	 * @param gamePage
	 * @return True if we should repaint the window after the play
	 */
	public abstract boolean play(Game game, GamePage gamePage);
	
	public List<Point> getOwnedCells() {
		return this.ownedCells;
	}

	public PlayerEnum getPlayerEnum() {
		return playerEnum;
	}

	@Override
	public Object clone() {
		try {
			Player o = (Player) super.clone();
			o.ownedCells = new ArrayList<>();

			for(Point p : this.ownedCells) {
				o.ownedCells.add(new Point(p));
			}

			o.playerEnum = this.playerEnum;

			return o;
		}
		catch(CloneNotSupportedException exception) {
			exception.printStackTrace();
		}

		return null;
	}
}
