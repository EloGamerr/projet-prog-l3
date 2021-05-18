package fr.prog.tablut.view.home;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.prog.tablut.controller.adaptators.ButtonNewGameAdaptator;
import fr.prog.tablut.view.GlobalWindow;

@SuppressWarnings("serial")
public class Button_choice extends JPanel{
	private final JButton button_new_game;
	private final JButton button_load_game;
	private final JButton button_shortcut;
	private final JButton button_quit;
	
	public Button_choice(GlobalWindow globalWindow) {
		this.setLayout(new GridBagLayout());
		this.button_new_game = new JButton("Nouvelle partie");
		this.button_load_game = new JButton("Charger partie");
		this.button_shortcut = new JButton("Raccourcis");
		this.button_quit = new JButton("Quitter");
		
		this.button_new_game.addActionListener(new ButtonNewGameAdaptator(globalWindow));
		
		this.button_new_game.setPreferredSize(new Dimension(200,30));
		this.button_load_game.setPreferredSize(new Dimension(200,30));
		this.button_shortcut.setPreferredSize(new Dimension(200,30));
		this.button_quit.setPreferredSize(new Dimension(200,30));
		
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
