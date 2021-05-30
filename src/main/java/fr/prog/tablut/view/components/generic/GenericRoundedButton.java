package fr.prog.tablut.view.components.generic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;

import javax.swing.BorderFactory;

import fr.prog.tablut.model.window.WindowName;


// Source : Adapted from http://www.bryanesmith.com/docs/rounded-jbuttons

public class GenericRoundedButton extends GenericButton {
    protected int paddingWidth = 60;
    protected int paddingHeight = 20;
    protected int borderRadius = 12;

    protected Rectangle2D textBounds;
    protected float labelX = 0;
    protected float labelY = 0;


    public GenericRoundedButton() {
        super();
        init();
    }

    public GenericRoundedButton(Dimension size) {
        super();
        init(size);
    }
    
    public GenericRoundedButton(int width, int height) {
        super();
        init(new Dimension(width, height));
    }

    public GenericRoundedButton(String title) {
        super(title);
        init();
    }

    public GenericRoundedButton(String title, Dimension size) {
        super(title);
        init(size);
    }

    public GenericRoundedButton(String title, int width, int height) {
        super(title);
        init(new Dimension(width, height));
    }

    public GenericRoundedButton(String title, WindowName href) {
        super(title);
        init(null);
        setHref(href);
    }

    private void init() {
        init(null);
    }

    private void init(Dimension size) {
        setBackground(GenericObjectStyle.getProp("button", "background"));
        setBorder(BorderFactory.createEmptyBorder());
        setFocusable(false);

        // set label size
        FontRenderContext frc = new FontRenderContext(null, false, false);
        textBounds = getFont().getStringBounds(getText(), frc);

        // set size
        if(size == null) {
            int width = textBounds.getBounds().width + paddingWidth;
            int height = textBounds.getBounds().height + paddingHeight;
            size = new Dimension(width, height);
        }

        setPreferredSize(size);
        setMinimumSize(size);

        labelX = (float) (size.getWidth() - textBounds.getBounds().width) / 2;
        labelY = (float) getFont().getSize() + (float) (size.getHeight() - getFont().getSize()) / 2 - 1;

		canHoverStyle = GenericObjectStyle.getStyle().has(styleName);
    }

    public void setSize(Dimension size) {
        setPreferredSize(size);
        setMinimumSize(size);

        labelX = (float) (size.getWidth() - textBounds.getBounds().width) / 2;
        labelY = (float) getFont().getSize() + (float) (size.getHeight() - getFont().getSize()) / 2;
    }

    public void setSize(int width, int height) {
        setSize(new Dimension(width, height));
    }

    public void setBorderRadius(int radius) {
        if(radius < 0)
            radius = 0;
        borderRadius = radius;
    }

    public void setStyle(String style) {
        if(GenericObjectStyle.getStyle().has(style))
            styleName = style;
		canHoverStyle = GenericObjectStyle.getStyle().has(styleName);
    }

    public void paint(Graphics g) {
        // Don't draw the button or border
        setContentAreaFilled(false);
        setBorderPainted(false);

        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliased lines and text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // This is needed on non-Mac so text is repainted correctly
        super.paint(g);

        String currentStyle = styleName + ((hovering && canHoverStyle)? ":hover" : "");

        Color background = GenericObjectStyle.getProp(currentStyle, "background");
        Color borderColor = GenericObjectStyle.getProp(currentStyle, "borderColor");
        Color color = GenericObjectStyle.getProp(currentStyle, "color");

        // fill
        g2d.setColor(background);
        g2d.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);
        // stroke
        g2d.setColor(borderColor);
        g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, borderRadius, borderRadius);
        // text
        g2d.setColor(color);
        g2d.drawString(getText(), labelX, labelY);
    }
}