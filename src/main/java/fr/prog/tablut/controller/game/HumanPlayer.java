package fr.prog.tablut.controller.game;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.game.GameWindow;

public class HumanPlayer extends Player<GameControllerHuman> {
    private int row;
    private int col;
    private GameWindow gameWindow;

    public void updateState(int row, int col, GameWindow gameWindow) {
        this.row = row;
        this.col = col;
        this.gameWindow = gameWindow;
    }

    @Override
    public void play(Game game, GameControllerHuman gameControllerHuman) {
        switch(game.getPlayingPlayerEnum()) {
            case ATTACKER:
                if(gameControllerHuman.getSelectedCell() == null) {
                    if(game.isAttackTower(row, col)) {
                        gameControllerHuman.setSelectedCell(new Couple<Integer, Integer>(row, col));
                        gameControllerHuman.mouseMoved(gameWindow.getMousePosition());
                    }
                    else {
                        return;
                    }
                }
                else {
                    if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                        gameControllerHuman.setSelectedCell(null);
                        gameWindow.getGridWindow().clearImageOnMouse();
                        gameWindow.getSouthWindow().repaint();
                    }
                }
                break;
            case DEFENDER:
                if(gameControllerHuman.getSelectedCell() == null) {
                    if(game.isDefenseTower(row, col) || game.isTheKing(row, col)) {
                        gameControllerHuman.setSelectedCell(new Couple<Integer, Integer>(row, col));
                        gameControllerHuman.mouseMoved(gameWindow.getMousePosition());
                    }
                    else {
                        return;
                    }
                }
                else {
                    if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                        gameControllerHuman.setSelectedCell(null);
                        gameWindow.getGridWindow().clearImageOnMouse();
                        gameWindow.getSouthWindow().repaint();
                    }
                }
                break;
        }
    }
}
