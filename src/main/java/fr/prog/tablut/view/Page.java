package fr.prog.tablut.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;

/**
 * A page inside the app, in the main window
 * @see GlobalWindow
 * @see JPanel
 */
public class Page extends JPanel {
    protected PageName windowName = PageName.DefaultPage;
    
    /**
     * Default constructor.
     * <p>A page is hidden by default.</p>
     */
    public Page() {
        super();
        setVisible(false);
        setLayout(new BorderLayout());
    }

    /**
     * Creates a page.
     * <p>A page is hidden by default.</p>
     */
    public Page(WindowConfig config) {
        super();
        setVisible(false);
        setLayout(new BorderLayout());
       
        // apply config's style
        if(config.hasComp("window"))
            setBackground(config.getComp("window").get("background"));
    }

    /**
	 * Returns the name of the window (sub-window, aka tab)
	 * @return The name of the window
	 */
	public PageName name() {
		return windowName;
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