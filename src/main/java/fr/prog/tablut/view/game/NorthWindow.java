package fr.prog.tablut.view.game;

import fr.prog.tablut.view.generic.GenericLabel;

import javax.swing.*;
import java.awt.*;

public class NorthWindow extends JPanel {
    public NorthWindow() {
        GenericLabel jLabel = new GenericLabel("TABLUT", 20);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(jLabel);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
