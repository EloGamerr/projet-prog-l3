package fr.prog.tablut.view;

import fr.prog.tablut.model.Model;

public class GridView {
	private GridWindow gridWindow;
	private Model model;
	private int cellWidth, cellHeight;

	public GridView(GridWindow gridWindow, Model model) {
		this.gridWindow = gridWindow;
		this.model = model;
	}
	
	public int cellHeight(){
		return cellHeight;
	}
	
	public int cellWidth(){
		return cellWidth;
	}
	
	public void draw() {
		cellWidth = gridWindow.width() / this.model.colAmount();
		cellHeight = gridWindow.height() / this.model.rowAmount();
		
		this.gridWindow.drawGrid();
	}
}
