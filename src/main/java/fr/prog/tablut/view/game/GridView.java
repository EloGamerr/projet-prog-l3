package fr.prog.tablut.view.game;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.util.GameColors;

public class GridView {
	private final GridWindow gridWindow;
	private final Game game;
	private int cellWidth, cellHeight;
	public static final int widthBorder = 25;
	private static final int widthSeperator = 6;
    private Image imageOnMouse;
    private Couple<Integer, Integer> selectedCell;
    private int x, y, height, width;
	public GridView(GridWindow gridWindow, Game game) {
		this.gridWindow = gridWindow;
		this.game = game;
	}
	
	public int cellHeight(){
		return cellHeight;
	}
	
	public int cellWidth(){
		return cellWidth;
	}
	
	public void draw() {
		cellWidth = (gridWindow.width() - widthBorder*2) / this.game.getColAmout();
		cellHeight = (gridWindow.height() - widthBorder*2) / this.game.getRowAmout();
	
		height = widthBorder*2 + game.getRowAmout() * cellHeight + widthSeperator;
		width = widthBorder*2 + game.getColAmout() * cellWidth + widthSeperator;
		
		x = gridWindow.getWidth()/2 - width/2; // getWidth() method returns the real width of the component
		y = gridWindow.getHeight()/2 - height/2; // getHeight() method returns the real height of the component
		
		if(this.game.getWinner() != null) {
			this.clearImageOnMouse();
		}
		
		this.drawGrid();
		
		this.drawPawns();
	}
	
	private void drawGrid() {
		this.gridWindow.setColor(GameColors.BACKGROUND_GRID);
		this.gridWindow.fillRect(x, y, width, height);
		
		this.gridWindow.setColor(GameColors.BORDER_GRID);
		this.gridWindow.fillRect(x, y, width, widthBorder);
		this.gridWindow.fillRect(x, y + height - widthBorder, width, widthBorder + 2);
		this.gridWindow.fillRect(x, y, widthBorder, height);
		this.gridWindow.fillRect(x + width - widthBorder, y, widthBorder + 2, height);

		Point mousePosition = this.gridWindow.getMousePosition();
		if(selectedCell != null) {
			this.gridWindow.setColor(GameColors.CELL_SELECTION);
			this.gridWindow.fillRect(x + widthBorder + selectedCell.getSecond() * cellWidth, y + widthBorder + selectedCell.getFirst() * cellHeight, cellWidth, cellHeight);
		
			this.drawCircles(selectedCell.getFirst(), selectedCell.getSecond());
		}
		else if(mousePosition != null && this.game.getWinner() == null) {
			this.gridWindow.setColor(GameColors.CELL_SELECTION);
			int col = this.getColFromXCoord(mousePosition.x);
			int row = this.getRowFromYCoord(mousePosition.y);
			
			if(game.isValid(row, col) && game.canPlay(row, col)) {
				this.gridWindow.fillRect(x + widthBorder + col * cellWidth, y + widthBorder + row * cellHeight, cellWidth, cellHeight);
				
				this.drawCircles(row, col);
			}
		}
		
		this.gridWindow.setColor(GameColors.CELL_BORDER);
		for(int i = 0 ; i <= game.getRowAmout() ; i++) {
			this.gridWindow.drawLine(x + widthBorder, y + widthBorder + i * cellHeight, x + width - widthBorder, y + widthBorder + i * cellHeight);
			this.gridWindow.drawLine(x + widthBorder, y + widthBorder + i * cellHeight + widthSeperator + 1, x + width - widthBorder, y + widthBorder + i * cellHeight + widthSeperator + 1);
		}
		for(int j = 0 ; j <= game.getColAmout() ; j++) {
			this.gridWindow.drawLine(x + widthBorder + j * cellWidth, y + widthBorder, x + widthBorder + j * cellWidth, y + height - widthBorder);
			this.gridWindow.drawLine(x + widthBorder + j * cellWidth + widthSeperator + 1, y + widthBorder, x + widthBorder + j * cellWidth + widthSeperator + 1, y + height - widthBorder);
		}
		
		this.gridWindow.setColor(GameColors.CELL_SEPARATOR);
		for(int i = 0 ; i <= game.getRowAmout() ; i++) {
			this.gridWindow.fillRect(x + widthBorder + 1, y + widthBorder + i * cellHeight + 1, width - widthBorder*2, widthSeperator);
		}
		for(int j = 0 ; j <= game.getColAmout() ; j++) {
			this.gridWindow.fillRect(x + widthBorder + j * cellWidth + 1, y + widthBorder + 1, widthSeperator, height - widthBorder*2);
		}
	}
	
	private void drawCircles(int row, int col) {
		List<Couple<Integer, Integer>> accessibleCells = game.accessibleCells(row, col);
		
		this.gridWindow.setColor(GameColors.CIRCLE);
		int circleWidth = cellWidth/3;
		int circleHeight = cellHeight/3;
		for(Couple<Integer, Integer> accessibleCell : accessibleCells) {
			this.gridWindow.fillOval(x + widthBorder + accessibleCell.getSecond() * cellWidth + cellWidth/2 - circleWidth/2 + 3, y + widthBorder + accessibleCell.getFirst() * cellHeight + cellHeight/2 - circleHeight/2 + 3, circleWidth, circleHeight);
		}
	}
	
	private void drawPawns() {
		int imgWidth = cellWidth/2;
		int imgHeight = cellHeight/2;
		for(int i = 0 ; i < game.getRowAmout() ; i++) {
			for(int j = 0 ; j < game.getColAmout() ; j++) {
				if(selectedCell != null && selectedCell.getFirst() == i && selectedCell.getSecond() == j) continue;
				
				if(game.getCellContent(i, j).getImage() != null)
					this.gridWindow.drawImage(game.getCellContent(i, j).getImage(), x + widthBorder + j * cellWidth - imgWidth/2 + cellWidth/2 + 4, y + widthBorder + i * cellHeight - imgHeight/2 + cellHeight/2 + 4, imgWidth, imgHeight);
			}
		}
		
		Point mousePosition = this.gridWindow.getMousePosition();
		if(this.imageOnMouse != null && mousePosition != null) {
			int xImg = Math.max(x, mousePosition.x - imgWidth/2);
			xImg = Math.min(xImg, x + width - imgWidth);
			int yImg = Math.max(y, mousePosition.y - imgHeight/2);
			yImg = Math.min(yImg, y + height - imgHeight);
			
    		this.gridWindow.drawImage(imageOnMouse, xImg, yImg, imgWidth, imgHeight);
    	}
	}
	
	public void updateImageOnMouse(Image image, Couple<Integer, Integer> selectedCell) {
		this.imageOnMouse = image;
		this.selectedCell = selectedCell;
	}
	
	public void clearImageOnMouse() {
		this.imageOnMouse = null;
		this.selectedCell = null;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getColFromXCoord(int x) {
		return (x - widthBorder - this.getX()) / this.cellWidth();
	}
	
	public int getRowFromYCoord(int y) {
		return (y - widthBorder - this.getY()) / this.cellHeight();
	}
}
