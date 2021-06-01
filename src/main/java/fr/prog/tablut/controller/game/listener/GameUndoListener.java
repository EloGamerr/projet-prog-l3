package fr.prog.tablut.controller.game.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.controller.game.gameController.GameController;

public class GameUndoListener implements ActionListener {
    private final GameController gameController;

    public GameUndoListener(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameController.undo();
    }
}
