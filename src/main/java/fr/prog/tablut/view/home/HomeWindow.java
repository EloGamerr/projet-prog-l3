package fr.prog.tablut.view.home;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.Window;

@SuppressWarnings("serial")
public class HomeWindow extends Window{
	private final Button_choice button_choice;
	private final Title title;
	
	public HomeWindow(GlobalWindow globalWindow) {
		this.setLayout(new GridBagLayout());
		
		this.title = new Title();
		this.button_choice = new Button_choice(globalWindow);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 0, 50, 0);
		
		c.gridx = 0;
		c.gridy = 1;
		
		this.add(title,c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(button_choice,c);
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
	public String name() {
		// TODO Auto-generated method stub
		return "HomeWindow";
	}
}
