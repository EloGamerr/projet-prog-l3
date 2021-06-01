package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.controller.game.gameController.GameController;

public class GameTimeAdaptator implements ActionListener {
    private final GameController gameController;

    public GameTimeAdaptator(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(!gameController.getGameWindow().isVisible()) return;

        this.gameController.tick();
    }
}
