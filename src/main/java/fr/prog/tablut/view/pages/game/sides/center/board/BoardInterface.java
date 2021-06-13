package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.center.board.designers.*;

/**
 * The board's interface that manages all the board's view
 * @see GameInterfaceSide
 * @see Designer
 * @see BoardDrawer
 * @see BoardData
 */
public class BoardInterface extends GameInterfaceSide {
	private final BoardDesigner boardDesigner;
    private final PiecesDesigner piecesDesigner;
    private final IndicatorsDesigner indicatorsDesigner;
    private final BoardDrawer boardDrawer;
    private final BoardData boardData;

    /**
     * Creates the board's interface manager.
     * <p>It paints the board with layers, called Designers, and it communicates
     * with the game's controller and the designers thanks a BoardData.</p>
     * <p>All Designers are calling drawing methods from BoardDrawer which is drawing on the board
     * regarding to the board's component size.</p>
     * @see Designer
     * @see BoardDrawer
     * @see BoardData
     */
    public BoardInterface(int size) {
        super(new Dimension(size, size));

        boardData = new BoardData();
        boardDrawer = new BoardDrawer();

        // initialize the board drawer's data
        updateBoardDrawer();
        boardDrawer.setContainer(this);

        // organize layers
    	boardDesigner = new BoardDesigner(boardDrawer);
    	piecesDesigner = new PiecesDesigner(boardDrawer);
    	indicatorsDesigner = new IndicatorsDesigner(boardDrawer);
	}

    /**
     * When the board's interface needs to be painted,
     * it updates the boardDrawer's graphics2D object,
     * it also updates the hovered's cell, selected's
     * cell and accessible's cells knowledge on its side.
     * Then it draws the different layers it has.
     * @see BoardDrawer
     * @see BoardData
     * @see Designer
     */
    @Override
    protected void paint(Graphics2D g2d) {
        // Anti-aliased text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // update board drawer's data in the case the container changed its size
        boardDrawer.setGraphics(g2d);
        updateBoardDrawer();

        Game game = Game.getInstance();

        boardData.lastMousePosition = boardData.mousePosition;
        boardData.mousePosition = getMousePosition();

        if(boardData.previewGrid != null)
            boardData.mousePosition = null;

        boardData.hoveringCell = (boardData.mousePosition != null)?
            boardData.hoveringCell = new Point(getColFromXCoord(boardData.mousePosition.x), getRowFromYCoord(boardData.mousePosition.y)) :
            null;


        if(boardData.selectedCell != null)
			boardData.accessibleCells = Game.getInstance().getAllPossibleMovesForPosition(boardData.selectedCell.x, boardData.selectedCell.y);

        else if(boardData.mousePosition != null && game.getPlayingPlayer().isHuman()) {
			int col = getColFromXCoord(boardData.mousePosition.x);
			int row = getRowFromYCoord(boardData.mousePosition.y);

			if(game.isValid(col, row) && game.canMove(col, row)) {
				boardData.accessibleCells = game.getAllPossibleMovesForPosition(col, row);
                boardData.hoveringPossibleMoveCell = new Point(col, row);
            }
            else
                boardData.hoveringPossibleMoveCell = null;
        } else
            boardData.hoveringPossibleMoveCell = null;


        // draw board layers
        boardDesigner.draw(boardData);
    	indicatorsDesigner.draw(boardData);
    	piecesDesigner.draw(boardData);
    }

    /**
     * Updates the BoardDrawer's dimension and position
     */
    private void updateBoardDrawer() {
        boardDrawer.setBoardDimension(Math.min(getWidth(), getHeight()));
        boardDrawer.setPosition(getWidth()/2 - boardDrawer.getSize()/2, getHeight()/2 - boardDrawer.getSize()/2);
    }

    /**
     * Returns the view board's data
     * @return The view board's data
     */
    public BoardData getBoardData() {
        return boardData;
    }

    /**
     * Returns the column's index from a given pixel coord
     * @param x The x-Axis coord
     * @return The column's index
     */
    public int getColFromXCoord(int x) {
        return (x - boardDrawer.getRealX(0)) / boardDrawer.getCellSize();
    }

    /**
     * Returns the row's index from a given pixel coord
     * @param y The y-Axis coord
     * @return The row's index
     */
    public int getRowFromYCoord(int y) {
        return (y - boardDrawer.getRealY(0)) / boardDrawer.getCellSize();
    }

    /**
     * Returns the top-left corner pixel's x-Axis coord from a given column's index
     * @param x The column's index
     * @return The top-left corner pixel's x-Axis coord of a cell
     */
    public int getXCoordFromCol(int x) {
        return  boardDrawer.getRealX(0) + boardDrawer.getCellSize() * x;
    }

    /**
     * Returns the top-left corner pixel's y-Axis coord from a given row's index
     * @param y The row's index
     * @return The top-left corner pixel's y-Axis coord of a cell
     */
    public int getYCoordFromRow(int y) {
        return boardDrawer.getRealY(0) + boardDrawer.getCellSize() * y;
    }

    /**
     * Stops the current piece's animation
     */
	public void stopAnimation() {
        boardData.animatedFinalCell = null;
        boardData.animatedCell = null;
        boardData.animPosition = null;
        boardData.animatedImage = null;
        boardData.isAnimating = false;
	}

    /**
     * Updates the current piece's animation
     * @param animPosition The current animated piece's position
     * @param animatedCell The starting point cell of the animation
     * @param animatedFinalCell The ending point cell of the animation
     */
	public void updateAnimation(Point animPosition, Point animatedCell, Point animatedFinalCell) {
        boardData.animatedFinalCell = animatedFinalCell;
        boardData.animatedCell = animatedCell;
        boardData.isAnimating = true;
        boardData.animatedImage = Game.getInstance().getCellContent(boardData.animatedCell.x, boardData.animatedCell.y).getImage();
		boardData.animPosition = animPosition;
	}

    /**
     * Updates the image that's on the mouse.
     * <p>For now, it's only possible to use it for selected cells.</p>
     * @param img The image to put on the mouse
     * @param selectedCell The cell index of the selected piece
     */
	public void updateImageOnMouse(Image img, Point selectedCell) {
		boardData.imageOnMouse = img;
		boardData.selectedCell = selectedCell;
	}

    /**
     * Updates the hovered cell at a given point
     * @param hoveringCell The hovered cell's index
     */
    public void updateCellHovering(Point hoveringCell) {
        boardData.hoveringCell = hoveringCell;
    }

    /**
     * Clears the image on the mouse
     */
	public void clearImageOnMouse() {
		boardData.imageOnMouse = null;
		boardData.selectedCell = null;
	}

    /**
     * Sets the preview grid to visualize
     * @see CellContent
     * @param grid The grid to visualize
     * @param moveIndex The move's index in the move's history
     */
    public void setPreviewGrid(CellContent[][] grid, int moveIndex) {
        boardData.previewGrid = grid;
        boardData.previewMoveIndex = moveIndex;
    }

    /**
     * Removes the preview grid to visualize
     */
    public void removePreview() {
    	boardData.previewGrid = null;
    }
}