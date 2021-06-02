package fr.prog.tablut.controller.game.gameController;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.pages.game.GamePage;

public class GameController {

	private final GamePage gamePage;
	private final GameControllerAI gameControllerAI;
	private final GameControllerHuman gameControllerHuman;

	public GameController(GamePage gamePage) {
		this.gamePage = gamePage;
		this.gameControllerAI = new GameControllerAI(20);
		this.gameControllerHuman = new GameControllerHuman(gamePage);
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

		if(gameControllerAI.tick())
			this.gamePage.repaint();	

		if(gameControllerAI.tick()) {
			this.gamePage.repaint();
		}

	}

	public GamePage getGameWindow() {
		return gamePage;
	}

	public void undo() {
		if(Game.getInstance().undo_move()) {
			System.out.println("Undo");
			this.gamePage.repaint();
		}
		else {
			System.out.println("Can't Undo");
		}
	}

	public void redo() {
		if(Game.getInstance().redo_move()) {
			System.out.println("Redo");
			this.gamePage.repaint();
		}
		else {
			System.out.println("Can't Redo");
		}
	}
}
