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

public class ImageComponent extends GenericPanel {
    protected String src;
    protected Image img;
    protected boolean loaded = false;
    protected int width = 0;
    protected int height = 0;


    public ImageComponent() {
        super();
    }

    public ImageComponent(String src) {
        this.src = src;
    }

    public ImageComponent(String src, int width, int height) {
        this(src, null, new Dimension(width, height));
    }

    public ImageComponent(String src, int x, int y, int width, int height) {
        this(src, new Point(x, y), new Dimension(width, height));
    }

    public ImageComponent(String src, Dimension d) {
        this(src, null, d);
    }

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

    /* public void setSize(int width, int height) {
        Dimension d = new Dimension(width, height);

        super.setSize(d);
        super.setPreferredSize(d);
        super.setMaximumSize(d);
        super.setMinimumSize(d);
    } */

    /* @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        super.setPreferredSize(d);
        super.setMaximumSize(d);
        super.setMinimumSize(d);
    } */

    public boolean hasLoaded() {
        return loaded;
    }

    public void setSrc(String src) {
        if(!hasLoaded()) {
            this.src = src;
        }
    }

    public String getSrc() {
        return src;
    }

    public Image getImage() {
        return img;
    }

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
