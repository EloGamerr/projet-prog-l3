package fr.prog.tablut.model.game;

public class Movement {
	public int fromL;
	public int fromC;
	public int toL;
	public int toC;

	public Movement(int fromL, int fromC, int toL, int toC) {
		this.fromL = fromL;
		this.fromC = fromC;
		this.toL = toL;
		this.toC = toC;
	}
	
	public Movement() {
	}
	
	public int getFromL() {
		return fromL;
	}

	public int getFromC() {
		return fromC;
	}

	public int getToL() {
		return toL;
	}

	public int getToC() {
		return toC;
	}
	
	public void setFromL(int fromL) {
		this.fromL = fromL;
	}

	public void setFromC(int fromC) {
		this.fromC = fromC;
	}

	public void setToL(int toL) {
		this.toL = toL;
	}

	public void setToC(int toC) {
		this.toC = toC;
	}

	public String toString() {
		return "("+fromL+","+fromC+")"+" ("+toL+","+toC+")";
	}
}
