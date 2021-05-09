package fr.prog.gaufre.controller.adaptators;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.gaufre.controller.Controller;

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
