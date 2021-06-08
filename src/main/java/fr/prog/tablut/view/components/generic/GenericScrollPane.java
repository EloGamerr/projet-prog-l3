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

public class GenericScrollPane extends JScrollPane {
    public GenericScrollPane(Component content) {
        super(content);
        init();
    }

    public GenericScrollPane(Component content, int vsbPolicy, int hsbPolicy) {
        super(content, vsbPolicy, hsbPolicy);
        init();
    }

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

class ScrollBarUI extends BasicScrollBarUI {
    public ScrollBarUI() {

    }

    protected JButton createZeroButton() {
        JButton button = new JButton("zero button");
        Dimension zeroDim = new Dimension(0,0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    // barre exterieure
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GenericObjectStyle.getProp("window", "background"));
        g2d.fillRect(0, 0, getTrackBounds().height, getTrackBounds().height);
    }

    // barre interieure
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GenericObjectStyle.getProp("scrollbar", "color"));
        g2d.fillRect(getThumbBounds().x, getThumbBounds().y, getThumbBounds().width, getThumbBounds().height);
    }
}