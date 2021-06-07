package fr.prog.tablut.model.game.player;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;

public abstract class Player implements Cloneable {
	private List<Couple<Integer, Integer>> ownedCells = new ArrayList<>();
	/**
	 * @return True if we should repaint the window after the play
	 */
	public abstract boolean play(Game game);
	
	public List<Couple<Integer, Integer>> getOwnedCells() {
		return this.ownedCells;
	}

	@Override
	public Object clone() {
		try {
			Player o = (Player) super.clone();
			o.ownedCells = new ArrayList<>();

			for(Couple<Integer, Integer> c : this.ownedCells) {
				o.ownedCells.add(new Couple<>(c.getFirst(), c.getSecond()));
			}

			return o;
		}
		catch(CloneNotSupportedException exception) {
			exception.printStackTrace();
		}

		return null;
	}
}
