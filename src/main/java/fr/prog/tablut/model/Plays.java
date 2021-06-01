package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.List;

public class Plays {
	private final List<Play> plays;
	private int currentMovement;

	Plays() {
		plays = new ArrayList<>();
		this.currentMovement = -1;
	}

	/**
	 * This method is called when a moved is done in the game
	 * @return The Play created by the move and add it to the plays list
	 */
	public Play move(int fromL, int fromC, int toL, int toC) {
		getNextMovements().clear(); // We must clear nextMovements if a player plays without using undo/redo buttons

		Play play = new Play(new Movement(fromL, fromC, toL, toC));
		plays.add(play);
		this.currentMovement++;

		return play;
	}

	/**
	 * @return The last play, null if there is no previous play
	 */
	public Play undo_move() {
		if(getPreviousMovements().isEmpty()) return null;

		this.currentMovement--;

		return plays.get(this.currentMovement+1);
	}

	/**
	 * @return The next play, null if there is no next play
	 */
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
