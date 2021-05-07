package fr.prog.gaufre.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
