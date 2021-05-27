package fr.prog.tablut.controller.game;

import java.awt.Point;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.game.GameWindow;

public class GameController {

	private final Game game;
	private final GameWindow gameWindow;
	private final GameControllerAI gameControllerAI;
	private final GameControllerHuman gameControllerHuman;

	public GameController(Game game, GameWindow gameWindow) {
		this.game = game;
		this.gameWindow = gameWindow;
		this.gameControllerAI = new GameControllerAI(game, 20);
		this.gameControllerHuman = new GameControllerHuman(game, gameWindow);
	}
	
	public void click(int row, int col) {
		if(this.gameControllerHuman.click(row, col))
			this.gameWindow.repaint();
	}

	public void undoSelect() {
		this.gameControllerHuman.undoSelect();
	}

	public void mouseMoved(Point mousePosition) {
		this.gameControllerHuman.mouseMoved(mousePosition);
	}

	public Couple<Integer, Integer> getSelectedCell() {
		return this.gameControllerHuman.getSelectedCell();
	}

	public void tick() {

		if(gameControllerAI.tick())
			this.gameWindow.repaint();	

		if(gameControllerAI.tick()) {
			this.gameWindow.repaint();
		}

	}

	public Game getGame() {
		return game;
	}

	public GameWindow getGameWindow() {
		return gameWindow;
	}

	public void undo() {
		if(this.game.undo_move()) {
			System.out.println("Undo");
			this.gameWindow.repaint();
		}
		else {
			System.out.println("Can't Undo");
		}
	}

	public void redo() {
		if(this.game.redo_move()) {
			System.out.println("Redo");
			this.gameWindow.repaint();
		}
		else {
			System.out.println("Can't Redo");
		}
	}
}
