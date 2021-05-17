package fr.prog.tablut.view;

import fr.prog.tablut.model.Model;

import javax.swing.*;
import java.awt.*;

public class GridWindow extends JComponent {
	private GridView gridView;
	private int width, height;
	private Graphics2D drawable;
    
    public GridWindow(Model model) {
    	gridView = new GridView(this, model);
	}

    @Override
    protected void paintComponent(Graphics graphics) {
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

	}

	public void drawCircle(int x, int y) {

	}
}
