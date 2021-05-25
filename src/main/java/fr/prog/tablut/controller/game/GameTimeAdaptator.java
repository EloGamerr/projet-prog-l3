package fr.prog.tablut.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimeAdaptator implements ActionListener {
    private final GameController gameController;

    public GameTimeAdaptator(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.gameController.tick();
    }
}
