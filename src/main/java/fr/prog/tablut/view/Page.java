package fr.prog.tablut.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import fr.prog.tablut.model.window.WindowConfig;

/**
 * A page inside the app, in the main window
 * @see GlobalWindow
 * @see Window
 * @see JPanel
 */
public class Page extends Window {
    /**
     * Default constructor.
     * <p>A page is hidden by default.</p>
     */
    public Page() {
        super();
        setVisible(false);
    }

    /**
     * Creates a page.
     * <p>A page is hidden by default.</p>
     * @param globalWindow The parent window in which the page will be shown. inherits from its configuration.
     * @param name 
     */
    public Page(WindowConfig config) {
        super();
        setVisible(false);
        setLayout(new BorderLayout());
       
        // apply config's style
        if(config.hasComp("window"))
            setBackground(config.getComp("window").get("background"));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D)graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }

    public void update() {

    }
}