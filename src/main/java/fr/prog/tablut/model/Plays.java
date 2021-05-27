package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.List;

public class Plays {
	private final List<Play> plays;
	private int currentMovement;
	private Game game;

	Plays(Game game) {
		plays = new ArrayList<>();
		this.game = game;
		this.currentMovement = -1;
	}

	public Play move(int dL, int dC, int vL, int vC) {
		getNextMovements().clear(); // We must clear nextMovements if a player plays without using undo/redo buttons

		Play play = new Play(new Movement(dL, dC, vL, vC));
		plays.add(play);
		this.currentMovement++;

		return play;
	}
	
	public Play undo_move() {
		if(getPreviousMovements().isEmpty()) return null;

		this.currentMovement--;

		return plays.get(this.currentMovement+1);
	}

	public Play redo_move() {
		if(getNextMovements().isEmpty()) return null;

		this.currentMovement++;

		return plays.get(this.currentMovement);
	}

	public List<Play> movements() {
		return plays;
	}

	public List<Play> getPreviousMovements() {
		return plays.subList(0, this.currentMovement+1);
	}

	public List<Play> getNextMovements() {
		return plays.subList(this.currentMovement+1, plays.size());
	}
}
