package fr.prog.tablut.controller.game;

import fr.prog.tablut.model.CellContent;
import fr.prog.tablut.model.Game;
import fr.prog.tablut.model.PlayerEnum;
import fr.prog.tablut.structures.Couple;

import java.util.List;
import java.util.Random;

public class AIRandom extends AIPlayer {
    private Random random;

    public AIRandom() {
        this.random = new Random();
    }

    @Override
    public void play(Game game, GameControllerAI gameControllerAI) {
        PlayerEnum playerEnum = game.getPlayingPlayerEnum();

        CellContent cellContentToSearch = playerEnum == PlayerEnum.ATTACKER ? CellContent.ATTACK_TOWER : CellContent.DEFENSE_TOWER;

        List<Couple<Integer, Integer>> accesibleCells;
        Couple<Integer, Integer> fromCell;
        do {
            List<Couple<Integer, Integer>> cells = game.getCellContentWhereEquals(cellContentToSearch);

            if(playerEnum == PlayerEnum.DEFENDER) {
                cells.add(new Couple<>(game.getKingL(), game.getKingC()));
            }

            fromCell = cells.get(random.nextInt(cells.size()));

            accesibleCells = game.getAccessibleCells(fromCell.getFirst(), fromCell.getSecond());
        } while(accesibleCells.isEmpty());

        Couple<Integer, Integer> toCell = accesibleCells.get(random.nextInt(accesibleCells.size()));
        game.move(fromCell.getFirst(), fromCell.getSecond(), toCell.getFirst(), toCell.getSecond());
    }
}
