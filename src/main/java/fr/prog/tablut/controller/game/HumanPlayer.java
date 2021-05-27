package fr.prog.tablut.controller.game;


import fr.prog.tablut.model.Game;
import fr.prog.tablut.model.Player;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.game.GameWindow;

public class HumanPlayer extends Player {
    private int row;
    private int col;
    private GameWindow gameWindow;
    private GameControllerHuman gameControllerHuman;
    
    public void updateState(int row, int col, GameWindow gameWindow, GameControllerHuman gameControllerHuman) {
        this.row = row;
        this.col = col;
        this.gameWindow = gameWindow;
        this.gameControllerHuman = gameControllerHuman;
    }




    public boolean play(Game game) {
        switch(game.getPlayingPlayerEnum()) {
            case ATTACKER:
                if(gameControllerHuman.getSelectedCell() == null) {
                    if(game.isAttackTower(row, col)) {
                        gameControllerHuman.setSelectedCell(new Couple<Integer, Integer>(row, col));
                        gameControllerHuman.mouseMoved(gameWindow.getMousePosition());
                    }
                }

                else if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                    gameControllerHuman.setSelectedCell(null);
                    gameWindow.getGridWindow().clearImageOnMouse();
                    return true;
                }
                else {
                    if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                        gameControllerHuman.setSelectedCell(null);
                        gameWindow.getGridWindow().clearImageOnMouse();
                        return true;
                    }

                }
                break;
            case DEFENDER:
                if(gameControllerHuman.getSelectedCell() == null) {
                    if(game.isDefenseTower(row, col) || game.isTheKing(row, col)) {
                        gameControllerHuman.setSelectedCell(new Couple<Integer, Integer>(row, col));
                        gameControllerHuman.mouseMoved(gameWindow.getMousePosition());
                    }
                }

                else if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                    gameControllerHuman.setSelectedCell(null);
                    gameWindow.getGridWindow().clearImageOnMouse();
                    return true;
                }
                
      
        
    
                else {
                    if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                        gameControllerHuman.setSelectedCell(null);
                        gameWindow.getGridWindow().clearImageOnMouse();
                        return true;
                    }
                }
                break;
        }

        return false;
    }
    

    public String toString() {
    	// TODO Auto-generated method stub
    	return "HumanPlayer";
    }
    
}
