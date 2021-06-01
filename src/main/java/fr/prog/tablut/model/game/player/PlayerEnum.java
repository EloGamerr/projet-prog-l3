package fr.prog.tablut.model.game.player;

public enum PlayerEnum {
	NONE("None"),
	ATTACKER("Attacker"),
	DEFENDER("Defender");

	private final String name;

	PlayerEnum(final String name) {
		this.name = name;
	}
	
	public PlayerEnum getOpponent() {
		return (this == NONE)? 
			NONE :
		(this == ATTACKER)?
			DEFENDER :
			ATTACKER;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
