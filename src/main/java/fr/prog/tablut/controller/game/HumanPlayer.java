package fr.prog.tablut.controller.game;

import java.awt.Point;

import fr.prog.tablut.controller.game.gameController.GameControllerHuman;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.pages.game.GamePage;

public class HumanPlayer extends Player {
    private int row;
    private int col;
    private GameControllerHuman gameControllerHuman;

    public HumanPlayer(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    public void updateState(int col, int row, GameControllerHuman gameControllerHuman) {
        this.row = row;
        this.col = col;
        this.gameControllerHuman = gameControllerHuman;
    }


    public boolean play(Game game, GamePage gamePage) {
        if(gameControllerHuman.getSelectedCell() == null) {
            if(game.canMove(col,row)) {
                gameControllerHuman.setSelectedCell(new Point(col, row));
                gameControllerHuman.mouseMoved(gamePage.getMousePosition());
            }
        }
        else if(gameControllerHuman.getSelectedCell().y == row && gameControllerHuman.getSelectedCell().x == col)
            gameControllerHuman.undoSelect();
        else
            return game.move(gameControllerHuman.getSelectedCell().x, gameControllerHuman.getSelectedCell().y, col, row);
        
        return false;
    }
}
