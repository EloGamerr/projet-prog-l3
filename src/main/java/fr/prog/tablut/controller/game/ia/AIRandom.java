package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.player.PlayerEnum;

/**
* IA totalement aléatoire. Aucun choix n'est plus rentable qu'un autre
 */
public class AIRandom extends AIMinMax {
    public AIRandom(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public double evaluation(Simulation simulation, PlayerEnum playerEnum) {
        // On renvoie une valeur constante, tous les coups auront donc une heuristique égale et le coup sera choisi aléatoirement
        return 0;
    }

    @Override
    public String toString() {
    	return "AIRandom";
    }
}
