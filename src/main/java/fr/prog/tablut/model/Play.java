package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.List;

public class Play {
	List<Movement> movements;

	Play() {
		movements = new ArrayList<>();
	}

	public void move(int dL, int dC, int vL, int vC) {
		movements.add(new Movement(dL, dC, vL, vC));
	}

	public List<Movement> movements() {
		return movements;
	}
}
