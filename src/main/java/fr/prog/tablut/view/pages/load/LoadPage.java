package fr.prog.tablut.view.pages.load;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericLabel;

public class LoadPage extends Page {
	private final GenericLabel title;
	private final GenericLabel subtitle;
	
	public LoadPage(GlobalWindow globalWindow) {
		super(globalWindow);

		setLayout(new GridBagLayout());
		
		title = new GenericLabel("Charger une partie", 80);
		subtitle = new GenericLabel("Choisissez une sauvegarde à restaurer", 20);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 0, 30, 0);
		
		c.gridx = 0;
		c.gridy = 1;
		add(title,c);

		c.gridy = 2;
		add(subtitle,c);
		
		c.gridy = 3;
		add(new ButtonTogglePannel(),c);
		
		c.gridy = 4;
		add(new NavButton(globalWindow),c);
	}
	
	@Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D)graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }

	@Override
	public WindowName name() {
		return WindowName.LoadWindow;
	}
}
