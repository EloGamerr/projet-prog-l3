package fr.prog.tablut.view.load;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.Window;
import fr.prog.tablut.view.generic.GenericLabel;

@SuppressWarnings("serial")
public class LoadWindow extends Window{
	private final GenericLabel title;
	private final GenericLabel subtitle;
	
	
	public LoadWindow(GlobalWindow globalWindow) {
		// TODO Auto-generated constructor stub
		
		this.setLayout(new GridBagLayout());
		
		this.title = new GenericLabel("Charger une partie", 80);
		this.subtitle = new GenericLabel("Choisissez une sauvegarde à restaurer", 20);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 0, 30, 0);
		
		c.gridx = 0;
		c.gridy = 1;
		
		this.add(title,c);
		
		c.gridx = 0;
		c.gridy = 2;
		
		this.add(subtitle,c);
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
		return "LoadWindow";
	}
}
