package fr.prog.tablut.controller.game.gameController;

import java.awt.Point;
import java.util.Objects;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.MoveType;
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

	@SuppressWarnings("UnusedReturnValue")
	public boolean undo() {
		int numberOfUndoDone = 0;
		int numberOfUndoToDo = 1;
		
		
		// Handle undo differently if it is Human VS AI
		if(Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker())).isAI() != Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender())).isAI()) {
			// We can't undo when the AI is playing in a game Human VS AI
			if(!Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getPlayingPlayer())).isAI()) {
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
			gamePage.setIsInAnim(false);
			postPlay(MoveType.UNDO);
            return true;
		}

        return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean redo() {
		int numberOfRedoDone = 0;
		int numberOfRedoToDo = 1;
		
		// Handle redo differently if it is Human VS AI
		if(Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker())).isAI() != Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender())).isAI()) {
			// We can't redo when the AI is playing in a game Human VS AI
			if(!Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getPlayingPlayer())).isAI()) {
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
			gamePage.setIsInAnim(false);
			postPlay(MoveType.REDO);
            return true;
		}

        return false;
	}

	public void save() {
		GameSaver.getInstance().newSave();
	}

	public void pause() {
		boolean pause = Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker())).isAI() && Objects.requireNonNull(PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender())).isAI();
		
        if(pause)
			pause = !Game.getInstance().isPaused();
        
		Game.getInstance().setPaused(pause);
		gamePage.stop_anim();
		gamePage.togglePauseButton(pause);
		postPlay(MoveType.PAUSE);
	}
}
