package fr.prog.tablut.view.west;

import fr.prog.tablut.view.GlobalWindow;

import javax.swing.*;
import java.awt.*;

public class WestWindow extends JPanel {
    GlobalWindow parent;

    public WestWindow(GlobalWindow parent) {
        this.parent = parent;
        this.setBackground(new Color(0, 0, 0));

        ChatWindow chatWindow = new ChatWindow(this);
        this.add(chatWindow);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
}
