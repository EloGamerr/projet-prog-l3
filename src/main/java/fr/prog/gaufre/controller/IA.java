package fr.prog.gaufre.controller;

import java.util.Random;

import fr.prog.gaufre.model.Couple;
import fr.prog.gaufre.model.Model;

public abstract class IA {
	
	protected Model model;
	protected Random random;
	private short playerNumber;
	
	public IA(Model model) {
		this.model = model;
		this.random = new Random();
		this.playerNumber = (short) (this.random.nextInt(2) + 1);
	}
	
	public abstract Couple<Integer, Integer> play();
	
	public short getPlayerNumber() {
		return this.playerNumber;
	}
}
