package fr.prog.tablut.view.game;

import fr.prog.tablut.view.generic.GenericLabel;

import javax.swing.*;
import java.awt.*;

public class SouthWindow extends JPanel {
    public SouthWindow() {
        GenericLabel jLabel = new GenericLabel("Au tour du d\u00e9fenseur", 15);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(jLabel);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
