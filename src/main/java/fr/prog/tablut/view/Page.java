package fr.prog.tablut.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.model.window.PageName;

/**
 * A page inside the app, in the main window
 * @see GlobalWindow
 * @see GenericPanel
 */
public class Page extends JPanel {
    protected PageName windowName = PageName.DefaultPage;
    protected GenericPanel backgroundPanel; // the background of the page
    protected GenericPanel foregroundPanel; // the foreground of the page
    protected GenericPanel panel; // the page itself, its content, where are buttons etc...
    
    /**
     * Default constructor.
     * <p>A page is hidden by default.</p>
     */
    public Page() {
        super();
        init(0, 0);
    }

    /**
     * Creates a page.
     * <p>A page is hidden by default.</p>
     */
    public Page(WindowConfig config) {
        super();
        init(config.windowWidth, config.windowHeight);
       
        // apply config's style
        if(config.hasComp("window"))
            setBackground(config.getComp("window").get("background"));
    }

    /**
     * Initializes the page (layout, visibility, dimension, childs)
     * @param width The width of the page
     * @param height The height of the page
    */
    private void init(int width, int height) {
        setVisible(false);
        setLayout(null);

        Dimension d = new Dimension(width, height - 40);
        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);

        backgroundPanel = new GenericPanel(null, d);
        foregroundPanel = new GenericPanel(null, d);
        panel = new GenericPanel(new BorderLayout(), d);

        // children take all page's size, and are located from top-left
        // (cover the entire page)
        panel.setLocation(0, 0);
        backgroundPanel.setLocation(0, 0);
        foregroundPanel.setLocation(0, 0);

        foregroundPanel.setVisible(false);

        super.add(foregroundPanel);
        super.add(panel);
        super.add(backgroundPanel);
    }

    /**
     * Returns the foreground panel of the page
     * @return The foreground panel of the page
     */
    public GenericPanel getForegroundPanel() {
        return foregroundPanel;
    }

    /**
     * Returns the background panel of the page
     * @return The background panel of the page
     */
    public GenericPanel getBackgroundPanel() {
        return backgroundPanel;
    }

    /**
     * Intercepts what it wants to add to itself, to add it to its panel
     * @param component The component to add to the page
     */
    @Override
    public Component add(Component component) {
        return panel.add(component);
    }

    /**
     * Intercepts what it wants to add to itself, to add it to its panel.
     * <p>Add the given component with given constraints</p>
     * @param comp The component to add
     * @param constraints The constraints to apply to the component
     */
    @Override
    public void add(Component component, Object constraints) {
        panel.add(component, constraints);
    }

    /**
     * Sets the layout of its panel
     * @param mgr The layout manager to set
     */
    public void setPLayout(LayoutManager mgr) {
        panel.setLayout(mgr);
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

    /**
     * Trigerred when the page is shown.
     * <p>It can be called outside this context.</p>
     */
    public void update() {

    }
}