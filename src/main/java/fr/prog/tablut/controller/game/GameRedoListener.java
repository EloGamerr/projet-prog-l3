package fr.prog.tablut.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameRedoListener implements ActionListener {
    private final GameController gameController;

    public GameRedoListener(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameController.redo();
    }
}
