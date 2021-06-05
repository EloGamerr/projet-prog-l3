package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.Cursor;
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
    private int x, y, height, width, cellSize;
    private Image imageOnMouse;
    private Couple<Integer, Integer> selectedCell;
    private Couple<Integer, Integer> hoveringCell;
    private Couple<Integer, Integer> animCell;
    private Image animImage;
    private int xAnim, yAnim;
	private boolean anim = false;
    protected Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
    protected Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	public GridView(GridWindow gridWindow) {
		this.gridWindow = gridWindow;
	}
	
	public int cellSize() {
		return cellSize;
	}
	
	public void draw() {
		Game game = Game.getInstance();

		cellSize = (gridWindow.width() - widthBorder*2) / game.getColAmout();
	
		height = widthBorder*2 + game.getRowAmout() * cellSize + widthSeperator;
		width = widthBorder*2 + game.getColAmout() * cellSize + widthSeperator;
		
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
			gridWindow.fillRect(x + widthBorder + selectedCell.getSecond() * cellSize, y + widthBorder + selectedCell.getFirst() * cellSize, cellSize, cellSize);

            if(hoveringCell != null)
                gridWindow.fillRect(x + widthBorder + hoveringCell.getSecond() * cellSize, y + widthBorder + hoveringCell.getFirst() * cellSize, cellSize, cellSize);
		
			drawCircles(selectedCell.getFirst(), selectedCell.getSecond());
		}

		else if(mousePosition != null && !game.isWon() && game.getPlayingPlayer() instanceof HumanPlayer) {
			gridWindow.setColor(GameColors.CELL_SELECTION);

			int col = getColFromXCoord(mousePosition.x);
			int row = getRowFromYCoord(mousePosition.y);
			
			if(game.isValid(row, col) && game.canPlay(row, col)) {
				gridWindow.fillRect(x + widthBorder + col * cellSize, y + widthBorder + row * cellSize, cellSize, cellSize);
				drawCircles(row, col);
                gridWindow.setCursor(handCursor);
			}
            else {
                gridWindow.setCursor(defaultCursor);
            }
		}
		
		gridWindow.setColor(GameColors.CELL_BORDER);

		for(int i = 0 ; i <= game.getRowAmout() ; i++) {
			gridWindow.drawLine(x + widthBorder, y + widthBorder + i * cellSize, x + width - widthBorder, y + widthBorder + i * cellSize);
			gridWindow.drawLine(x + widthBorder, y + widthBorder + i * cellSize + widthSeperator + 1, x + width - widthBorder, y + widthBorder + i * cellSize + widthSeperator + 1);
		}

		for(int j = 0 ; j <= game.getColAmout() ; j++) {
			gridWindow.drawLine(x + widthBorder + j * cellSize, y + widthBorder, x + widthBorder + j * cellSize, y + height - widthBorder);
			gridWindow.drawLine(x + widthBorder + j * cellSize + widthSeperator + 1, y + widthBorder, x + widthBorder + j * cellSize + widthSeperator + 1, y + height - widthBorder);
		}
		
		gridWindow.setColor(GameColors.CELL_SEPARATOR);

		for(int i = 0 ; i <= game.getRowAmout() ; i++) {
			gridWindow.fillRect(x + widthBorder + 1, y + widthBorder + i * cellSize + 1, width - widthBorder*2, widthSeperator);
		}

		for(int j = 0 ; j <= game.getColAmout() ; j++) {
			gridWindow.fillRect(x + widthBorder + j * cellSize + 1, y + widthBorder + 1, widthSeperator, height - widthBorder*2);
		}
	}
	
	private void drawCircles(int row, int col) {
		List<Couple<Integer, Integer>> accessibleCells = Game.getInstance().getAccessibleCells(row, col);
		
		gridWindow.setColor(GameColors.CIRCLE);
		int circleRadius = cellSize/3;

		for(Couple<Integer, Integer> accessibleCell : accessibleCells) {
			gridWindow.fillOval(x + widthBorder + accessibleCell.getSecond() * cellSize + cellSize/2 - circleRadius/2 + 3, y + widthBorder + accessibleCell.getFirst() * cellSize + cellSize/2 - circleRadius/2 + 3, circleRadius, circleRadius);
		}
	}
	
	private void drawPawns() {
		Game game = Game.getInstance();

		int imgSize = cellSize/2;

		for(int i = 0 ; i < game.getRowAmout() ; i++) {
			for(int j = 0 ; j < game.getColAmout() ; j++) {
				if(selectedCell != null && selectedCell.getFirst() == i && selectedCell.getSecond() == j) continue;
				if(anim && animCell != null && animCell.getFirst() == i && animCell.getSecond() == j) continue;
				if(game.getCellContent(i, j).getImage() != null)
					gridWindow.drawImage(
                        game.getCellContent(i, j).getImage(),
                        x + widthBorder + j * cellSize - imgSize/2 + cellSize/2 + 4,
                        y + widthBorder + i * cellSize - imgSize/2 + cellSize/2 + 4,
                        imgSize,
                        imgSize
                    );
			}
		}
		
		Point mousePosition = gridWindow.getMousePosition();

		if(imageOnMouse != null && mousePosition != null) {
			int xImg = Math.max(x, mousePosition.x - imgSize/2);
			xImg = Math.min(xImg, x + width - imgSize);
			int yImg = Math.max(y, mousePosition.y - imgSize/2);
			yImg = Math.min(yImg, y + height - imgSize);
			
    		gridWindow.drawImage(imageOnMouse, xImg, yImg, imgSize, imgSize);
    	}
		
		if(anim) {
			gridWindow.drawImage(animImage, xAnim , yAnim, imgSize, imgSize);
		}
	}
	
	public void updateImageOnMouse(Image image, Couple<Integer, Integer> selectedCell) {
		imageOnMouse = image;
		this.selectedCell = selectedCell;
	}

    public void updateCellHovering(Couple<Integer, Integer> hoveringCell) {
        this.hoveringCell = hoveringCell;
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
		return (x - widthBorder - getX()) / cellSize();
	}
	
	public int getRowFromYCoord(int y) {
		return (y - widthBorder - getY()) / cellSize();
	}
	
	public int getXCoordFromCol(int c) {

		return cellSize()*c + getX() + widthBorder - cellSize/4 + cellSize/2 + 4;
	}
	
	public int getYCoordFromRow(int l) {
		return cellSize()*l+ getY() + widthBorder - cellSize/4  + cellSize/2 + 4;
	}


	public void update_anim(int toL, int toC, Couple<Integer, Integer> couple) {
		xAnim = toC;
		yAnim = toL;
		if(anim != true) {
			anim = true;
			animCell = couple;
			animImage = Game.getInstance().getCellContent(animCell.getFirst(),animCell.getSecond()).getImage();
		}
	}

	public void stop_anim() {
		if(anim == true) {
			anim = false;
		}
	}
	
	public boolean isInAnim() {
		return anim;
	}

	public void setIsInAnim(boolean b) {
		anim = b;
		
	}
	
}
