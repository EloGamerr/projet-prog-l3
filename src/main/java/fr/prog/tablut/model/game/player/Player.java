package fr.prog.tablut.model.game.player;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.Game;

public abstract class Player implements Cloneable {
	private List<Point> ownedCells;
	private PlayerEnum playerEnum;
    protected final PlayerTypeEnum type;

	public Player(PlayerEnum playerEnum, PlayerTypeEnum type) {
		this.playerEnum = playerEnum;
		this.ownedCells = new ArrayList<>();
        this.type = type;
	}

	/**
	 * @return True if we should repaint the window after the play
	 */
	public abstract boolean play(Game game, Point from, Point to);

	public List<Point> getOwnedCells() {
		return this.ownedCells;
	}

	public PlayerEnum getPlayerEnum() {
		return playerEnum;
	}

    public PlayerTypeEnum getType() {
        return type;
    }

    public boolean isHuman() {
        return type == PlayerTypeEnum.HUMAN;
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
