package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardInterface;

public class GameMouseAdaptator extends MouseAdapter {
	private final BoardInterface gridWindow;
	private final GameController gameController;
	
	public GameMouseAdaptator(GameController gameController, BoardInterface gridWindow) {
		this.gameController = gameController;
		this.gridWindow = gridWindow;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameController.getGameWindow().isVisible())
			return;

		if(e.getButton() == MouseEvent.BUTTON1) {
			int	l = 0;//gridWindow.getRowFromYCoord(e.getY());
			int c = 0;//gridWindow.getColFromXCoord(e.getX());
			gameController.click(l, c);
		}

		else if(e.getButton() == MouseEvent.BUTTON3)
			gameController.undoSelect();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameController.getGameWindow().isVisible())
			return;

		gameController.mouseMoved(e.getPoint());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!gameController.getGameWindow().isVisible())
			return;

		gameController.mouseMoved(e.getPoint());
	}
}
