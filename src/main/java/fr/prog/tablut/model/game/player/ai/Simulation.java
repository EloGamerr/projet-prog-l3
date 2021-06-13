package fr.prog.tablut.model.game.player.ai;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;

public class Simulation extends Game {

    public Simulation(Game game) {
        super();

        this.start(PlayerTypeEnum.HUMAN, PlayerTypeEnum.HUMAN, "", "");
        this.setGrid(copyGrid(game.getGrid()));
        this.setPlayingPlayer(game.getPlayingPlayerEnum());
        this.setWinner(game.getWinner());
        this.setAttacker((Player) game.getAttacker().clone());
        this.setDefender((Player) game.getDefender().clone());
        this.setKingC(game.getKingC());
        this.setKingL(game.getKingL());
    }

    private CellContent[][] copyGrid(CellContent[][] grid) {
        if (grid == null)
            return null;
        CellContent[][] newGrid = new CellContent[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            newGrid[i] = grid[i].clone();
        }
        return newGrid;
    }
}
