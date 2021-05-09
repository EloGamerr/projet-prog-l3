package fr.prog.gaufre.controller.adaptators;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.gaufre.controller.Controller;

public class RollbackAdaptator extends MouseAdapter {
    Controller controller;

    public RollbackAdaptator(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.rollback();
    }
}
