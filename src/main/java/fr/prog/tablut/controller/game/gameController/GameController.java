package fr.prog.tablut.controller.game.gameController;

import java.awt.Point;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.view.pages.game.GamePage;

public class GameController {

	private final GamePage gamePage;
	private final GameControllerAI gameControllerAI;
	private final GameControllerHuman gameControllerHuman;

	public GameController(GamePage gamePage) {
		this.gamePage = gamePage;
		this.gameControllerAI = new GameControllerAI(1,gamePage);
		this.gameControllerHuman = new GameControllerHuman(gamePage);
	}
	
	public void click(int col, int row) {
		if(gameControllerHuman.click(col, row)) {
			postPlay();
			checkEnd();
		}
	}

	public void undoSelect() {
		gameControllerHuman.undoSelect();
	}

	public void mouseMoved(Point mousePosition) {
		gameControllerHuman.mouseMoved(mousePosition);
	}

	public Point getSelectedCell() {
		return gameControllerHuman.getSelectedCell();
	}

	public void tick() {
		if(gameControllerAI.tick()) {
			postPlay();
			checkEnd();
		}
	}
	
	private void postPlay() {
        gamePage.updateTurn();
		gameControllerHuman.undoSelect();
	}

	private void checkEnd() {
		if(Game.getInstance().isWon()) {
			gamePage.announceWinner();
		}
	}

	public void restart() {
		Game.getInstance().restart();
		gamePage.update();
		postPlay();
		gamePage.repaint();
	}
	

	public GamePage getGamePage() {
		return gamePage;
	}

	public boolean undo() {
		if(gamePage.isInAnim())
			return false;

		if(Game.getInstance().undo_move()) {
			gamePage.togglePauseButton(Game.getInstance().isPaused());
			postPlay();
            return true;
		}

        return false;
	}

	public boolean redo() {
		if(gamePage.isInAnim())
			return false;
            
		if(Game.getInstance().redo_move()) {
			gamePage.togglePauseButton(Game.getInstance().isPaused());
			postPlay();
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
		gamePage.stop_anim();
		gamePage.togglePauseButton(true);
		postPlay();
	}
}
