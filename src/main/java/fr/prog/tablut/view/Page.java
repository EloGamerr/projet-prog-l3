package fr.prog.tablut.view;

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
    public Page(GlobalWindow globalWindow) {
        super();

        setVisible(false);
		setConfig(globalWindow.getConfig());
       
        // apply config's style
        setBackground(config.components.get("window").background);
    }
}