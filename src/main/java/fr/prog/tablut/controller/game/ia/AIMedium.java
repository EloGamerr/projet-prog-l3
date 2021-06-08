package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.player.PlayerEnum;

/**
* IA de difficulté moyenne prenant en compte le nombre de pièces du joueur actuel, de son adversaire
 * et l'évasion du roi
 */
public class AIMedium extends AIHard {
    public AIMedium(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    /**
     * Fonction calculant l'heuristique d'une simulation
     */
    public double heuristic(Simulation simulation, PlayerEnum playerEnum) {
        int ownedCellsAmountPlayer = simulation.getPlayer(playerEnum).getOwnedCells().size();
        PlayerEnum opponent = playerEnum.getOpponent();
        int ownedCellsAmountOpponent = simulation.getPlayer(opponent).getOwnedCells().size();

        /*
         * On doit gérer l'évaluation différement selon si le joueur actuel est un défenseur ou un attaquant
         *
         */
        double result;
        if (playerEnum == PlayerEnum.DEFENDER) {
            //Le défenseur cherche à garder le plus de tours possibles et d'éliminer le plus de tours adverse
            //On essaie de rapprocher au maximum le roi d'une des portes de sortie
            result = ownedCellsAmountPlayer - ownedCellsAmountOpponent + 5 * amountOfkingMovesToCorner(simulation, 100, 2, 0.5);
        } else {
            //L'attaquant cherche à garder le plus de tours possibles et d'éliminer le plus de tours adverse
            //On cherche aussi à éviter que le roi s'approche trop des portes de sortie
            result = 1.5 * (ownedCellsAmountPlayer - ownedCellsAmountOpponent) - 2 * amountOfkingMovesToCorner(simulation, 100, 2, 0.5);
        }

        return result;
    }
}