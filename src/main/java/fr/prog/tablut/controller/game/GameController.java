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
		this.gameControllerHuman.click(row, col);
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
		gameControllerAI.tick();
	}

	public Game getGame() {
		return game;
	}

	public GameWindow getGameWindow() {
		return gameWindow;
	}
}
