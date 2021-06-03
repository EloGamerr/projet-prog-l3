package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fr.prog.tablut.controller.game.gameController.GameController;

public class GameKeyAdaptator extends KeyAdapter {
    private final GameController gameController;
    private boolean ctrlPressed;

    public GameKeyAdaptator(GameController gameController) {
        this.gameController = gameController;
        this.ctrlPressed = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
            this.ctrlPressed = true;
            return;
        }

        if(!gameController.getGameWindow().isVisible())
            return;

        if(this.ctrlPressed) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_S: gameController.save(); break;
                case KeyEvent.VK_Z: gameController.undo(); break;
                case KeyEvent.VK_Y: gameController.redo(); break;
                case KeyEvent.VK_N: gameController.restart(); break;
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) gameController.pause();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
            this.ctrlPressed = false;
        }
    }
}
