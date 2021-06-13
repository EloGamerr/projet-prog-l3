package fr.prog.tablut.view.window;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import java.io.IOException;

import java.text.ParseException;

import javax.swing.WindowConstants;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import org.json.JSONObject;

import fr.prog.tablut.model.Loader;
import fr.prog.tablut.view.components.NavPage;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.pages.Page;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.pages.help.HelpPage;
import fr.prog.tablut.view.pages.home.HomePage;
import fr.prog.tablut.view.pages.load.LoadSavesPage;
import fr.prog.tablut.view.pages.newGame.NewGamePage;

/**
 * The main window of the application.
 * <p>It stores all pages and manage these ones, to show the current on its frame.</p>
 * @see Page
 * @see JFrame
 */
public class GlobalWindow {
	protected final WindowConfig config;
    private final Titlebar titlebar;
    private final GamePage gamePage;
    private final HomePage homePage;
    private final LoadSavesPage loadPage;
    private final HelpPage helpPage;
    private final NewGamePage newGamePage;
	private final JFrame jFrame;
    private Page currentPage;
	private PageName previousPageName;

	/**
	 * Creates the main window with given surface
	 */
	public GlobalWindow() throws ParseException {
		this(null);
	}

	/**
	 * Creates the main window with given surface and configuration.
     * <p>Loads everything about the window : resources (font, ...), pages, configuration etc...</p>
     * @see Page
     * @see JFrame
	 * @param configfilePath The configuration file path
	 */
    public GlobalWindow(String configfilePath) throws ParseException {
		super();

        // create a default configuration
		config = new WindowConfig();

        // load the given custom configuration
		if(configfilePath != null) {
			setConfig(configfilePath);
		}

        // dispatch the style onto all components
		GenericObjectStyle.setStyle(config.getStyle());
		NavPage.setDimension(new Dimension(config.windowWidth, config.windowHeight - 25));

        // load fonts
		Loader.loadCustomFont("Farro-Regular.ttf");
		Loader.loadCustomFont("Farro-Light.ttf");
		Loader.loadCustomFont("Staatliches-Regular.ttf");


		GenericObjectStyle.setGlobalWindow(this);
        Page.setParent(this);

		gamePage = new GamePage(this.config);
		homePage = new HomePage(this.config);
		currentPage = homePage;
		previousPageName = currentPage.name();
		helpPage = new HelpPage(this.config, this.previousPageName);
		newGamePage = new NewGamePage(this.config);
		loadPage = new LoadSavesPage(this.config, this);


        // create its frame and load all pages
		jFrame = new JFrame(config.projectName);
        jFrame.setUndecorated(true);
        jFrame.getRootPane().setBorder(new LineBorder(new Color(68, 68, 68)));

        titlebar = new Titlebar(this);

        jFrame.getContentPane().add(titlebar, BorderLayout.PAGE_START);

		// default page to show - home page
		homePage.setVisible(true);
		jFrame.getContentPane().add(homePage);

        // frame parameters
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setSize(config.windowWidth, config.windowHeight + titlebar.getHeight());
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		jFrame.setResizable(false);

		try {
			ImageIcon img = new ImageIcon(Loader.getImage("chess/small/white_tower_small.png"));
			jFrame.setIconImage(img.getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * Returns the configuration object of the window
     * @see WindowConfig
	 * @return The window's configuration
	 */
	public WindowConfig getConfig() {
		return config;
	}

	/**
	 * Sets the window's configuration from the file at given path
     * @see WindowConfig
	 * @param configPath file's path
	 */
	protected void setConfig(String configPath) throws ParseException {
		config.setConfig(configPath);
	}

	/**
	 * Sets the window's configuration from given JSON
     * @see JSONObject
     * @see WindowConfig
	 * @param configObject The json object
	 */
	protected void setConfig(JSONObject configObject) {
		config.setConfig(configObject);
	}

	/**
	 * Copies the window's configuration from another window's config
     * @see WindowConfig
	 * @param config The configuration to copy
	 */
	protected void setConfig(WindowConfig config) {
		this.config.setConfig(config);
	}

	/**
	 * Changes the visibility of the windows, depending of the window to display
     * @see PageName
     * @see Page
	 * @param dest The window's name
	 */
	public void changeWindow(PageName dest) {
        // we use a tmpPage so we don't hide the previous page
        // if the new one does not exists
        Page tmpPage;

        // not dynamic - security
		switch(dest) {
			case GamePage: tmpPage = gamePage; break;
			case LoadPage: tmpPage = loadPage; break;
			case HomePage: tmpPage = homePage; break;
			case HelpPage: tmpPage = helpPage; break;
			case NewGamePage: tmpPage = newGamePage; break;
			default: throw new IllegalArgumentException("Unexpected value: " + dest);
		}

        // hide future previous page
		currentPage.setVisible(false);
        // store the name of the future previous page in the case
        // we want to go back to
		previousPageName = currentPage.name();
		helpPage.setBackPage(previousPageName);

        jFrame.getContentPane().remove(currentPage);

        currentPage = tmpPage;

        // update page if previous wasn't the help one
		if(previousPageName != PageName.HelpPage)
            currentPage.update();

        // show the new page
		currentPage.setVisible(true);
        jFrame.getContentPane().add(currentPage);
	}

    /**
     * Returns the game page
     * @see GamePage
     * @return The game page
     */
	public GamePage getGamePage() {
        return gamePage;
    }

    /**
     * Returns the home page
     * @see HomePage
     * @return The home page
     */
    public HomePage getHomePage() {
        return homePage;
    }

    /**
     * Returns the load games page
     * @see LoadSavesPage
     * @return The load games page
     */
    public LoadSavesPage getLoadPage() {
        return loadPage;
    }

    /**
     * Returns the help page
     * @see HelpPage
     * @return The help page
     */
    public HelpPage getHelpPage() {
        return helpPage;
    }

    /**
     * Returns the new game page
     * @see NewGamePage
     * @return The new game page
     */
    public NewGamePage getNewGamePage() {
        return newGamePage;
    }

    /**
     * Returns the current displayed page
     * @see Page
     * @return The current displayed page
     */
    public Page getcurrentPage() {
        return currentPage;
    }

    /**
     * Returns the JFrame object of the window
     * @see JFrame
     * @return The JFrame object
     */
    public JFrame getjFrame() {
        return jFrame;
    }

    /**
     * Returns the window's titlebar component
     * @return THe window's titlebar component
     */
    public Titlebar getTitlebar() {
        return titlebar;
    }

    /**
     * Returns the window's size, not including the titlebar's size
     * @return The inner window's size
     */
    public Dimension getSize() {
        return new Dimension(jFrame.getWidth(), jFrame.getHeight() - titlebar.getHeight());
    }

    /**
     * Sets the window's state (iconified or not)
     * @param iconified The window's state
     */
    public void setState(int iconified) {
        jFrame.setState(iconified);
    }

    /**
     * Closes the window
     */
    public void close() {
        System.exit(0);
    }

    /**
     * Toggles the state of the window between minimized and maximized
     * <p>Dispatches an event for all its pages</p>
     */
    public void maximize() {
        jFrame.setExtendedState((jFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH)? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
        jFrame.revalidate();
        jFrame.repaint();
        dispatchEvent("resize");
    }

    /**
     * Dispatch the given event in all its pages
     * @param eventName The event name to dispatch
     */
    public void dispatchEvent(String eventName) {
        homePage.listenEvent(eventName);
        helpPage.listenEvent(eventName);
        newGamePage.listenEvent(eventName);
        loadPage.listenEvent(eventName);
        gamePage.listenEvent(eventName);
    }

    /**
     * Sets the window's location at given point
     * @param p The new window's location
     */
	public void setLocation(Point p) {
        jFrame.setLocation(p);
	}

    /**
     * Sets the window's location at the given point
     * @param x The top-left corner X-Axis coord of the window
     * @param y The top-left corner Y-Axis coord of the window
     */
    public void setLocation(int x, int y) {
        jFrame.setLocation(x, y);
    }
}