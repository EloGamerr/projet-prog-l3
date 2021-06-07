package fr.prog.tablut.controller.game.gameController;

import java.awt.Point;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.GamePage;

public class GameControllerHuman {
    private final GamePage gamePage;
    private Point selectedCell;
    private int lastRowHovered;
    private int lastColHovered;
    
    public GameControllerHuman(GamePage gamePage) {
        this.gamePage = gamePage;
    }

    /**
	 * @return True if we should repaint the window after the click
	 */
    public boolean click(int col, int row) {
        if(!Game.getInstance().isValid(col, row) || !(Game.getInstance().getPlayingPlayer() instanceof HumanPlayer))
            return false;

        HumanPlayer humanPlayer = (HumanPlayer) Game.getInstance().getPlayingPlayer();

        humanPlayer.updateState(col, row, gamePage, this);

        return humanPlayer.play(Game.getInstance(), gamePage);
    }

    public void undoSelect() {
        selectedCell = null;
        gamePage.clearImageOnMouse();
    }


    public void mouseMoved(Point mousePosition) {
        int colHovered = gamePage.getColFromXCoord(mousePosition.x);
        int rowHovered = gamePage.getRowFromYCoord(mousePosition.y);

        if(mousePosition != null) {
            if(selectedCell != null) {
                gamePage.updateImageOnMouse(Game.getInstance().getCellContent(selectedCell.x, selectedCell.y).getImage(), selectedCell);

                Point hoveringCell = null;
                
                if(colHovered < 9 && rowHovered < 9)
                    hoveringCell = new Point(colHovered, rowHovered);

                gamePage.updateCellHovering(hoveringCell);
            }
            else if(lastRowHovered != rowHovered || lastColHovered != colHovered) {
                lastRowHovered = rowHovered;
                lastColHovered = colHovered;
                gamePage.repaint();
            }
        }
    }

    public Point getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(Point selectedCell) {
        this.selectedCell = selectedCell;
    }
}
