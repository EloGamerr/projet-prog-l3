package fr.prog.tablut.view.pages.game.regions;

import java.awt.*;

import javax.swing.*;

import fr.prog.tablut.view.components.generic.GenericLabel;

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
