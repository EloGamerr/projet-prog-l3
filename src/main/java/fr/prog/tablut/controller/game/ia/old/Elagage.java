package fr.prog.tablut.controller.game.ia.old;

import java.util.List;

import fr.prog.tablut.model.game.Movement;

@FunctionalInterface
public interface Elagage {
	public List<Movement> elagage(List<Movement> pc);
}
