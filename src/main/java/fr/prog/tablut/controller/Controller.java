package fr.prog.tablut.controller;

import java.awt.Point;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.game.GridWindow;

public class Controller {

	private final Game game;
	private final GridWindow gridWindow;
	private Couple<Integer, Integer> selectedCell;
	
	public Controller(Game game, GridWindow gridWindow) {
		this.game = game;
		this.gridWindow = gridWindow;
	}
	
	public void click(int row, int col) {
		if(!game.isValid(row, col)) return;
		
		switch(game.getPlayer()) {
			case ATTACKER:
				if(selectedCell == null) {
					if(game.isAttackTower(row, col)) {
						selectedCell = new Couple<Integer, Integer>(row, col);
						this.mouseMoved(gridWindow.getMousePosition());
					}
					else {
						return;
					}
				}
				else {
					if(game.move(selectedCell.getFirst(), selectedCell.getSecond(), row ,col)) {
						selectedCell = null;
						gridWindow.clearImageOnMouse();
					}
				}
				break;
			case DEFENDER:
				if(selectedCell == null) {
					if(game.isDefenseTower(row, col) || game.isTheKing(row, col)) {
						selectedCell = new Couple<Integer, Integer>(row, col);
						this.mouseMoved(gridWindow.getMousePosition());
					}
					else {
						return;
					}
				}
				else {
					if(game.move(selectedCell.getFirst(), selectedCell.getSecond(), row ,col)) {
						selectedCell = null;
						gridWindow.clearImageOnMouse();
					}
				}
				break;
		}
	}
	
	public Couple<Integer, Integer> getSelectedCell() {
		return selectedCell;
	}

	public void undoSelect() {
		selectedCell = null;
		gridWindow.clearImageOnMouse();
	}
	
	private int lastRowHovered;
	private int lastColHovered;
	public void mouseMoved(Point mousePosition) {
		if(mousePosition != null) {
			if(this.selectedCell != null) {
				gridWindow.updateImageOnMouse(game.getCellContent(selectedCell.getFirst(), selectedCell.getSecond()).getImage(), selectedCell);
			}
			else {
				int colHovered = gridWindow.getColFromXCoord(mousePosition.x);
				int rowHovered = gridWindow.getRowFromYCoord(mousePosition.y);
				
				if(lastRowHovered != rowHovered || lastColHovered != colHovered) {
					lastRowHovered = rowHovered;
					lastColHovered = colHovered;
					gridWindow.repaint();
				}
			}
		}
	}
}
