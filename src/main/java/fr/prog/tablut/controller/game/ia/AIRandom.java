package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.player.PlayerEnum;

public class AIRandom extends AIMinMax {
    public AIRandom(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public double evaluation(Simulation simulation, PlayerEnum playerEnum) {
        return 0;
    }

    @Override
    public String toString() {
    	return "AIRandom";
    }
}
