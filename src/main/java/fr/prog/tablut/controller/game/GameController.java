package fr.prog.tablut.controller.game;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.controller.game.GameControllerAI;
import fr.prog.tablut.controller.game.GameControllerHuman;

public class GameController {

	private final Game game;
	private final GamePage gamePage;
	private final GameControllerAI gameControllerAI;
	private final GameControllerHuman gameControllerHuman;

	public GameController(Game game, GamePage gamePage) {
		this.game = game;
		this.gamePage = gamePage;
		this.gameControllerAI = new GameControllerAI(game, 20);
		this.gameControllerHuman = new GameControllerHuman(game, gamePage);
	}
	
	public void click(int row, int col) {
		if(this.gameControllerHuman.click(row, col))
			this.gamePage.repaint();
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
		if(gameControllerAI.tick()) {
			this.gamePage.repaint();
		}
	}

	public Game getGame() {
		return game;
	}

	public GamePage getGameWindow() {
		return gamePage;
	}

	public void undo() {
		if(this.game.undo_move()) {
			System.out.println("Undo");
			this.gamePage.repaint();
		}
		else {
			System.out.println("Can't Undo");
		}
	}

	public void redo() {
		if(this.game.redo_move()) {
			System.out.println("Redo");
			this.gamePage.repaint();
		}
		else {
			System.out.println("Can't Redo");
		}
	}
}
