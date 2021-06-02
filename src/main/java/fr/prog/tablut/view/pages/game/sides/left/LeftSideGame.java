package fr.prog.tablut.view.pages.game.sides.left;

import java.awt.Dimension;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.left.moveHistory.MoveHistoryPanel;

public class LeftSideGame extends GameInterfaceSide {
    public LeftSideGame(WindowConfig config, Dimension d) {
        super(d);

        Dimension mbd = new Dimension(d.width, 40);

        add(new MoveHistoryPanel(d.width - 10, d.height - mbd.height*2 - 25));
        add(new MoveButtons(mbd));
    }
}