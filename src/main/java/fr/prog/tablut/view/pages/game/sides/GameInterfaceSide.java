package fr.prog.tablut.view.pages.game.sides;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;

import fr.prog.tablut.view.components.generic.GenericPanel;

/**
 * A component that is put in a the game's page, extending GenericPanel.
 * @see GenericPanel
 */
public abstract class GameInterfaceSide extends GenericPanel {
    /**
     * Creates the game interface side of dimension d
     * @param d The dimension of the component
     */
    protected GameInterfaceSide(Dimension d) {
        super();
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