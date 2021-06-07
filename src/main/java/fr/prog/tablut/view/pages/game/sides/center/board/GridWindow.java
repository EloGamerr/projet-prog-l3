package fr.prog.tablut.view.pages.game.sides.center.board;

import java.awt.Color;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics2D;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

public class GridWindow extends GameInterfaceSide {
	private final GridView gridView;
	private int width, height;
	private Graphics2D drawable;
	
    public GridWindow(Game game, int size) {
		super(new Dimension(size, size));

    	gridView = new GridView(this, game);
	}

    @Override
    protected void paint(Graphics2D drawable) {
		this.drawable = drawable;
    	width = Math.min(getWidth(), getHeight());
		height = width;
    	gridView.draw();
    }
    
	public int height() {
		return height;
	}
	
	public int width() {
		return width;
	}
	
	public int cellHeight() {
		return gridView.cellHeight();
	}
	
	public int cellWidth() {
		return gridView.cellWidth();
	}

	public void setColor(Color color) {
    	drawable.setColor(color);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		drawable.drawLine(x1, y1, x2, y2);
	}

	public void fillRect(int x, int y, int width, int height) {
		drawable.fillRect(x, y, width, height);
	}
	
	public void fillOval(int x, int y, int width, int height) {
		drawable.fillOval(x, y, width, height);
	}
	
	public void drawImage(Image image, int x, int y, int width, int height) {
		drawable.drawImage(image, x, y, width, height, null);
	}
	
	public void updateImageOnMouse(Image image, Couple<Integer, Integer> selectedCell) {
		gridView.updateImageOnMouse(image, selectedCell);
		repaint();
	}
	
	public void clearImageOnMouse() {
		gridView.clearImageOnMouse();
		repaint();
	}
	
	public int getColFromXCoord(int x) {
		return gridView.getColFromXCoord(x);
	}
	
	public int getRowFromYCoord(int y) {
		return gridView.getRowFromYCoord(y);
	}
}