package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.player.PlayerEnum;


/**
* IA de difficulté facile basée sur le nombre de pièces du joueur actuel et de son adversiare et qui ne prend pas en compte l'évasion du roi
 */
public class AIEasy extends AIMinMax {
    public AIEasy(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public double evaluation(Simulation simulation, PlayerEnum playerEnum) {
        // Calcul de la différence entre le nombre de pièces du joueur et de son adversaire
        double difference = (double) (simulation.getPlayer(playerEnum).getOwnedCells().size() - simulation.getPlayer(playerEnum.getOpponent()).getOwnedCells().size());

        // Plus la différence est grande, meilleur est le coup
        return difference;
    }

     @Override
    public String toString() {
    	return "AIEasy";
    }
}
