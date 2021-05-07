package fr.prog.gaufre.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewGameAdaptator extends MouseAdapter {
    Controller controller;

    public NewGameAdaptator(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.newGame();
    }
}
