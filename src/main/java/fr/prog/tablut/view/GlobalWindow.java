package fr.prog.tablut.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import fr.prog.tablut.controller.game.AIPlayer;
import fr.prog.tablut.controller.game.AIRandom;
import fr.prog.tablut.controller.game.HumanPlayer;
import fr.prog.tablut.model.Game;
import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.view.game.GameWindow;
import fr.prog.tablut.view.generic.GenericButton;
import fr.prog.tablut.view.help.HelpWindow;
import fr.prog.tablut.view.home.HomeWindow;
import fr.prog.tablut.view.load.LoadWindow;
import fr.prog.tablut.view.newGame.NewGameWindow;

public class GlobalWindow extends Window {
    private GameWindow gameWindow;
    private HomeWindow homeWindow;
    private LoadWindow loadWindow;
   

	private HelpWindow helpWindow;
    private NewGameWindow newGameWindow;
    public Window currentWindow;
	private JFrame jFrame;

    public GlobalWindow(JFrame jFrame) {
        this.jFrame = jFrame;
        
        GenericButton.setGlobalWindow(this);
    	//this.setBackground(new Color(85, 85, 85));
    	gameWindow = new GameWindow();
    	gameWindow.setVisible(false);
        
        homeWindow = new HomeWindow(this);
        jFrame.setContentPane(homeWindow);
        
        currentWindow = homeWindow;
        loadWindow = new LoadWindow(this);
        loadWindow.setVisible(false);
        
        helpWindow = new HelpWindow(this);
        helpWindow.setVisible(false);
        
        newGameWindow = new NewGameWindow(this);
        newGameWindow.setVisible(false);
        
        
        currentWindow = homeWindow;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D) graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }

	public void changeWindow(WindowName dest) {
		// TODO Auto-generated method stub
		currentWindow.setVisible(false);
		switch (dest) {
		case GameWindow: {
			if(currentWindow == loadWindow) {
				currentWindow = gameWindow;
				gameWindow.getGame().load(loadWindow.getPanel().index_selected);
			}
			else {
				currentWindow = gameWindow;
				gameWindow.getGame().start(new AIRandom(), new HumanPlayer());
			}
				
			break;
		}
		case LoadWindow: {
			currentWindow = loadWindow;
			break;
		}
		case HomeWindow: {
			currentWindow = homeWindow;
			break;
		}
		
		case HelpWindow: {
			currentWindow = helpWindow;
			break;
		}
		case NewGameWindow: {
			currentWindow = newGameWindow;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + dest);
		}
		
		currentWindow.setVisible(true);
		jFrame.setContentPane(currentWindow);
	}
	
	 public GameWindow getGameWindow() {
			return gameWindow;
		}

		public HomeWindow getHomeWindow() {
			return homeWindow;
		}

		public LoadWindow getLoadWindow() {
			return loadWindow;
		}

		public HelpWindow getHelpWindow() {
			return helpWindow;
		}

		public NewGameWindow getNewGameWindow() {
			return newGameWindow;
		}

		public Window getCurrentWindow() {
			return currentWindow;
		}

		public JFrame getjFrame() {
			return jFrame;
		}

		public void setGameWindow(GameWindow gameWindow) {
			this.gameWindow = gameWindow;
		}

		public void setHomeWindow(HomeWindow homeWindow) {
			this.homeWindow = homeWindow;
		}

		public void setLoadWindow(LoadWindow loadWindow) {
			this.loadWindow = loadWindow;
		}

		public void setHelpWindow(HelpWindow helpWindow) {
			this.helpWindow = helpWindow;
		}

		public void setNewGameWindow(NewGameWindow newGameWindow) {
			this.newGameWindow = newGameWindow;
		}

		public void setCurrentWindow(Window currentWindow) {
			this.currentWindow = currentWindow;
		}

		public void setjFrame(JFrame jFrame) {
			this.jFrame = jFrame;
		}
}
