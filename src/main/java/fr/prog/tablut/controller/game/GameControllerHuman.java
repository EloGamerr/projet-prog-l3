package fr.prog.tablut.controller.game;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.game.GameWindow;

import java.awt.*;

public class GameControllerHuman {
    private final Game game;
    private final GameWindow gameWindow;
    private Couple<Integer, Integer> selectedCell;
    private int lastRowHovered;
    private int lastColHovered;
    
    public GameControllerHuman(Game game, GameWindow gameWindow) {
        this.game = game;
        this.gameWindow = gameWindow;
    }

    public void click(int row, int col) {
        if(!game.isValid(row, col)) return;

        if(!(game.getPlayingPlayer() instanceof HumanPlayer)) return;

        HumanPlayer humanPlayer = (HumanPlayer) game.getPlayingPlayer();

        humanPlayer.updateState(row, col, gameWindow, this);
        humanPlayer.play(game);
    }

    public void undoSelect() {
        selectedCell = null;
        gameWindow.getGridWindow().clearImageOnMouse();
    }


    public void mouseMoved(Point mousePosition) {
        if(mousePosition != null) {
            if(this.selectedCell != null) {
                gameWindow.getGridWindow().updateImageOnMouse(game.getCellContent(selectedCell.getFirst(), selectedCell.getSecond()).getImage(), selectedCell);
            }
            else {
                int colHovered = gameWindow.getGridWindow().getColFromXCoord(mousePosition.x);
                int rowHovered = gameWindow.getGridWindow().getRowFromYCoord(mousePosition.y);

                if(lastRowHovered != rowHovered || lastColHovered != colHovered) {
                    lastRowHovered = rowHovered;
                    lastColHovered = colHovered;
                    gameWindow.repaint();
                }
            }
        }
    }

    public Couple<Integer, Integer> getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(Couple<Integer, Integer> selectedCell) {
        this.selectedCell = selectedCell;
    }
}
