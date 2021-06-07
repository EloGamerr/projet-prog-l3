package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.Player;

public class Simulation extends Game implements Cloneable {

    public Simulation(Game game) {
        this.start(new HumanPlayer(), new HumanPlayer());
        this.setGrid(copyGrid(game.getGrid()));
        this.setPlayingPlayer(game.getPlayingPlayerEnum());
        this.setWinner(game.getWinner());
        this.setAttacker((Player) game.getAttacker().clone());
        this.setDefender((Player) game.getDefender().clone());
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() {
        return new Simulation(this);
    }
}
