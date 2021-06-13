package fr.prog.tablut.model.game.player.ai;

import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;


/**
* IA de difficulté facile basée sur le nombre de pièces du joueur actuel et de son adversiare
 * et qui ne prend pas en compte l'évasion du roi
 */
public class AIEasy extends AIMinMax {
    public AIEasy(PlayerEnum playerEnum) {
        super(playerEnum, PlayerTypeEnum.EASY_AI);
    }

    @Override
    public double heuristic(Simulation simulation, PlayerEnum playerEnum) {
        // Calcul de la différence entre le nombre de pièces du joueur et de son adversaire

        // Plus la différence est grande, meilleur est le coup
        return simulation.getPlayer(playerEnum).getOwnedCells().size() - simulation.getPlayer(playerEnum.getOpponent()).getOwnedCells().size();
    }
}
