package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import fr.prog.tablut.controller.game.gameController.GameController;

import javax.swing.JOptionPane;

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

        if(!gameController.getGamePage().isVisible())
            return;

        if(this.ctrlPressed) {
            Object value;
            switch(e.getKeyCode()) {
                case KeyEvent.VK_S:
                    value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment sauvegarder la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if(value != null && (int) value == 0) {
                        gameController.save();
                    }
                    break;
                case KeyEvent.VK_Z: gameController.undo(); break;
                case KeyEvent.VK_Y: gameController.redo(); break;
                case KeyEvent.VK_N:
                    value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment recommencer la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if(value != null && (int) value == 0) {
                        gameController.restart();
                    }
                    break;
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
