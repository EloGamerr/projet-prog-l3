package fr.prog.tablut.view.pages.home;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericButton;

public class ButtonChoice extends JPanel {
	private final GenericButton button_new_game;
	private final GenericButton button_load_game;
	private final GenericButton button_shortcut;
	private final GenericButton button_quit;
	
	public ButtonChoice(GlobalWindow globalWindow) {
		setOpaque(false);
		setLayout(new GridBagLayout());

		button_new_game = new GenericButton("Nouvelle partie", GenericButton.GO(WindowName.NewGameWindow), new Dimension(200, 30));
		button_load_game = new GenericButton("Charger partie", GenericButton.GO(WindowName.LoadWindow), new Dimension(200, 30));
		button_shortcut = new GenericButton("Raccourcis", GenericButton.GO(WindowName.HelpWindow), new Dimension(200, 30));
		button_quit = new GenericButton("Quitter", GenericButton.QUIT ,new Dimension(200, 30));
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(10, 10, 10, 10);
		
		c.gridx = 0;
		c.gridy = 1;
		add(button_new_game, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(button_load_game, c);
		
		c.gridx = 0;
		c.gridy = 3;
		add(button_shortcut, c);
		
		c.gridx = 0;
		c.gridy = 4;
		add(button_quit, c);
	}
}
	