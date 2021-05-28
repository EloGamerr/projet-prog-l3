package fr.prog.tablut.model.game;

public class Movement {
	public final int fromL;
	public final int fromC;
	public final int toL;
	public final int toC;

	public Movement(int fromL, int fromC, int toL, int toC) {
		this.fromL = fromL;
		this.fromC = fromC;
		this.toL = toL;
		this.toC = toC;
	}
	
}
