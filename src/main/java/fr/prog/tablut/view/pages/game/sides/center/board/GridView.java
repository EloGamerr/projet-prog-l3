package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.Image;
import java.awt.Point;

import java.util.List;

import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;

public class GridView {
	private static final int widthSeperator = 6;
	public static final int widthBorder = 25;
	private final GridWindow gridWindow;
    private int x, y, height, width, cellWidth, cellHeight;
    private Image imageOnMouse;
    private Couple<Integer, Integer> selectedCell;

	public GridView(GridWindow gridWindow) {
		this.gridWindow = gridWindow;
	}
	
	public int cellHeight() {
		return cellHeight;
	}
	
	public int cellWidth() {
		return cellWidth;
	}
	
	public void draw() {
		Game game = Game.getInstance();

		cellWidth = (gridWindow.width() - widthBorder*2) / game.getColAmout();
		cellHeight = (gridWindow.height() - widthBorder*2) / game.getRowAmout();
	
		height = widthBorder*2 + game.getRowAmout() * cellHeight + widthSeperator;
		width = widthBorder*2 + game.getColAmout() * cellWidth + widthSeperator;
		
		x = gridWindow.getWidth()/2 - width/2; // getWidth() method returns the real width of the component
		y = gridWindow.getHeight()/2 - height/2; // getHeight() method returns the real height of the component
		
		if(game.isWon()) {
			clearImageOnMouse();
		}
		
		drawGrid();
		
		drawPawns();
	}
	
	private void drawGrid() {
		Game game = Game.getInstance();

		gridWindow.setColor(GameColors.BACKGROUND_GRID);
		gridWindow.fillRect(x, y, width, height);
		
		gridWindow.setColor(GameColors.BORDER_GRID);
		gridWindow.fillRect(x, y, width, widthBorder);
		gridWindow.fillRect(x, y + height - widthBorder, width, widthBorder + 2);
		gridWindow.fillRect(x, y, widthBorder, height);
		gridWindow.fillRect(x + width - widthBorder, y, widthBorder + 2, height);

		Point mousePosition = gridWindow.getMousePosition();

		if(selectedCell != null) {
			gridWindow.setColor(GameColors.CELL_SELECTION);
			gridWindow.fillRect(x + widthBorder + selectedCell.getSecond() * cellWidth, y + widthBorder + selectedCell.getFirst() * cellHeight, cellWidth, cellHeight);
		
			drawCircles(selectedCell.getFirst(), selectedCell.getSecond());
		}

		else if(mousePosition != null && !game.isWon() && game.getPlayingPlayer() instanceof HumanPlayer) {
			gridWindow.setColor(GameColors.CELL_SELECTION);

			int col = getColFromXCoord(mousePosition.x);
			int row = getRowFromYCoord(mousePosition.y);
			
			if(game.isValid(row, col) && game.canPlay(row, col)) {
				gridWindow.fillRect(x + widthBorder + col * cellWidth, y + widthBorder + row * cellHeight, cellWidth, cellHeight);
				drawCircles(row, col);
			}
		}
		
		gridWindow.setColor(GameColors.CELL_BORDER);

		for(int i = 0 ; i <= game.getRowAmout() ; i++) {
			gridWindow.drawLine(x + widthBorder, y + widthBorder + i * cellHeight, x + width - widthBorder, y + widthBorder + i * cellHeight);
			gridWindow.drawLine(x + widthBorder, y + widthBorder + i * cellHeight + widthSeperator + 1, x + width - widthBorder, y + widthBorder + i * cellHeight + widthSeperator + 1);
		}

		for(int j = 0 ; j <= game.getColAmout() ; j++) {
			gridWindow.drawLine(x + widthBorder + j * cellWidth, y + widthBorder, x + widthBorder + j * cellWidth, y + height - widthBorder);
			gridWindow.drawLine(x + widthBorder + j * cellWidth + widthSeperator + 1, y + widthBorder, x + widthBorder + j * cellWidth + widthSeperator + 1, y + height - widthBorder);
		}
		
		gridWindow.setColor(GameColors.CELL_SEPARATOR);

		for(int i = 0 ; i <= game.getRowAmout() ; i++) {
			gridWindow.fillRect(x + widthBorder + 1, y + widthBorder + i * cellHeight + 1, width - widthBorder*2, widthSeperator);
		}

		for(int j = 0 ; j <= game.getColAmout() ; j++) {
			gridWindow.fillRect(x + widthBorder + j * cellWidth + 1, y + widthBorder + 1, widthSeperator, height - widthBorder*2);
		}
	}
	
	private void drawCircles(int row, int col) {
		List<Couple<Integer, Integer>> accessibleCells = Game.getInstance().getAccessibleCells(row, col);
		
		gridWindow.setColor(GameColors.CIRCLE);
		int circleWidth = cellWidth/3;
		int circleHeight = cellHeight/3;

		for(Couple<Integer, Integer> accessibleCell : accessibleCells) {
			gridWindow.fillOval(x + widthBorder + accessibleCell.getSecond() * cellWidth + cellWidth/2 - circleWidth/2 + 3, y + widthBorder + accessibleCell.getFirst() * cellHeight + cellHeight/2 - circleHeight/2 + 3, circleWidth, circleHeight);
		}
	}
	
	private void drawPawns() {
		Game game = Game.getInstance();

		int imgWidth = cellWidth/2;
		int imgHeight = cellHeight/2;

		for(int i = 0 ; i < game.getRowAmout() ; i++) {
			for(int j = 0 ; j < game.getColAmout() ; j++) {
				if(selectedCell != null && selectedCell.getFirst() == i && selectedCell.getSecond() == j) continue;
				
				if(game.getCellContent(i, j).getImage() != null)
					gridWindow.drawImage(game.getCellContent(i, j).getImage(), x + widthBorder + j * cellWidth - imgWidth/2 + cellWidth/2 + 4, y + widthBorder + i * cellHeight - imgHeight/2 + cellHeight/2 + 4, imgWidth, imgHeight);
			}
		}
		
		Point mousePosition = gridWindow.getMousePosition();

		if(imageOnMouse != null && mousePosition != null) {
			int xImg = Math.max(x, mousePosition.x - imgWidth/2);
			xImg = Math.min(xImg, x + width - imgWidth);
			int yImg = Math.max(y, mousePosition.y - imgHeight/2);
			yImg = Math.min(yImg, y + height - imgHeight);
			
    		gridWindow.drawImage(imageOnMouse, xImg, yImg, imgWidth, imgHeight);
    	}
	}
	
	public void updateImageOnMouse(Image image, Couple<Integer, Integer> selectedCell) {
		imageOnMouse = image;
		this.selectedCell = selectedCell;
	}
	
	public void clearImageOnMouse() {
		imageOnMouse = null;
		selectedCell = null;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getColFromXCoord(int x) {
		return (x - widthBorder - getX()) / cellWidth();
	}
	
	public int getRowFromYCoord(int y) {
		return (y - widthBorder - getY()) / cellHeight();
	}
}
