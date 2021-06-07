package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.pages.game.GamePage;

public class GameMouseAdaptator extends MouseAdapter {
	private final GamePage gamePage;
	private final GameController gameController;
	
	public GameMouseAdaptator(GameController gameController, GamePage gamePage) {
		this.gameController = gameController;
		this.gamePage = gamePage;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameController.getGamePage().isVisible())
			return;

		if(e.getButton() == MouseEvent.BUTTON1) {
			int	l = gamePage.getRowFromYCoord(e.getY());
			int c = gamePage.getColFromXCoord(e.getX());
			gameController.click(c, l);
		}

		else if(e.getButton() == MouseEvent.BUTTON3)
			gameController.undoSelect();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameController.getGamePage().isVisible())
			return;

		gameController.mouseMoved(e.getPoint());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!gameController.getGamePage().isVisible())
			return;

		gameController.mouseMoved(e.getPoint());
	}
}
