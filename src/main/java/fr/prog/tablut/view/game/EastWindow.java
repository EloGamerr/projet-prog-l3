package fr.prog.tablut.view.game;

import javax.swing.*;
import java.awt.*;

public class EastWindow extends JPanel {
    public EastWindow() {
        this.setBackground(new Color(0, 0, 0));
        JButton jButton = new JButton("Test");
        this.add(jButton);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
