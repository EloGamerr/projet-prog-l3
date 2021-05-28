package fr.prog.tablut.view.pages.help;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericButton;

public class HelpPage extends Page {
	public HelpPage(GlobalWindow globalWindow) {
		super(globalWindow);

		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		
		add(new HelpPannel(), c);
		
		c.gridy = 1;
		c.insets = new Insets(200, 0, 0, 0);
		
		add(new GenericButton("Retour", new ButtonNavAdaptator(globalWindow, globalWindow.currentPage.name()), GenericButton.DIMENSION()), c);
	}
	
	protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D) graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }

	@Override
	public WindowName name() {
		return WindowName.HelpWindow;
	}
}
