package fr.prog.tablut.controller.game.gameController;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.saver.GameSaver;
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
			this.postPlay();
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
			this.postPlay();
	}
	
	private void postPlay() {
		this.gamePage.getLeftSide().getMoveButtons().enableUndoButton(!Game.getInstance().getPlays().getPreviousMovements().isEmpty());
        this.gamePage.getLeftSide().getMoveButtons().enableRedoButton(!Game.getInstance().getPlays().getNextMovements().isEmpty());
		this.gameControllerHuman.undoSelect(); // This method will also repaint the game
	}

	public void restart() {
		Game.getInstance().restart();
		this.gamePage.update();
		this.gamePage.repaint();
	}
	

	public GamePage getGameWindow() {
		return gamePage;
	}

	public boolean undo() {
		if(Game.getInstance().undo_move()) {
			this.gamePage.getRightSide().togglePauseButton(Game.getInstance().isPaused());
			this.postPlay();
            return true;
		}

        return false;
	}

	public boolean redo() {
		if(Game.getInstance().redo_move()) {
			this.gamePage.getRightSide().togglePauseButton(Game.getInstance().isPaused());
			this.postPlay();
            return true;
		}

        return false;
	}

	public void save() {
		GameSaver.getInstance().newSave();
	}

	public void pause() {
		boolean pause = PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker()).isAI() && PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender()).isAI();

        if(pause)
			pause = !Game.getInstance().isPaused();

		Game.getInstance().setPaused(pause);

		this.gamePage.getRightSide().togglePauseButton(pause);
	}
}
