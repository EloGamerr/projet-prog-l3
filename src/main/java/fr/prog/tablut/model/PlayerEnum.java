package fr.prog.tablut.model;

public enum PlayerEnum {
	ATTACKER(),
	DEFENDER();
	
	public PlayerEnum getOpponent() {
		return this == ATTACKER ? DEFENDER : ATTACKER;
	}
	
	@Override
	public String toString() {
		return this == ATTACKER ? "L'attaquant" : "Le défenseur";
	}
}
