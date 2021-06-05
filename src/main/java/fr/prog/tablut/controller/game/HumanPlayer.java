package fr.prog.tablut.controller.game;

import fr.prog.tablut.controller.game.gameController.GameControllerHuman;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.GamePage;

public class HumanPlayer extends Player {
    private int row;
    private int col;
    private GamePage gamePage;
    private GameControllerHuman gameControllerHuman;
    
    public void updateState(int row, int col, GamePage gamePage, GameControllerHuman gameControllerHuman) {
        this.row = row;
        this.col = col;
        this.gamePage = gamePage;
        this.gameControllerHuman = gameControllerHuman;
    }


    public boolean play(Game game, GamePage gamePage) {
        if(gameControllerHuman.getSelectedCell() == null) {
            if(game.canMove(row, col)) {
                gameControllerHuman.setSelectedCell(new Couple<Integer, Integer>(row, col));
                gameControllerHuman.mouseMoved(gamePage.getMousePosition());
            }
        }
        else if(gameControllerHuman.getSelectedCell().getFirst() == row && gameControllerHuman.getSelectedCell().getSecond() == col)
            gameControllerHuman.undoSelect();
        else
            return game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col);
        
        return false;
    }
    

    public String toString() {
    	return "HumanPlayer";
    }
    
}
