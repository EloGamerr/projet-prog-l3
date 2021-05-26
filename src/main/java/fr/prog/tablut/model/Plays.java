package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.List;

public class Plays {
	private final List<Movement> movements;
	private Game game;

	Plays(Game game) {
		movements = new ArrayList<>();
		this.game = game;
	}

	public void move(int dL, int dC, int vL, int vC) {
		movements.add(new Movement(dL, dC, vL, vC));
	}
	
	public void undo_move() {
		Movement moveToUndo = movements.get(movements.size()-1);
		game.setContent(game.getCellContent(moveToUndo.toL, moveToUndo.toC),moveToUndo.fromL, moveToUndo.fromC);
		game.setContent(CellContent.EMPTY, moveToUndo.toL, moveToUndo.toC);
		movements.remove(movements.size()-1);
	}

	public List<Movement> movements() {
		return movements;
	}
}
