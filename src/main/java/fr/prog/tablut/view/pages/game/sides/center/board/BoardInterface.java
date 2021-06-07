package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.center.board.designers.*;

public class BoardInterface extends GameInterfaceSide {
	private final BoardDesigner boardDesigner;
    private final PiecesDesigner piecesDesigner;
    private final IndicatorsDesigner indicatorsDesigner;
    private BoardDrawer boardDrawer;
    private BoardData boardData;
	private int x, y;
    public BoardInterface(int size) {
    	
    	
        super(new Dimension(size, size));

        boardData = new BoardData();
        boardDrawer = new BoardDrawer();

        // initialize the board drawer's data
        boardDrawer.setBoardDimension(size);
        boardDrawer.setPosition(getWidth()/2 - size/2, getHeight()/2 - size/2);

        // organize layers
    	boardDesigner = new BoardDesigner(boardDrawer);
    	piecesDesigner = new PiecesDesigner(boardDrawer);
    	indicatorsDesigner = new IndicatorsDesigner(boardDrawer);
	}

    @Override
    protected void paint(Graphics2D g2d) {
        // Anti-aliased text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // update board drawer's data in the case the container changed its size
        boardDrawer.setGraphics(g2d);
        boardDrawer.setBoardDimension(Math.min(getWidth(), getHeight()));
        boardDrawer.setPosition(getWidth()/2 - boardDrawer.getSize()/2, getHeight()/2 - boardDrawer.getSize()/2);

        Game game = Game.getInstance();

        boardData.lastMousePosition = boardData.mousePosition;
        boardData.mousePosition = getMousePosition();

        if(boardData.mousePosition == null) {
            boardData.hoveringCell = null;
        } else {
            boardData.hoveringCell = new Point(getColFromXCoord(boardData.mousePosition.x),getRowFromYCoord(boardData.mousePosition.y));
        }
        


        if(boardData.selectedCell != null) {
			boardData.accessibleCells = Game.getInstance().getAccessibleCells(boardData.selectedCell.x, boardData.selectedCell.y);
        }

        else if(boardData.mousePosition != null && game.getPlayingPlayer() instanceof HumanPlayer) {
			int col = getColFromXCoord(boardData.mousePosition.x);
			int row = getRowFromYCoord(boardData.mousePosition.y);

			if(game.isValid(col, row) && game.canMove(col, row)) {
				boardData.accessibleCells = game.getAccessibleCells(col, row);
                boardData.hoveringPossibleMoveCell = new Point(col, row);
            }
            else {
                boardData.hoveringPossibleMoveCell = null;
            }
        }
    	
        
        // draw board layers
        boardDesigner.draw(boardData);
    	indicatorsDesigner.draw(boardData);
    	piecesDesigner.draw(boardData);
    	
    }

    public BoardData getBoardData() {
        return boardData;
    }

    public int getColFromXCoord(int x) {
        return (int)((x - boardDrawer.getRealX(0)) / boardDrawer.getCellSize());   
    }

    public int getRowFromYCoord(int y) {
        return (int)((y  - boardDrawer.getRealY(0)) / boardDrawer.getCellSize());
    }

    public int getXCoordFromCol(int x) {
        return  boardDrawer.getRealX(0) + boardDrawer.getCellSize() * x ;
    }

    public int getYCoordFromRow(int y) {
        return boardDrawer.getRealY(0) + boardDrawer.getCellSize() * y;
    }

	public void stop_anim() {
		if(boardData.isAnim) {
			boardData.animatedFinalCell = null;
			boardData.animatedCell = null;
			boardData.animPosition = null;
			boardData.animatedImage = null;
			boardData.isAnim = false;
		}

	}

	public void update_anim(Point animPosition, Point animatedCell, Point animatedFinalCell) {
		if(!boardData.isAnim) {
			boardData.animatedFinalCell = animatedFinalCell;
			boardData.animatedCell = animatedCell;
			boardData.isAnim = true;
			boardData.animatedImage = Game.getInstance().getCellContent(boardData.animatedCell.x,boardData.animatedCell.y).getImage();
		}
		
		boardData.animPosition = animPosition;
		

	}

	public void updateImageOnMouse(Image img, Point selectedCell) {
		boardData.imageOnMouse = img;
		boardData.selectedCell = selectedCell;
	}
	
    public void updateCellHovering(Point hoveringCell) {
        boardData.hoveringCell = hoveringCell;
    }
	
	public void clearImageOnMouse() {
		boardData.imageOnMouse = null;
		boardData.selectedCell = null;
	}
}