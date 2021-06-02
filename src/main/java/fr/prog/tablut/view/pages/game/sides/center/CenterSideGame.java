package fr.prog.tablut.view.pages.game.sides.center;

import java.awt.Dimension;
import java.awt.BorderLayout;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.center.board.GridWindow;

public class CenterSideGame extends GameInterfaceSide {
    protected GridWindow gridWindow;

    public CenterSideGame(WindowConfig config, Dimension d) {
        super(d);

        setLayout(new BorderLayout());

        gridWindow = new GridWindow(d.height);

        add(gridWindow, BorderLayout.CENTER);
    }

    public GridWindow getBoard() {
        return gridWindow;
    }
}
