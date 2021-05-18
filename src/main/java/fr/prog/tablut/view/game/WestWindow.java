package fr.prog.tablut.view.game;

import javax.swing.*;

import java.awt.*;

public class WestWindow extends JPanel {

    public WestWindow() {
        this.setBackground(new Color(26, 29, 33));

        ChatWindow chatWindow = new ChatWindow();
        this.add(chatWindow);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
