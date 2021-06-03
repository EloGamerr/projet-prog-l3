package fr.prog.tablut.model.game.player;

import java.lang.reflect.InvocationTargetException;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.controller.game.ia.AIRandom;

public enum PlayerTypeEnum {
    HUMAN("Humain", HumanPlayer.class),
    EASY_AI("Ordinateur facile", AIRandom.class);
    
	private String name;
    private Class<? extends Player> playerClass;
    PlayerTypeEnum(String name, Class<? extends Player> playerClass) {
        this.name = name;
        this.playerClass = playerClass;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Player createPlayer() {
        try {
            return this.playerClass.getConstructor().newInstance();
        }
        catch(InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PlayerTypeEnum getFromPlayer(Player player) {
        for(PlayerTypeEnum playerTypeEnum : values()) {
            if(player.getClass().equals(playerTypeEnum.playerClass)) {
                return playerTypeEnum;
            }
        }

        return null;
    }

    public static Player getDefaultPlayer() {
        return HUMAN.createPlayer();
    }
    
    
}