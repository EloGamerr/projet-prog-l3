package fr.prog.tablut.view.pages.game.sides.left;

import java.awt.Dimension;

import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.left.moveHistory.MoveHistoryPanel;

public class LeftSideGame extends GameInterfaceSide {
    private final MoveHistoryPanel historyPanel;
    private final MoveButtons buttons;

    public LeftSideGame(WindowConfig config, GameController gameController, Dimension d) {
        super(d);

        Dimension mbd = new Dimension(d.width, 40);

        historyPanel = new MoveHistoryPanel(d.width - 10, d.height - mbd.height*2 - 25);

        buttons = new MoveButtons(mbd,gameController);

        add(historyPanel);
        add(buttons);
    }

    public void update() {
        historyPanel.update();
    }
}