package fr.prog.tablut.view.pages.game.sides;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * A component that is put in a the game's page, extending JPanel.
 * @see JPanel
 */
public abstract class GameInterfaceSide extends JPanel {
    /**
     * Creates the game interface side of dimension d
     * @param d The dimension of the component
     */
    protected GameInterfaceSide(Dimension d) {
        setOpaque(false);

        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D drawable = (Graphics2D) graphics;
        paint(drawable);
    }

    protected void paint(Graphics2D drawable) {
        
    }
}