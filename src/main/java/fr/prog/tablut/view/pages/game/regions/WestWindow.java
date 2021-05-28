package fr.prog.tablut.view.pages.game.regions;

import java.awt.*;

import javax.swing.*;

import fr.prog.tablut.view.pages.game.ChatWindow;

public class WestWindow extends JPanel {

    public WestWindow() {
        setBackground(new Color(26, 29, 33));

        ChatWindow chatWindow = new ChatWindow();
        add(chatWindow);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
