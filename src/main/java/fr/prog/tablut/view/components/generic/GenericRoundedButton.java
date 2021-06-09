package fr.prog.tablut.view.components.generic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import fr.prog.tablut.model.window.PageName;


// Source : Adapted from http://www.bryanesmith.com/docs/rounded-jbuttons

/**
 * A component that extends a GenericButton (Jbutton), a button with rounded corners
 * @see GenericButton
 */
public class GenericRoundedButton extends GenericButton {
    protected TextAlignment alignment = TextAlignment.CENTER;
    protected int width = 0;
    protected int height = 0;
    protected final int paddingWidth = 60;
    protected final int paddingHeight = 20;
    protected int borderRadius = 12;

    protected Rectangle2D textBounds;
    protected float labelX = 0;
    protected float labelY = 0;
    protected Image image = null;
    protected int imageWidth = 0;
    protected int imageHeight = 0;
    protected int imageX = 0;
    protected int imageY = 0;

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
     * @see PageName
     * @param text The button's text
     * @param href The button's href location (WidnowName)
     */
    public GenericRoundedButton(String text, PageName href) {
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

        width = size.width;
        height = size.height;

        labelY = (float) getFont().getSize() + (float) (height - getFont().getSize()) / 2 - 1;
        
        alignText(alignment);
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
     * @see GenericObjectStyle
     * @param style The style to apply
     */
    public void setStyle(String style) {
        String oldStyle = getStyle();
        
        super.setStyle(style);

        if(!oldStyle.equals(style)) {
            repaint();
        }
    }

    /**
     * Sets the text's alignment of the button
     * @see TextAlignment
     * @param alignment The text's alignment of the button
     */
    public void alignText(TextAlignment alignment) {
        this.alignment = alignment;
        int padding = 10;

        switch(alignment) {
            case CENTER:
                labelX = (float) (width - textBounds.getBounds().width) / 2;
                break;

            case LEFT:
                labelX = padding;
                break;

            case RIGHT:
                labelX = width - textBounds.getBounds().width - padding;
                break;
        }
    }

    /**
     * Sets an image on the button at specified position and dimension
     * @param imageSrc The image's source path
     * @param x The image's top-left corner x-Axis coord
     * @param y The image's top-left corner y-Axis coord
     * @param width The image's width
     * @param height The image's height
     * @return Either it successfully loaded the image or not
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean setImage(String imageSrc, int x, int y, int width, int height) {
        if(imageSrc != null) {
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("images/" + imageSrc);

			try {
				image = ImageIO.read(Objects.requireNonNull(in));

                imageX = x;
                imageY = y;
                imageWidth = width;
                imageHeight = height;

                return true;
			}
			catch(IOException e) {
				e.printStackTrace();
                return false;
			}
		}

        return false;
    }

    /**
     * Updates the text of the button and re-calculates its position
     * @param text The text to put in the button
     */
    public void updateText(String text) {
        super.setText(text);
        textBounds = getFont().getStringBounds(text, new FontRenderContext(null, false, false));
        alignText(alignment);
    }

    /**
     * Repains the button with its set style
     */
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

        if(image != null) {
            g2d.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
        }
    }
}