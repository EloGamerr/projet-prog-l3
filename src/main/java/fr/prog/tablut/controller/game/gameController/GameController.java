package fr.prog.tablut.controller.game.gameController;

import java.awt.Point;
import java.util.Map;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.MoveType;
import fr.prog.tablut.model.game.Play;
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
			postPlay(MoveType.MOVE);
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
			postPlay(MoveType.MOVE);
			checkEnd();
		}
	}
	
	private void postPlay(MoveType moveType) {
        gamePage.updateTurn(moveType);
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
		postPlay(MoveType.RESTART);
		gamePage.repaint();
	}
	

	public GamePage getGamePage() {
		return gamePage;
	}

	public boolean undo() {
		if(gamePage.isInAnim())
			return false;
		
		int numberOfUndoDone = 0;
		int numberOfUndoToDo = 1;
		
		
		// Handle undo differently if it is Human VS AI
		if(PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker()).isAI() != PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender()).isAI()) {
			// We can't undo when the AI is playing in a game Human VS AI
			if(!PlayerTypeEnum.getFromPlayer(Game.getInstance().getPlayingPlayer()).isAI()) {
				numberOfUndoToDo++;
			}
			else {
				numberOfUndoToDo = 0;
			}
		}
		
		for(int i=0; i < numberOfUndoToDo; i++) {
			if(Game.getInstance().undo_move())
				numberOfUndoDone++;
		}
		
		if(numberOfUndoDone > 0) {
			gamePage.togglePauseButton(Game.getInstance().isPaused());
			postPlay(MoveType.UNDO);
            return true;
		}

        return false;
	}

	public boolean redo() {
		if(gamePage.isInAnim())
			return false;
		
		int numberOfRedoDone = 0;
		int numberOfRedoToDo = 1;
		
		// Handle redo differently if it is Human VS AI
		if(PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker()).isAI() != PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender()).isAI()) {
			// We can't redo when the AI is playing in a game Human VS AI
			if(!PlayerTypeEnum.getFromPlayer(Game.getInstance().getPlayingPlayer()).isAI()) {
				numberOfRedoToDo++;
			}
			else {
				numberOfRedoToDo = 0;
			}
		}
		
		for(int i=0; i < numberOfRedoToDo; i++) {
			if(Game.getInstance().redo_move())
				numberOfRedoDone++;
		}
        
		if(numberOfRedoDone > 0) {
			gamePage.togglePauseButton(Game.getInstance().isPaused());
			postPlay(MoveType.REDO);
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
		gamePage.togglePauseButton(pause);
		postPlay(MoveType.PAUSE);
	}
}
