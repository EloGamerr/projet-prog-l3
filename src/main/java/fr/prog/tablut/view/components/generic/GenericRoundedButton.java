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

/**
 * A component that extends a GenericButton (Jbutton), a button with rounded corners
 * @see GenericButton
 * @see JButton
 */
public class GenericRoundedButton extends GenericButton {
    protected int paddingWidth = 60;
    protected int paddingHeight = 20;
    protected int borderRadius = 12;

    protected Rectangle2D textBounds;
    protected float labelX = 0;
    protected float labelY = 0;

    /**
     * Defaults constructor.
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     */
    public GenericRoundedButton() {
        super();
        init();
    }

    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     * @param size The button's dimension
     */
    public GenericRoundedButton(Dimension size) {
        super();
        init(size);
    }
    
    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     * @param width The button's width
     * @param height The button's height
     */
    public GenericRoundedButton(int width, int height) {
        super();
        init(new Dimension(width, height));
    }

    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     * @param text The button's text
     */
    public GenericRoundedButton(String text) {
        super(text);
        init();
    }

    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     * @param text The button's text
     * @param size The button's dimension
     */
    public GenericRoundedButton(String text, Dimension size) {
        super(text);
        init(size);
    }

    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     * @param text The button's text
     * @param width The button's width
     * @param height The button's height
     */
    public GenericRoundedButton(String text, int width, int height) {
        super(text);
        init(new Dimension(width, height));
    }

    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     * @see WindowName
     * @param text The button's text
     * @param href The button's href location (WidnowName)
     */
    public GenericRoundedButton(String text, WindowName href) {
        super(text);
        init(null);
        setHref(href);
    }

    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     */
    private void init() {
        init(null);
    }

    /**
     * <p>Initializes all stuff around the draw : the position of the text, the rectangle etc...</p>
     * <p>Sets the font, the size, the colors</p>
     * @param size The button's dimension
     */
    private void init(Dimension size) {
        // style
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

        setSize(size);

        // when mouse hovering it, could we apply the :hover style cause it exists or not
        // if it does not exist, then keep the normal style when hovering
		canHoverStyle = GenericObjectStyle.getStyle().has(getStyle());
    }

    /**
     * Sets the button's size.
     * <p>Re-calculates the text's position in the button</p>
     * @param size The button's dimension
     */
    public void setSize(Dimension size) {
        setPreferredSize(size);
        setMinimumSize(size);

        labelX = (float) (size.getWidth() - textBounds.getBounds().width) / 2;
        labelY = (float) getFont().getSize() + (float) (size.getHeight() - getFont().getSize()) / 2;
    }

    /**
     * Sets the button's size.
     * <p>Re-calculates the text's position in the button</p>
     * @param width The button's width
     * @param height The button's height
     */
    public void setSize(int width, int height) {
        setSize(new Dimension(width, height));
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
     * @see Style
     * @see GenericObjectStyle
     * @param style The style to apply
     */
    public void setStyle(String style) {
        String oldStyle = getStyle();
        
        super.setStyle(style);

        if(oldStyle != style) {
            repaint();
        }
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

        String currentStyle = getStyle() + ((!getStyle().contains(":disabled") && hovering && canHoverStyle)? ":hover" : "");

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