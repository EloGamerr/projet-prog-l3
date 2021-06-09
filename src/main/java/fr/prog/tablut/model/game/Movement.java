package fr.prog.tablut.model.game;

import java.awt.Point;
import java.util.Objects;

public class Movement {
	private Point pointFrom;
	private Point pointTo;

	public Movement(int fromC, int fromL, int toC, int toL) {
		this.pointFrom = new Point(fromC, fromL);
		this.pointTo = new Point(toC, toL);
	}
	
	public Movement() {
	}

	public Point getFrom() {
		return this.pointFrom;
	}

	public Point getTo() {
		return this.pointTo;
	}

	public int getFromL() {
		return this.pointFrom.y;
	}

	public int getFromC() {
		return this.pointFrom.x;
	}

	public int getToL() {
		return this.pointTo.y;
	}

	public int getToC() {
		return this.pointTo.x;
	}
	
	public void setFromL(int fromL) {
		if(this.pointFrom == null) {
			this.pointFrom = new Point();
		}

		this.pointFrom.y = fromL;
	}

	public void setFromC(int fromC) {
		if(this.pointFrom == null) {
			this.pointFrom = new Point();
		}

		this.pointFrom.x = fromC;
	}

	public void setToL(int toL) {
		if(this.pointTo == null) {
			this.pointTo = new Point();
		}

		this.pointTo.y = toL;
	}

	public void setToC(int toC) {
		if(this.pointTo == null) {
			this.pointTo = new Point();
		}

		this.pointTo.x = toC;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Movement) {
			Movement movement = (Movement)obj;

			return ((pointFrom == null && movement.pointFrom == null) || Objects.requireNonNull(pointFrom).equals(movement.pointFrom)) && ((pointTo == null && movement.pointTo == null) || Objects.requireNonNull(pointTo).equals(movement.pointTo));
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "("+getFromC()+","+getFromL()+")"+" ("+getToC()+","+getToL()+")";
	}
}
