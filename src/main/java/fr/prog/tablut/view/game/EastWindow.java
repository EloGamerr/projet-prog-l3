package fr.prog.tablut.view.game;

import fr.prog.tablut.view.generic.GenericButton;

import javax.swing.*;
import java.awt.*;

public class EastWindow extends JPanel {
    public EastWindow() {
        this.setBackground(new Color(0, 0, 0));
        GenericButton jButton = new GenericButton("Test");
        this.add(jButton);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
