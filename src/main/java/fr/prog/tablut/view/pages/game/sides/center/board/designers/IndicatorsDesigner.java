package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import java.awt.Image;
import java.awt.Point;

import java.io.IOException;

import java.util.List;

import fr.prog.tablut.model.Loader;
import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;
import fr.prog.tablut.view.pages.game.sides.center.board.GameColors;

/**
 * The designer layer that draws all board's indications, decorations
 * @see Designer
 */
public class IndicatorsDesigner extends Designer {
    private Image throne = null;

    /**
     * Creates the indication/decoration designer's layer
     * @see Designer
     * @see BoardDrawer
     * @param bd The board drawer reference
     */
    public IndicatorsDesigner(BoardDrawer bd) {
        super(bd);

        try {
            throne = Loader.getImage("chess/small/throne_small.png");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(BoardData data) {
        final Point selectedCell = data.selectedCell;
        final Point hoveringCell = data.hoveringCell;
        final Point hoveringPossibleMoveCell = data.hoveringPossibleMoveCell;
        final List<Movement> accessibleCells = data.accessibleCells;

        // last move feedback
        Movement lastMove = Game.getInstance().getCurrentLastPlay();

        // if we're viewing a preview, then don't draw the last done move but the last move at given move index
        if(data.previewGrid != null) {
            lastMove = (data.previewMoveIndex > -1)? Game.getInstance().getMoveAt(data.previewMoveIndex) : null;
        }

        // draw the last move from - to
        if(lastMove != null) {
            g.setColor(GameColors.FEEDBAK_LAST_MOVE);
            g.fillSquare(lastMove.getFromC(), lastMove.getFromL()); // before
            g.fillSquare(lastMove.getToC(), lastMove.getToL()); // after
        }

        final int widthBorder = g.getBorderWidth();
        final int cellSize = g.getCellSize();
        final int x = g.getRealX(0);
        final int y = g.getRealY(0);

        // animation's indications
        if(data.isAnimating && data.animatedCell != null) {
        	g.setColor(GameColors.FROM_CELL);
        	g.fillSquare(data.animatedCell.x, data.animatedCell.y);
        	g.setColor(GameColors.TO_CELL);
        	g.fillSquare(data.animatedFinalCell.x, data.animatedFinalCell.y);

		}

        // the player has a piece on his hand : draw all possible moves
        if(!data.isAnimating && data.selectedCell != null) {
			g.setColor(GameColors.CELL_SELECTION);
			g.fillSquare(selectedCell.x, selectedCell.y);

            // The place of the piece that's selected
            if(hoveringCell != null && Game.getInstance().canMove(selectedCell.x, selectedCell.y, hoveringCell.x, hoveringCell.y))
				g.fillRect(x + widthBorder + hoveringCell.x * cellSize, y + widthBorder + hoveringCell.y * cellSize, cellSize, cellSize);

			drawAccessibleCells(accessibleCells, data.hoveringCell);

            g.setCursor("grabbing");
		}

        // The player hovers pieces
        else if(!data.isAnimating && hoveringPossibleMoveCell != null) {
            g.setColor(GameColors.CELL_SELECTION);
            g.fillSquare(hoveringPossibleMoveCell.x, hoveringPossibleMoveCell.y);
            drawAccessibleCells(accessibleCells, null);
            g.setCursor("grab");
        }

        // default state
        else {
            g.setCursor("default");
        }

        // draw the throne
        g.drawImage(throne, 4, 4, cellSize/2, cellSize/2, true);
    }

    // draw a symbol on a cell indicating that the current piece can move on that cell
    private void drawAccessibleCells(List<Movement> accessibleCells, Point hoveringCell) {
        int cellSize = g.getCellSize();
        final int r = cellSize / 3;

        g.setColor(GameColors.CIRCLE);

        int cx, cy;

		for(Movement accessibleCell : accessibleCells) {
            int x = accessibleCell.getToC();
            int y = accessibleCell.getToL();

            CellContent cell = Game.getInstance().getCellContent(x, y);

            // door hovering - if selected/hovering cell is the king and can access a door
            if(cell == CellContent.GATE) {
                g.setColor(GameColors.GATE_FRAME_COLOR);
                g.strokeWidth(2);
                g.strokeSquare(x, y);
                g.strokeWidth(1);
                g.setColor(GameColors.CIRCLE);
            }

            else {
                cx = g.getRealX(x) - r/2 + cellSize/2;
                cy = g.getRealY(y) - r/2 + cellSize/2;

                // The hovered possible move
                if(hoveringCell != null && (x == hoveringCell.x && y == hoveringCell.y)) {
                    g.setColor(GameColors.HOVERING_CIRCLE);
                    g.strokeSquare(x, y);
                }
                else {
                    g.setColor(GameColors.CIRCLE);
                }

                g.fillCircleCoords(cx, cy, r);
            }
		}
    }

    public BoardDrawer getBoardDrawer() {
        return g;
    }
}
