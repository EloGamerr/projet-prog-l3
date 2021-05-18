package fr.prog.tablut.view.game;

import javax.swing.*;
import java.awt.*;

public class SouthWindow extends JPanel {
    public SouthWindow() {
        JLabel jLabel = new JLabel("Au tour du d\u00e9fenseur");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
        this.add(jLabel);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
