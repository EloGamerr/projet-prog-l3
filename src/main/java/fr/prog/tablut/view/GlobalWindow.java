package fr.prog.tablut.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.pages.help.HelpPage;
import fr.prog.tablut.view.pages.home.HomePage;
import fr.prog.tablut.view.pages.load.LoadPage;
import fr.prog.tablut.view.pages.newGame.NewGamePage;

public class GlobalWindow extends Window {
    private final GamePage gamePage;
    private final HomePage homePage;
    private final LoadPage loadPage;
    private final HelpPage helpPage;
    private final NewGamePage newGamePage;
    public Page currentPage;
	private JFrame jFrame;

	/**
	 * Creates the main window with given surface
	 * @param frame The surface
	 * @throws ParseException
	 */
	public GlobalWindow() throws ParseException {
		this(null);
	}

	/**
	 * Creates the main window with given surface and configuration
	 * @param frame The surface
	 * @param configfilePath The configuration file path
	 * @throws ParseException
	 */
    public GlobalWindow(String configfilePath) throws ParseException {
		super();

		if(configfilePath != null) {
			setConfig(configfilePath);
		}
		
		jFrame = new JFrame(config.projectName);
	
		GenericButton.setGlobalWindow(this);
		
		gamePage = new GamePage();
		homePage = new HomePage(this);
		currentPage = homePage;
		loadPage = new LoadPage(this);
		helpPage = new HelpPage(this);
		newGamePage = new NewGamePage(this);
		
		homePage.setVisible(true);
		jFrame.setContentPane(homePage);
		
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setSize(config.width, config.height);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
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
		
		switch(dest) {
			case GameWindow:
				currentPage = gamePage;
				break;
			case LoadWindow:
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
}
