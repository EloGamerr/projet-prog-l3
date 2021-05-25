package fr.prog.tablut.controller.game;

import java.awt.Point;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.game.GameWindow;

public class GameController {

	private final Game game;
	private final GameWindow gameWindow;
	private Couple<Integer, Integer> selectedCell;
	
	public GameController(Game game, GameWindow gameWindow) {
		this.game = game;
		this.gameWindow = gameWindow;
	}
	
	public void click(int row, int col) {
		if(!game.isValid(row, col)) return;
		
		switch(game.getPlayer()) {
			case ATTACKER:
				if(selectedCell == null) {
					if(game.isAttackTower(row, col)) {
						selectedCell = new Couple<Integer, Integer>(row, col);
						this.mouseMoved(gameWindow.getMousePosition());
					}
					else {
						return;
					}
				}
				else {
					if(game.move(selectedCell.getFirst(), selectedCell.getSecond(), row ,col)) {
						selectedCell = null;
						gameWindow.getGridWindow().clearImageOnMouse();
						gameWindow.getSouthWindow().repaint();
					}
				}
				break;
			case DEFENDER:
				if(selectedCell == null) {
					if(game.isDefenseTower(row, col) || game.isTheKing(row, col)) {
						selectedCell = new Couple<Integer, Integer>(row, col);
						this.mouseMoved(gameWindow.getMousePosition());
					}
					else {
						return;
					}
				}
				else {
					if(game.move(selectedCell.getFirst(), selectedCell.getSecond(), row ,col)) {
						selectedCell = null;
						gameWindow.getGridWindow().clearImageOnMouse();
						gameWindow.getSouthWindow().repaint();
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
		gameWindow.getGridWindow().clearImageOnMouse();
	}
	
	private int lastRowHovered;
	private int lastColHovered;
	public void mouseMoved(Point mousePosition) {
		if(mousePosition != null) {
			if(this.selectedCell != null) {
				gameWindow.getGridWindow().updateImageOnMouse(game.getCellContent(selectedCell.getFirst(), selectedCell.getSecond()).getImage(), selectedCell);
			}
			else {
				int colHovered = gameWindow.getGridWindow().getColFromXCoord(mousePosition.x);
				int rowHovered = gameWindow.getGridWindow().getRowFromYCoord(mousePosition.y);
				
				if(lastRowHovered != rowHovered || lastColHovered != colHovered) {
					lastRowHovered = rowHovered;
					lastColHovered = colHovered;
					gameWindow.repaint();
				}
			}
		}
	}

	public void tick() {

	}
}
