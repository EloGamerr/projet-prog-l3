package fr.prog.tablut.view.components.generic;

public enum TextAlignment {
	CENTER(0),
	LEFT(1),
	RIGHT(2);

	private final int i;

	TextAlignment(final int i) {
		this.i = i;
	}

    public int getValue() {
        return i;
    }
}
