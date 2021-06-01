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


    public boolean play(Game game) {
        switch(game.getPlayingPlayerEnum()) {
            case ATTACKER:
                if(gameControllerHuman.getSelectedCell() == null) {
                    if(game.isAttackTower(row, col)) {
                        gameControllerHuman.setSelectedCell(new Couple<Integer, Integer>(row, col));
                        gameControllerHuman.mouseMoved(gamePage.getMousePosition());
                    }
                }

                else if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                    gameControllerHuman.setSelectedCell(null);
                    gamePage.getGridWindow().clearImageOnMouse();
                    return true;
                }
                else {
                    if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                        gameControllerHuman.setSelectedCell(null);
                        gamePage.getGridWindow().clearImageOnMouse();
                        return true;
                    }

                }
                break;

            case DEFENDER:
                if(gameControllerHuman.getSelectedCell() == null) {
                    if(game.isDefenseTower(row, col) || game.isTheKing(row, col)) {
                        gameControllerHuman.setSelectedCell(new Couple<Integer, Integer>(row, col));
                        gameControllerHuman.mouseMoved(gamePage.getMousePosition());
                    }
                }

                else if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                    gameControllerHuman.setSelectedCell(null);
                    gamePage.getGridWindow().clearImageOnMouse();
                    return true;
                }
    
                else {
                    if(game.move(gameControllerHuman.getSelectedCell().getFirst(), gameControllerHuman.getSelectedCell().getSecond(), row ,col)) {
                        gameControllerHuman.setSelectedCell(null);
                        gamePage.getGridWindow().clearImageOnMouse();
                        return true;
                    }
                }
                break;

            default:
                break;
        }

        return false;
    }
    

    public String toString() {
    	return "HumanPlayer";
    }
    
}
