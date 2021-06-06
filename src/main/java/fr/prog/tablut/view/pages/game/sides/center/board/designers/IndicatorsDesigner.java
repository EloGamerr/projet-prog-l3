package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import java.awt.Point;

import java.util.List;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;
import fr.prog.tablut.view.pages.game.sides.center.board.GameColors;

public class IndicatorsDesigner extends Designer {
    public IndicatorsDesigner(BoardDrawer bd) {
        super(bd);
    }

    @Override
    public void draw(BoardData data) {
        final Point selectedCell = data.selectedCell;
        final Point hoveringCell = data.hoveringCell;
        final Point hoveringPossibleMoveCell = data.hoveringPossibleMoveCell;
        final List<Point> accessibleCells = data.accessibleCells;

        final int widthBorder = g.getBorderWidth();
        final int cellSize = g.getCellSize();
        final int x = g.getRealX(0);
        final int y = g.getRealY(0);


        // the player has a piece on his hand : draw all possible moves
        if(selectedCell != null) {
			g.setColor(GameColors.CELL_SELECTION);
			g.fillRect(x + widthBorder + selectedCell.x * cellSize, y + widthBorder + selectedCell.y * cellSize, cellSize, cellSize);

            if(hoveringCell != null && Game.getInstance().canMove(selectedCell.y, selectedCell.x, hoveringCell.y, hoveringCell.x))
				g.fillRect(x + widthBorder + hoveringCell.x * cellSize, y + widthBorder + hoveringCell.y * cellSize, cellSize, cellSize);

			drawAccessibleCells(accessibleCells);

            // door hovering - if selected cell is the king and near from a door
            /* if(cell == CellContent.GATE && accessibleCells.contains(new Couple<>(i, j))) {
                g.setColor(GameColors.GATE_FRAME_COLOR);
                g.strokeWidth(2);
                g.strokeSquare(j, i);
                g.strokeWidth(1);
            } */
		}

        // The player hovers pieces
        else if(hoveringPossibleMoveCell != null) {
            g.setColor(GameColors.CELL_SELECTION);
            g.fillSquare(hoveringPossibleMoveCell.x, hoveringPossibleMoveCell.y);
            drawAccessibleCells(accessibleCells);
            g.setCursor("hand");
        }

        // default state
        else {
            g.setCursor("default");
        }
    }

    // draw a symbol on a cell indicating that the current piece can move on that cell
    private void drawAccessibleCells(List<Point> accessibleCells) {
        int cellSize = g.getCellSize();
        final int r = cellSize / 3;

        g.setColor(GameColors.CIRCLE);

        int cx, cy;

		for(Point accessibleCell : accessibleCells) {
            cx = g.getRealX(accessibleCell.x) - r/2 + cellSize/2;
            cy = g.getRealY(accessibleCell.y) - r/2 + cellSize/2;
		    g.fillCircleCoords(cx, cy, r);
		}
    }
}
