package fr.prog.tablut.view.north;

import javax.swing.*;
import java.awt.*;

public class NorthWindow extends JPanel {
    public NorthWindow() {
        JLabel jLabel = new JLabel("TABLUT");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        this.add(jLabel);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
