package fr.prog.tablut.model;

public enum PlayerEnum {
	ATTACKER(),
	NONE(),
	DEFENDER();
	
	public PlayerEnum getOpponent() {
		return this == ATTACKER ? DEFENDER : ATTACKER;
	}
	
	@Override
	public String toString() {
		if(this == ATTACKER)
			return "Attacker";
		else if(this == DEFENDER)
			return "Defender";
		else
			return "None";
	}
}
