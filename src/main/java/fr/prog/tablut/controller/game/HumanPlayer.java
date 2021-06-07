package fr.prog.tablut.controller.game;

import java.awt.Point;

import fr.prog.tablut.controller.game.gameController.GameControllerHuman;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.view.pages.game.GamePage;

public class HumanPlayer extends Player {
    private int row;
    private int col;
    private GamePage gamePage;
    private GameControllerHuman gameControllerHuman;
    
    public void updateState(int col, int row, GamePage gamePage, GameControllerHuman gameControllerHuman) {
        this.row = row;
        this.col = col;
        this.gamePage = gamePage;
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
    

    public String toString() {
    	return "HumanPlayer";
    }
    
}
