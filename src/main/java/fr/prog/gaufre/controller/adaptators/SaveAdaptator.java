package fr.prog.gaufre.controller.adaptators;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.gaufre.controller.Controller;

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
