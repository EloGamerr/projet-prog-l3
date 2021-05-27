package fr.prog.tablut.view.game;

import fr.prog.tablut.controller.game.*;
import fr.prog.tablut.model.Game;
import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.view.Window;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GameWindow extends Window{
    private final GridWindow gridWindow;
    private final NorthWindow northWindow;
    private final EastWindow eastWindow;
    private final WestWindow westWindow;
    private final SouthWindow southWindow;
    private final Game game;
    
    public GameWindow() {
        this.setLayout(new BorderLayout());
        
        game = new Game();
        gridWindow = new GridWindow(game);
        GameController gameController = new GameController(game, this);

        northWindow = new NorthWindow();
        this.add(northWindow, BorderLayout.NORTH);
        eastWindow = new EastWindow(game);
        this.add(eastWindow, BorderLayout.EAST);
        westWindow = new WestWindow();
        this.add(westWindow, BorderLayout.WEST);
        southWindow = new SouthWindow(game, this);
        this.add(southWindow, BorderLayout.SOUTH);

        GameMouseAdaptator gameMouseAdaptator = new GameMouseAdaptator(gameController, gridWindow);
        gridWindow.addMouseListener(gameMouseAdaptator);
        gridWindow.addMouseMotionListener(gameMouseAdaptator);

        this.add(gridWindow, BorderLayout.CENTER);
        

        Timer time = new Timer(50, new GameTimeAdaptator(gameController));
        time.start();
    }
    
    public GameWindow(Game game) {
        this.setLayout(new BorderLayout());
        
        this.game = game;
        gridWindow = new GridWindow(game);
        GameController gameController = new GameController(game, this);

        northWindow = new NorthWindow();
        this.add(northWindow, BorderLayout.NORTH);
        eastWindow = new EastWindow(game);
        this.add(eastWindow, BorderLayout.EAST);
        westWindow = new WestWindow();
        this.add(westWindow, BorderLayout.WEST);
        southWindow = new SouthWindow(game, this);
        this.add(southWindow, BorderLayout.SOUTH);

        GameMouseAdaptator gameMouseAdaptator = new GameMouseAdaptator(gameController, gridWindow);
        gridWindow.addMouseListener(gameMouseAdaptator);
        gridWindow.addMouseMotionListener(gameMouseAdaptator);

        this.add(gridWindow, BorderLayout.CENTER);
        

        Timer time = new Timer(50, new GameTimeAdaptator(gameController));
        time.start();
    }



	@Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D) graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }
    
    public GridWindow getGridWindow() {
        return gridWindow;
    }

    public NorthWindow getNorthWindow() {
        return northWindow;
    }

    public EastWindow getEastWindow() {
        return eastWindow;
    }

    public WestWindow getWestWindow() {
        return westWindow;
    }

    public SouthWindow getSouthWindow() {
        return southWindow;
    }

	public WindowName name() {
		return WindowName.GameWindow;
	}
	
	public Game getGame() {
		return game;
	}
}
