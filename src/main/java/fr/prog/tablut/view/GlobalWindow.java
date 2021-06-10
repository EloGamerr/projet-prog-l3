package fr.prog.tablut.view;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.json.JSONObject;

import fr.prog.tablut.model.Loader;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.components.NavPage;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
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
		Loader loader = new Loader();
		loader.loadCustomFont("Farro-Regular.ttf");
		loader.loadCustomFont("Farro-Light.ttf");
		loader.loadCustomFont("Staatliches-Regular.ttf");
		

        // create its frame and load all pages
		jFrame = new JFrame(config.projectName);

		GenericObjectStyle.setGlobalWindow(this);
		
		gamePage = new GamePage(this.config);
		homePage = new HomePage(this.config);
		currentPage = homePage;
		previousPageName = currentPage.name();
		helpPage = new HelpPage(this.config, this.previousPageName);
		newGamePage = new NewGamePage(this.config);
		loadPage = new LoadSavesPage(this.config, this);

		// default page to show - home page
		homePage.setVisible(true);
		jFrame.setContentPane(homePage);
		
        // frame parameters
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setSize(config.windowWidth, config.windowHeight);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		jFrame.setResizable(false);

		InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("images/chess/small/white_tower_small.png");
		try {
			ImageIcon img = new ImageIcon(ImageIO.read(Objects.requireNonNull(in)));
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

        currentPage = tmpPage;
        
        // update page if previous wasn't the help one
		if(previousPageName != PageName.HelpPage)
            currentPage.update();
        
        // show the new page
		currentPage.setVisible(true);
		jFrame.setContentPane(currentPage);
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
}
