package fr.prog.tablut.view.pages.home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.Title;

public class HomePage extends Page {
	private final ButtonChoice button_choice;
	private final Title title;
	
	public HomePage(WindowConfig config) {
		super(config);

		windowName = WindowName.HomeWindow;

		setLayout(new GridBagLayout());
		
		title = new Title("TABLUT");
		button_choice = new ButtonChoice();
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 0, 50, 0);
		
		c.gridx = 0;
		c.gridy = 1;
		add(title, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(button_choice, c);
	}
}
