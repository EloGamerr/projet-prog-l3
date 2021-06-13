package fr.prog.tablut.model.game;

public enum MoveType {
	MOVE("move"),
	UNDO("undo"),
	REDO("redo"),
	RESTART("restart"),
	PAUSE("pause"),
    NONE("none");

	private final String name;

	MoveType(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
