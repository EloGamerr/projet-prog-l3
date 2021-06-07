package fr.prog.tablut.model.game.player;

import java.lang.reflect.InvocationTargetException;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.controller.game.ia.AIEasy;
import fr.prog.tablut.controller.game.ia.AIMedium;
import fr.prog.tablut.controller.game.ia.AIRandom;

public enum PlayerTypeEnum {
    HUMAN("Humain", HumanPlayer.class, false),
    RANDOM_AI("Ordinateur al\u00e9atoire", AIRandom.class, true),
    EASY_AI("Ordinateur facile", AIEasy.class, true),
    MEDIUM_AI("Ordinateur moyen", AIMedium.class, true);

    
	private String name;
    private Class<? extends Player> playerClass;
    private boolean isAI;
    
    PlayerTypeEnum(String name, Class<? extends Player> playerClass, boolean isAI) {
        this.name = name;
        this.playerClass = playerClass;
        this.isAI = isAI;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Player createPlayer(PlayerEnum playerEnum) {
        try {
            return this.playerClass.getConstructor(PlayerEnum.class).newInstance(playerEnum);
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

    public static Player getDefaultPlayer(PlayerEnum playerEnum) {
        return HUMAN.createPlayer(playerEnum);
    }
    
    public boolean isAI() {
        return this.isAI;
    }
}