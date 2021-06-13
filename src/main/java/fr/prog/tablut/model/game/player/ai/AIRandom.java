package fr.prog.tablut.model.game.player.ai;

import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;

/**
* IA totalement aléatoire. Aucun choix n'est plus rentable qu'un autre
 */
public class AIRandom extends AIMinMax {
    public AIRandom(PlayerEnum playerEnum) {
        super(playerEnum, PlayerTypeEnum.RANDOM_AI);
    }

    @Override
    public double heuristic(Simulation simulation, PlayerEnum playerEnum) {
        // On renvoie une valeur constante, tous les coups auront donc une heuristique égale et le coup sera choisi aléatoirement
        return 0;
    }
}
