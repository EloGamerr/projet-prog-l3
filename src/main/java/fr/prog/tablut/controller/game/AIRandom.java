package fr.prog.tablut.controller.game;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;

import java.util.List;
import java.util.Random;

public class AIRandom extends AIPlayer {
    private Random random;

    public AIRandom() {
        this.random = new Random();
    }

    @Override
    public void play(Game game) {
        List<Couple<Integer, Integer>> accesibleCells;
        Couple<Integer, Integer> fromCell;
        do {
            List<Couple<Integer, Integer>> ownedCells = this.getOwnedCells();

            fromCell = ownedCells.get(random.nextInt(ownedCells.size()));

            accesibleCells = game.getAccessibleCells(fromCell.getFirst(), fromCell.getSecond());
        } while(accesibleCells.isEmpty());

        System.out.println("AI Play");
        Couple<Integer, Integer> toCell = accesibleCells.get(random.nextInt(accesibleCells.size()));
        game.move(fromCell.getFirst(), fromCell.getSecond(), toCell.getFirst(), toCell.getSecond());
    }
}
