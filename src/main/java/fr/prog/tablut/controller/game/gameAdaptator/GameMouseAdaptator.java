package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.tablut.controller.game.gameController.GameController;

public class GameMouseAdaptator extends MouseAdapter {
	private final GameController gameController;
    private boolean isMouseDown = false;

	public GameMouseAdaptator(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameController.getGamePage().isVisible())
			return;

		if(e.getButton() == MouseEvent.BUTTON1 && !isMouseDown) {
			gameController.drag(e.getPoint());
            isMouseDown = true;
		}
	}

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameController.getGamePage().isVisible())
			return;

        if(e.getButton() == MouseEvent.BUTTON1 && isMouseDown) {
            gameController.drop(e.getPoint());
            isMouseDown = false;
        }
    }

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMove(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMove(e);
	}

    private void mouseMove(MouseEvent e) {
        if(!gameController.getGamePage().isVisible())
            return;

        gameController.mouseMoved(e.getPoint());
    }
}
