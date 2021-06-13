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
		this.gameControllerAI = new GameControllerAI(300, gamePage);
		this.gameControllerHuman = new GameControllerHuman(gamePage);
	}

    public void drag(Point p) {
        if(gamePage.isInAnim())
            return;

        Point cp = gamePage.getCellFromCoord(p);

        if(getSelectedCell() == null && Game.getInstance().canMove(cp.x, cp.y)) {
            setSelectedCell(cp);
            mouseMoved(p);
            gamePage.update(MoveType.MOVE);
        }
    }

    public void drop(Point p) {
        if(gamePage.isInAnim())
            return;

        Point cp = gamePage.getCellFromCoord(p);

        Point sc = getSelectedCell();

        if(sc != null) {
            boolean played = gameControllerHuman.play(cp.x, cp.y);

            undoSelect();

            if(played) {
                gamePage.updateTurn(MoveType.MOVE);
			    checkEnd();
            } else {
                gamePage.update(MoveType.NONE);
            }
        }
    }

	public void undoSelect() {
		gameControllerHuman.undoSelect();
	}

	public void mouseMoved(Point mousePosition) {
        if(!gamePage.isInAnim())
		    gameControllerHuman.mouseMoved(mousePosition);
	}

	public Point getSelectedCell() {
		return gameControllerHuman.getSelectedCell();
	}

    public void setSelectedCell(Point p) {
        gameControllerHuman.setSelectedCell(p);
    }

	public void tick() {
        // try to play or created the animation
        if(gameControllerAI.tick()) {
            // just played a move - waiting for aniamtion
            if(gameControllerAI.isAnimationPending()) {
                gamePage.setIsInAnim(true);
                gamePage.update(MoveType.NONE);
            }
        }

        else if(gameControllerAI.isAnimationProcessing()) {
            if(gameControllerAI.tickAnimation()) {
                gamePage.update(MoveType.NONE);
            }
            // animation has ended : change turn
            else {
                Game.getInstance().confirmMove();
                gamePage.setIsInAnim(false);
                gamePage.updateTurn(MoveType.MOVE);
                checkEnd();
            }
        }
	}

	private void checkEnd() {
		if(Game.getInstance().isWon()) {
			gamePage.announceWinner();
		}
	}

	public void restart() {
		Game.getInstance().restart();
		gamePage.update();
		gamePage.updateTurn(MoveType.RESTART);
		gamePage.repaint();
	}


	public GamePage getGamePage() {
		return gamePage;
	}

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
			gamePage.updateTurn(MoveType.UNDO);
            checkEnd();
            return true;
		}

        return false;
	}

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
			gamePage.updateTurn(MoveType.REDO);
            checkEnd();
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
		gamePage.stopAnimation();
		gamePage.togglePauseButton(pause);
		gamePage.updateTurn(MoveType.PAUSE);
        checkEnd();
	}
}
