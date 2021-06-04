package fr.prog.tablut.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
    protected GenericPanel absolutePanel = new GenericPanel();
    protected GenericPanel panel = new GenericPanel();
    
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

    private void init(int width, int height) {
        setVisible(false);
        setLayout(null);

        Dimension d = new Dimension(width, height - 45);
        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);

        absolutePanel = new GenericPanel(new BorderLayout(), d);
        panel = new GenericPanel(new BorderLayout(), d);

        panel.setLocation(0, 0);
        absolutePanel.setLocation(0, 0);

        super.add(panel);
        super.add(absolutePanel);
    }

    @Override
    public Component add(Component component) {
        return panel.add(component);
    }

    @Override
    public void add(Component comp, Object constraints) {
        panel.add(comp, constraints);
    }

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

    public GenericPanel getAbsPanel() {
        return absolutePanel;
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