package fr.prog.tablut.view.components.generic;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * A component extending a JScrollPane, to create a custom UI
 * @see JScrollPane
 */
public class GenericScrollPane extends JScrollPane {
    /**
     * Creates a generic custom styled JScrollPane (for custom scrollbar)
     * @see JScrollPane
     * @param content The component to put in the scrollpane
     */
    public GenericScrollPane(Component content) {
        super(content);
        init();
    }

    /**
     * Creates a generic custom styled JScrollPane (for custom scrollbar)
     * @see JScrollPane
     * @param content The component to put in the scrollpane
     * @param vsbPolicy The vertical scrollbar's status
     * @param hsbPolicy The horizontal scrollbar's status
     */
    public GenericScrollPane(Component content, int vsbPolicy, int hsbPolicy) {
        super(content, vsbPolicy, hsbPolicy);
        init();
    }

    /**
     * Initializes the GenericScrollPane's UI
     */
    private void init() {
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));

        getVerticalScrollBar().setUI(new ScrollBarUI());
        getVerticalScrollBar().setPreferredSize(new Dimension(2, 0));
        getHorizontalScrollBar().setPreferredSize(new Dimension(0, 2));
        getVerticalScrollBar().setUnitIncrement(16);
        getHorizontalScrollBar().setUnitIncrement(16);
    }
}

/**
 * The custom scrollbar UI
 * @see BasicScrollBarUI
 */
class ScrollBarUI extends BasicScrollBarUI {
    public ScrollBarUI() {

    }

    /**
     * Creates an empty and invisible button
     * @return
     */
    protected JButton emptyButton() {
        JButton button = new JButton("");
        Dimension d = new Dimension(0, 0);
        button.setPreferredSize(d);
        button.setMinimumSize(d);
        button.setMaximumSize(d);
        return button;
    }

    /**
     * Removes the decrease button
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return emptyButton();
    }

    /**
     * Removes the increase button
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return emptyButton();
    }

    // external rod
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GenericObjectStyle.getProp("window", "background"));
        g2d.fillRect(0, 0, getTrackBounds().height, getTrackBounds().height);
    }

    // internal rod
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GenericObjectStyle.getProp("scrollbar", "color"));
        g2d.fillRect(getThumbBounds().x, getThumbBounds().y, getThumbBounds().width, getThumbBounds().height);
    }
}