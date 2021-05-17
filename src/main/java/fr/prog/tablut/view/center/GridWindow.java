package fr.prog.tablut.view.center;

import fr.prog.tablut.model.Model;

import javax.swing.*;
import java.awt.*;

public class GridWindow extends JPanel {
	private GridView gridView;
	private int width, height;
	private Graphics2D drawable;
    
    public GridWindow(Model model) {
    	gridView = new GridView(this, model);
	}

    @Override
    protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

        drawable = (Graphics2D) graphics;
        
    	width = getSize().width;
    	height = getSize().height;

    	gridView.draw();
    }
	
	public int height() {
		return width;
	}
	
	public int width() {
		return height;
	}
	
	public int cellHeight() {
		return gridView.cellHeight();
	}
	
	public int cellWidth(){
		return gridView.cellWidth();
	}

	public void drawGrid() {
    	drawable.setColor(new Color(127, 127, 127));
		drawable.fillRect(0, 0, width, height);
	}

	public void drawCircle(int x, int y) {

	}
}
