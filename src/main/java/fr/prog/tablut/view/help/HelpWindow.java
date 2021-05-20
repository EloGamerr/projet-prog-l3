package fr.prog.tablut.view.help;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.Window;
import fr.prog.tablut.view.generic.GenericButton;

public class HelpWindow extends Window{
	
	public HelpWindow(GlobalWindow globalWindow) {
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		
		this.add(new HelpPannel(),c);
		
		c.gridy = 1;
		c.insets = new Insets(200, 0, 0, 0);
		
		this.add(new GenericButton("Retour", new ButtonNavAdaptator(globalWindow, globalWindow.currentWindow.name()),GenericButton.DIMENTION()),c);
		
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
