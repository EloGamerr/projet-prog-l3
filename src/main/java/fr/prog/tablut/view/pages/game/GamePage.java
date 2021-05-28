package fr.prog.tablut.view.pages.game;

import java.awt.*;

import javax.swing.*;

import fr.prog.tablut.controller.game.*;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.pages.game.grid.GridWindow;
import fr.prog.tablut.view.pages.game.regions.EastWindow;
import fr.prog.tablut.view.pages.game.regions.NorthWindow;
import fr.prog.tablut.view.pages.game.regions.SouthWindow;
import fr.prog.tablut.view.pages.game.regions.WestWindow;

@SuppressWarnings("serial")
public class GamePage extends Page {
    private final GridWindow gridWindow;
    private final NorthWindow northWindow;
    private final EastWindow eastWindow;
    private final WestWindow westWindow;
    private final SouthWindow southWindow;
    private final Game game;
    
    public GamePage() {
        super();

        setLayout(new BorderLayout());
        
        AIRandom attacker = new AIRandom();
        HumanPlayer defender = new HumanPlayer();
        game = new Game(attacker, defender);
        gridWindow = new GridWindow(game);
        GameController gameController = new GameController(game, this);

        northWindow = new NorthWindow();
        add(northWindow, BorderLayout.NORTH);
        eastWindow = new EastWindow(game);
        add(eastWindow, BorderLayout.EAST);
        westWindow = new WestWindow();
        add(westWindow, BorderLayout.WEST);
        southWindow = new SouthWindow(game, this);
        add(southWindow, BorderLayout.SOUTH);

        GameMouseAdaptator gameMouseAdaptator = new GameMouseAdaptator(gameController, gridWindow);
        gridWindow.addMouseListener(gameMouseAdaptator);
        gridWindow.addMouseMotionListener(gameMouseAdaptator);

        add(gridWindow, BorderLayout.CENTER);

        Timer time = new Timer(50, new GameTimeAdaptator(gameController));
        time.start();
    }
    
    public GamePage(Game g) {
        setLayout(new BorderLayout());
        
        game = g;
        gridWindow = new GridWindow(g);
        GameController gameController = new GameController(g, this);

        northWindow = new NorthWindow();
        add(northWindow, BorderLayout.NORTH);
        eastWindow = new EastWindow(g);
        add(eastWindow, BorderLayout.EAST);
        westWindow = new WestWindow();
        add(westWindow, BorderLayout.WEST);
        southWindow = new SouthWindow(g, this);
        add(southWindow, BorderLayout.SOUTH);

        GameMouseAdaptator gameMouseAdaptator = new GameMouseAdaptator(gameController, gridWindow);
        gridWindow.addMouseListener(gameMouseAdaptator);
        gridWindow.addMouseMotionListener(gameMouseAdaptator);

        add(gridWindow, BorderLayout.CENTER);
        

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
}
