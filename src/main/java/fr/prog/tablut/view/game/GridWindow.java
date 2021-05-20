package fr.prog.tablut.view.game;

import javax.swing.*;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;

import java.awt.*;

public class GridWindow extends JPanel {
	private final GridView gridView;
	private int width, height;
	private Graphics2D drawable;
	
    public GridWindow(Game game) {
    	gridView = new GridView(this, game);
	}

    @Override
    protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

        drawable = (Graphics2D) graphics;
    	
    	width = Math.min(getSize().width, getSize().height);
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
	
	public int cellWidth(){
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
