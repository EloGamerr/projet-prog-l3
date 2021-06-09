package fr.prog.tablut.view.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;

import java.io.IOException;

import javax.imageio.ImageIO;

import fr.prog.tablut.view.components.generic.GenericPanel;

/**
 * Used to cheat on images and display absoluted positionated images on the window
 * thanks a panel.
 * @see GenericPanel
 * @see Image
 */
public class ImageComponent extends GenericPanel {
    protected String src;
    protected Image img;
    protected boolean loaded = false;
    protected int width = 0;
    protected int height = 0;

    /**
     * Creates an image component without any setting
     */
    public ImageComponent() {
        super();
    }

    /**
     * Creates an image component with given image source's path
     * @param src The image source's path
     */
    public ImageComponent(String src) {
        this.src = src;
    }

    /**
     * Creates an image component with given image source's path and dimension
     * @param src The image source's path
     * @param width The image's width
     * @param height The image's height
     */
    public ImageComponent(String src, int width, int height) {
        this(src, null, new Dimension(width, height));
    }

    /**
     * Creates an image component with given image source's path, top-left corner location and dimension
     * @param src The image source's path
     * @param x The image top-left corner x-Axis coord
     * @param y The image top-left corner y-Axis coord
     * @param width The image's width
     * @param height The image's height
     */
    public ImageComponent(String src, int x, int y, int width, int height) {
        this(src, new Point(x, y), new Dimension(width, height));
    }

    /**
     * Creates an image component with given image source's path and dimension
     * @param src The image source's path
     * @param d The image's dimension
     */
    public ImageComponent(String src, Dimension d) {
        this(src, null, d);
    }

    /**
     * Creates an image component with given image source's path, location and dimension
     * @param src The image source's path
     * @param p The image top-left corner's location
     * @param d The image's dimension
     */
    public ImageComponent(String src, Point p, Dimension d) {
        this.src = src;

        if(p != null) {
            setLocation(p);
        }
        
        if(d != null) {
            super.setSize(d);
            super.setPreferredSize(d);
            super.setMaximumSize(d);
            super.setMinimumSize(d);
        }
    }

    /**
     * Creates an image component with given image, location and dimension
     * @param img The pre-loaded image
     * @param p The image top-left corner's location
     * @param d The image's dimension
     */
    public ImageComponent(Image img, Point p, Dimension d) {
        if(img != null) {
            this.img = img;
            this.src = img.getSource().toString();
            loaded = true;
        }

        if(p != null) {
            setLocation(p);
        }

        if(d != null) {
            super.setSize(d);
            super.setPreferredSize(d);
            super.setMaximumSize(d);
            super.setMinimumSize(d);
        }
    }

    /**
     * Loads the image if not already loaded
     */
    public void load() {
        if(!hasLoaded()) {
            try {
                img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("images/" + src));
                loaded = true;
                revalidate();
                repaint();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns either it has loaded or not its image
     */
    public boolean hasLoaded() {
        return loaded;
    }

    /**
     * sets the image's source if the image has not loaded yet
     */
    public void setSrc(String src) {
        if(!hasLoaded()) {
            this.src = src;
        }
    }

    /**
     * Returns the image's source
     */
    public String getSrc() {
        return src;
    }

    /**
     * Returns the image
     */
    public Image getImage() {
        return img;
    }

    /**
     * Custom paint of the image's component
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliased lines and text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // This is needed on non-Mac so text is repainted correctly
        super.paintComponent(g);
        
        if(hasLoaded()) {
            g2d.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    }
}
