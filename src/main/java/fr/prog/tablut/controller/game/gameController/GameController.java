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
		this.gameControllerAI = new GameControllerAI(1,gamePage);
		this.gameControllerHuman = new GameControllerHuman(gamePage);
	}
	
	public void click(int row, int col) {
		if(gameControllerHuman.click(row, col))
			postPlay();
	}

	public void undoSelect() {
		gameControllerHuman.undoSelect();
	}

	public void mouseMoved(Point mousePosition) {
		gameControllerHuman.mouseMoved(mousePosition);
	}

	public Couple<Integer, Integer> getSelectedCell() {
		return gameControllerHuman.getSelectedCell();
	}

	public void tick() {
		if(gameControllerAI.tick())
			postPlay();
	}
	
	private void postPlay() {
		gamePage.enableUndoButton(!Game.getInstance().getPlays().getPreviousMovements().isEmpty());
		gamePage.enableUndoButton(!Game.getInstance().getPlays().getNextMovements().isEmpty());
		gameControllerHuman.undoSelect(); 
	}

	public void restart() {
		Game.getInstance().restart();
		gamePage.setIsInAnim(false);
		gamePage.update();
		gamePage.repaint();
	}
	

	public GamePage getGameWindow() {
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
