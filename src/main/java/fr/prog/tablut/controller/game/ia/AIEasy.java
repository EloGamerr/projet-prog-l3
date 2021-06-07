package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.player.PlayerEnum;

public class AIEasy extends AIMinMax {
    public AIEasy(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public double evaluation(Simulation simulation, PlayerEnum playerEnum) {
        // Calculate the difference between the player's pieces and the opponent's pieces
        double pieceDifference = (double) (simulation.getPlayer(playerEnum).getOwnedCells().size() - simulation.getPlayer(playerEnum.getOpponent()).getOwnedCells().size());

        return pieceDifference;
    }
}
