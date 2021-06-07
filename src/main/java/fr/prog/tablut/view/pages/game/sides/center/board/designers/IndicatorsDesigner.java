package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import java.awt.Image;
import java.awt.Point;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.List;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardData;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;
import fr.prog.tablut.view.pages.game.sides.center.board.GameColors;

public class IndicatorsDesigner extends Designer {
    private Image throne = null;

    public IndicatorsDesigner(BoardDrawer bd) {
        super(bd);

        try {
            throne = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("images/chess/small/throne_small.png"));
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
        final List<Point> accessibleCells = data.accessibleCells;

        final int widthBorder = g.getBorderWidth();
        final int cellSize = g.getCellSize();
        final int x = g.getRealX(0);
        final int y = g.getRealY(0);


        g.drawImage(throne, 4, 4, cellSize/2, cellSize/2, true);
        
        if(data.isAnim) {
        	g.setColor(GameColors.FROM_CELL);
        	g.fillSquare(data.animatedCell.x, data.animatedCell.y);
        	g.setColor(GameColors.TO_CELL);
        	g.fillSquare(data.animatedFinalCell.x, data.animatedFinalCell.y);
        	
		}
        
        // the player has a piece on his hand : draw all possible moves
        if(!data.isAnim && data.selectedCell != null) {
			g.setColor(GameColors.CELL_SELECTION);
			g.fillRect(x + widthBorder + selectedCell.x * cellSize, y + widthBorder + selectedCell.y * cellSize, cellSize, cellSize);

            if(hoveringCell != null && Game.getInstance().canMove(selectedCell.y, selectedCell.x, hoveringCell.y, hoveringCell.x))
				g.fillRect(x + widthBorder + hoveringCell.x * cellSize, y + widthBorder + hoveringCell.y * cellSize, cellSize, cellSize);

			drawAccessibleCells(accessibleCells);
		}

        // The player hovers pieces
        else if(!data.isAnim && hoveringPossibleMoveCell != null) {
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
            CellContent cell = Game.getInstance().getCellContent(accessibleCell.y, accessibleCell.x);

            // door hovering - if selected/hovering cell is the king and can access a door
            if(cell == CellContent.GATE) {
                g.setColor(GameColors.GATE_FRAME_COLOR);
                g.strokeWidth(2);
                g.strokeSquare(accessibleCell.x, accessibleCell.y);
                g.strokeWidth(1);
                g.setColor(GameColors.CIRCLE);
            }

            else {
                cx = g.getRealX(accessibleCell.x) - r/2 + cellSize/2;
                cy = g.getRealY(accessibleCell.y) - r/2 + cellSize/2;
                g.fillCircleCoords(cx, cy, r);
            }
		}
    }
}
