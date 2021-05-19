package fr.prog.tablut.view.game;

import fr.prog.tablut.controller.AdaptateurSouris;
import fr.prog.tablut.controller.Controller;
import fr.prog.tablut.model.Model;
import fr.prog.tablut.model.WindowName;
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

    public GameWindow() {
    	
        this.setLayout(new BorderLayout());

        northWindow = new NorthWindow();
        this.add(northWindow, BorderLayout.NORTH);
        eastWindow = new EastWindow();
        this.add(eastWindow, BorderLayout.EAST);
        westWindow = new WestWindow();
        this.add(westWindow, BorderLayout.WEST);
        southWindow = new SouthWindow();
        this.add(southWindow, BorderLayout.SOUTH);

        Model model = new Model();
        gridWindow = new GridWindow(model);
        Controller controller = new Controller(model, gridWindow);

        gridWindow.addMouseListener(new AdaptateurSouris(controller, gridWindow));

        this.add(gridWindow, BorderLayout.CENTER);
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
		// TODO Auto-generated method stub
		return WindowName.GameWindow;
	}
}
