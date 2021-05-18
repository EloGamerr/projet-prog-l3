package fr.prog.tablut.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import fr.prog.tablut.view.game.GameWindow;
import fr.prog.tablut.view.home.HomeWindow;

@SuppressWarnings("serial")
public class GlobalWindow extends Window {
    private final GameWindow gameWindow;
    private final HomeWindow homeWindow;
    public Window currentWindow;

    public GlobalWindow() {
    	//this.setBackground(new Color(85, 85, 85));
    	gameWindow = new GameWindow();
    	gameWindow.setVisible(false);
        this.add(gameWindow);
        
        homeWindow = new HomeWindow(this);
        homeWindow.setVisible(true);
        this.add(homeWindow);
        
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

	public void changeWindow(String dest) {
		// TODO Auto-generated method stub
		currentWindow.setVisible(false);
		if(dest == "GameWindow") {
			gameWindow.setVisible(true);
			currentWindow = gameWindow;
		}
		
	}
}
