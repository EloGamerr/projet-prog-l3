package fr.prog.tablut.view.pages.home;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.GlobalWindow;

public class HomePage extends Page {
	private final ButtonChoice button_choice;
	private final Title title;
	
	public HomePage(GlobalWindow globalWindow) {
		super(globalWindow);

		setLayout(new GridBagLayout());
		
		title = new Title();
		button_choice = new ButtonChoice(globalWindow);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 0, 50, 0);
		
		c.gridx = 0;
		c.gridy = 1;
		add(title, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(button_choice, c);
	}
	
	@Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D) graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }

	@Override
	public WindowName name() {
		return WindowName.HomeWindow;
	}
}
