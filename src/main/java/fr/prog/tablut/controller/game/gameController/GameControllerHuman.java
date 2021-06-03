package fr.prog.tablut.controller.game.gameController;

import java.awt.Point;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.GamePage;

public class GameControllerHuman {
    private final GamePage gamePage;
    private Couple<Integer, Integer> selectedCell;
    private int lastRowHovered;
    private int lastColHovered;
    
    public GameControllerHuman(GamePage gamePage) {
        this.gamePage = gamePage;
    }

    /**
	 * @return True if we should repaint the window after the click
	 */
    public boolean click(int row, int col) {
        if(!Game.getInstance().isValid(row, col) || !(Game.getInstance().getPlayingPlayer() instanceof HumanPlayer))
            return false;

        HumanPlayer humanPlayer = (HumanPlayer) Game.getInstance().getPlayingPlayer();

        humanPlayer.updateState(row, col, gamePage, this);

        return humanPlayer.play(Game.getInstance());
    }

    public void undoSelect() {
        selectedCell = null;
        gamePage.getGridWindow().clearImageOnMouse();
    }


    public void mouseMoved(Point mousePosition) {
        if(mousePosition != null) {
            if(this.selectedCell != null) {
                gamePage.getGridWindow().updateImageOnMouse(Game.getInstance().getCellContent(selectedCell.getFirst(), selectedCell.getSecond()).getImage(), selectedCell);
            }
            else {
                int colHovered = gamePage.getGridWindow().getColFromXCoord(mousePosition.x);
                int rowHovered = gamePage.getGridWindow().getRowFromYCoord(mousePosition.y);

                if(lastRowHovered != rowHovered || lastColHovered != colHovered) {
                    lastRowHovered = rowHovered;
                    lastColHovered = colHovered;
                    gamePage.repaint();
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
