package fr.prog.tablut.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.json.JSONObject;

import fr.prog.tablut.model.Loader;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.components.NavPage;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.pages.help.HelpPage;
import fr.prog.tablut.view.pages.home.HomePage;
import fr.prog.tablut.view.pages.load.LoadSavesPage;
import fr.prog.tablut.view.pages.newGame.NewGamePage;

/**
 * The main window of the application
 * @see Window
 */
public class GlobalWindow extends Window {
	protected WindowConfig config;
    private GamePage gamePage;
    private HomePage homePage;
    private LoadSavesPage loadPage;
    private HelpPage helpPage;
    private NewGamePage newGamePage;
	private JFrame jFrame;
    private Page currentPage;
	private WindowName previousPageName;

	/**
	 * Creates the main window with given surface
	 * @throws ParseException
	 */
	public GlobalWindow() throws ParseException {
		this(null);
	}

	/**
	 * Creates the main window with given surface and configuration
	 * @param configfilePath The configuration file path
	 * @throws ParseException
	 */
    public GlobalWindow(String configfilePath) throws ParseException {
		super();

		config = new WindowConfig();

		if(configfilePath != null) {
			setConfig(configfilePath);
		}
		
		setSize(config.windowWidth, config.windowHeight);
		GenericObjectStyle.setStyle(config.getStyle());
		NavPage.setDimension(new Dimension(config.windowWidth, config.windowHeight));


		Loader loader = new Loader();
		loader.loadCustomFont("Farro-Regular.ttf");
		
		jFrame = new JFrame(config.projectName);
	
		GenericObjectStyle.setGlobalWindow(this);
		
		gamePage = new GamePage(this.config);
		homePage = new HomePage(this.config);
		currentPage = homePage;
		previousPageName = currentPage.name();
		helpPage = new HelpPage(this.config, this.previousPageName);
		newGamePage = new NewGamePage(this.config);
		loadPage = new LoadSavesPage(this.config);
		
		homePage.setVisible(true);
		jFrame.setContentPane(homePage);
		
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setSize(config.windowWidth, config.windowHeight);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		jFrame.setResizable(false);
    }

	/**
	 * Returns the configuration object of the window
	 * @return The window's configuration
	 */
	public WindowConfig getConfig() {
		return config;
	}

	/**
	 * Sets the window's configuration from the file at given path
	 * @param configPath file's path
	 * @throws ParseException
	 */
	protected void setConfig(String configPath) throws ParseException {
		config.setConfig(configPath);
	}

	/**
	 * Sets the window's configuration from given JSON
	 * @param configObject The json object
	 */
	protected void setConfig(JSONObject configObject) {
		config.setConfig(configObject);
	}

	/**
	 * Copies the window's configuration from another window's config
	 * @param config The configuration to copy
	 */
	protected void setConfig(WindowConfig config) {
		this.config.setConfig(config);
	}


    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D) graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }

	/**
	 * Changes the visibility of the windows, depending of the window to display
	 * @param dest The window's name
	 */
	public void changeWindow(WindowName dest) {
		currentPage.setVisible(false);
		previousPageName = currentPage.name();
		helpPage.setBackPage(previousPageName);

		switch(dest) {
			case GameWindow:
				currentPage = gamePage;
				break;

			case LoadWindow:
				setLoadPage(new LoadSavesPage(config));
				currentPage = loadPage;
				break;

			case HomeWindow:
				currentPage = homePage;
				break;

			case HelpWindow:
				currentPage = helpPage;
				break;

			case NewGameWindow:
				currentPage = newGamePage;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + dest);
		}
		
		currentPage.setVisible(true);
		jFrame.setContentPane(currentPage);
	}
	
	 public GamePage getGamePage() {
			return gamePage;
		}

		public HomePage getHomePage() {
			return homePage;
		}

		public LoadSavesPage getLoadPage() {
			return loadPage;
		}

		public HelpPage getHelpPage() {
			return helpPage;
		}

		public NewGamePage getNewGamePage() {
			return newGamePage;
		}

		public Window getcurrentPage() {
			return currentPage;
		}

		public JFrame getjFrame() {
			return jFrame;
		}

		public void setGameWindow(GamePage gamePage) {
			this.gamePage = gamePage;
		}

		public void setHomePage(HomePage homePage) {
			this.homePage = homePage;
		}

		public void setLoadPage(LoadSavesPage loadPage) {
			this.loadPage = loadPage;
		}

		public void setHelpPage(HelpPage helpPage) {
			this.helpPage = helpPage;
		}

		public void setNewGameWindow(NewGamePage newGamePage) {
			this.newGamePage = newGamePage;
		}

		public void setcurrentPage(Page currentPage) {
			this.currentPage = currentPage;
		}

		public void setjFrame(JFrame jFrame) {
			this.jFrame = jFrame;
		}
}
