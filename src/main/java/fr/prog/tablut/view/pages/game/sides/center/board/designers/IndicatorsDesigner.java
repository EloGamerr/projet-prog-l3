package fr.prog.tablut.view.pages.game.sides.center.board.designers;

import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;

public class IndicatorsDesigner extends Designer {
    public IndicatorsDesigner(BoardDrawer bd) {
        super(bd);
    }

    @Override
    public void draw() {
        // door hovering
        /* if(cell == CellContent.GATE && accessibleCells.contains(new Couple<>(i, j))) {
            g.setColor(GameColors.GATE_FRAME_COLOR);
            g.strokeWidth(2);
            g.strokeSquare(j, i);
            g.strokeWidth(1);
        } */

        /*
        if(selectedCell != null) {
			accessibleCells = Game.getInstance().getAccessibleCells(selectedCell.getFirst(), selectedCell.getSecond());

			gridWindow.setColor(GameColors.CELL_SELECTION);
			gridWindow.fillRect(x + widthBorder + selectedCell.getSecond() * cellSize, y + widthBorder + selectedCell.getFirst() * cellSize, cellSize, cellSize);

            if(hoveringCell != null && Game.getInstance().canMove(selectedCell.getFirst(), selectedCell.getSecond(), hoveringCell.getFirst(), hoveringCell.getSecond()))
				gridWindow.fillRect(x + widthBorder + hoveringCell.getSecond() * cellSize, y + widthBorder + hoveringCell.getFirst() * cellSize, cellSize, cellSize);

			drawCircles(accessibleCells);
		}
		else if(mousePosition != null && game.getPlayingPlayer() instanceof HumanPlayer) {
			int col = getColFromXCoord(mousePosition.x);
			int row = getRowFromYCoord(mousePosition.y);

			gridWindow.setColor(GameColors.CELL_SELECTION);

			if(game.isValid(row, col) && game.canMove(row, col)) {
				accessibleCells = Game.getInstance().getAccessibleCells(row, col);

				gridWindow.fillRect(x + widthBorder + col * cellSize, y + widthBorder + row * cellSize, cellSize, cellSize);
				drawCircles(accessibleCells);
                gridWindow.setCursor(handCursor);
			}
            else {
                gridWindow.setCursor(defaultCursor);
            }
		}
        */
    }
}
