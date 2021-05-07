package fr.prog.gaufre.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SaveAdaptator extends MouseAdapter {
    Controller controller;

    public SaveAdaptator(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        controller.save();
    }
}
