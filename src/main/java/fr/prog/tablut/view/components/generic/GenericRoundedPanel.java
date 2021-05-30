package fr.prog.tablut.view.components.generic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import fr.prog.tablut.model.window.ComponentStyle;

public class GenericRoundedPanel extends JPanel {
    protected ComponentStyle style = new ComponentStyle();
    protected int borderRadius = 10;

    public GenericRoundedPanel() {
        super();
        setOpaque(false);
    }

    public void setBorderRadius(int radius) {
        if(radius < 0)
            radius = 0;
        borderRadius = radius;
    }

    public void setStyle(String style) {
        if(GenericObjectStyle.getStyle().has(style))
            this.style = GenericObjectStyle.getStyle().get(style);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliased lines and text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // This is needed on non-Mac so text is repainted correctly
        super.paintComponent(g);

        // fill
        g2d.setColor(style.get("background"));
        g2d.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);
        // stroke
        g2d.setColor(style.get("borderColor"));
        g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);

        revalidate();
    }
}
