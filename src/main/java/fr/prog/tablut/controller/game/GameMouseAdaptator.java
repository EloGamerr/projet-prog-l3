package fr.prog.tablut.controller.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.tablut.view.pages.game.grid.GridWindow;

public class GameMouseAdaptator extends MouseAdapter {
	private final GridWindow gridWindow;
	private final GameController gameController;
	
	public GameMouseAdaptator(GameController gameController, GridWindow gridWindow) {
		this.gameController = gameController;
		this.gridWindow = gridWindow;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameController.getGameWindow().isVisible()) return;

		if(e.getButton() == MouseEvent.BUTTON1) {
			int	l = gridWindow.getRowFromYCoord(e.getY());
			int c = gridWindow.getColFromXCoord(e.getX());
			gameController.click(l, c);
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
			gameController.undoSelect();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameController.getGameWindow().isVisible()) return;

		gameController.mouseMoved(e.getPoint());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!gameController.getGameWindow().isVisible()) return;

		gameController.mouseMoved(e.getPoint());
	}
}
