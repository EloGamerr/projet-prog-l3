package fr.prog.tablut.view.components.generic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import fr.prog.tablut.view.window.ComponentStyle;

/**
 * A component extending a GenericPanel, to create a rounded GenericPanel
 * @see GenericPanel
 */
public class GenericRoundedPanel extends GenericPanel {
    protected ComponentStyle style = new ComponentStyle();
    protected int borderRadius = 10;

    /**
     * Creates a GenericPanel, and make it translucent, to not see the default painting
     */
    public GenericRoundedPanel() {
        super();
    }

    /**
     * Sets the border radius of the button (the same for each border).
     * <p>radius can't be a negative integer.</p>
     * <p>If a negative integer is given, sets the radius to 0.</p>
     * @param radius The border radius
     */
    public void setBorderRadius(int radius) {
        if(radius < 0)
            radius = 0;
        borderRadius = radius;
    }

    /**
     * Sets the button's style.
     * <p>Search the ComponentStyle in the generic global stylesheet thanks its name.
     * If the component is found, then apply its style, otherwise it does not change it.</p>
     * @see ComponentStyle
     * @see GenericObjectStyle
     * @param style The style to apply
     */
    public void setStyle(String style) {
        if(GenericObjectStyle.getStyle().has(style))
            this.style = GenericObjectStyle.getStyle().get(style);
    }

    /**
     * Repaint the rounded panel
     */
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
    }
}
