package fr.prog.tablut.model;


import Structures.Sequence;
import Structures.SequenceList;

public class Play {
	Sequence<Movement> movements;

	Play() {
		movements = new SequenceList<Movement>();
	}

	public void move(int dL, int dC, int vL, int vC) {
		movements.addTail(new Movement(dL, dC, vL, vC));
	}

	public Sequence<Movement> movements() {
		return movements;
	}
}
