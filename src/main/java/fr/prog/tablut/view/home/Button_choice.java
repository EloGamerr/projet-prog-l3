package fr.prog.tablut.view.home;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.generic.GenericButton;

public class Button_choice extends JPanel{
	private final GenericButton button_new_game;
	private final GenericButton button_load_game;
	private final GenericButton button_shortcut;
	private final GenericButton button_quit;
	
	public Button_choice(GlobalWindow globalWindow) {
		this.setLayout(new GridBagLayout());
		this.button_new_game = new GenericButton("Nouvelle partie", GenericButton.GO(WindowName.NewGameWindow), new Dimension(200,30));
		this.button_load_game = new GenericButton("Charger partie", GenericButton.GO(WindowName.LoadWindow), new Dimension(200,30));
		this.button_shortcut = new GenericButton("Raccourcis", GenericButton.GO(WindowName.HelpWindow) ,new Dimension(200,30));
		this.button_quit = new GenericButton("Quitter", GenericButton.QUIT ,new Dimension(200,30));
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(10,10,10,10);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(button_new_game,c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(button_load_game,c);
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(button_shortcut,c);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(button_quit,c);
	}
}
	