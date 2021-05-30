package fr.prog.tablut.view.pages.newGame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ComboBoxAdaptator;
import fr.prog.tablut.view.components.generic.GenericComboBox;
import fr.prog.tablut.view.components.generic.GenericInput;
import fr.prog.tablut.view.components.generic.GenericLabel;

public class SelectionPlayer extends JPanel {
	protected JTextField pseudoAttaquant;
	protected JTextField pseudoDefenseur;

	protected final String[] playerTypes = {
		"Humain",
		"Ordinateur facile",
		"Ordinateur moyen",
		"Ordinateur difficile"
	};

	protected PlayerData attaquant = new PlayerData("Attaquant", "Joueur 1");
	protected PlayerData defenseur = new PlayerData("Défenseur", "Joueur 2");

	public SelectionPlayer() {
		setOpaque(false);
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		createSide(c, 0, attaquant);

		c.insets = new Insets(100, 0, 0, 0);
		add(new GenericLabel("VS", 20));

		createSide(c, 2, defenseur);
	}
	
	private void createSide(GridBagConstraints cc, int gridX, PlayerData side) {
		cc.gridx = gridX;

		JPanel pannel = new JPanel();
		pannel.setOpaque(false);
		pannel.setLayout(new GridBagLayout());
		pannel.setBorder(new EmptyBorder(0, 100, 0, 100));
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		
		c.gridy = 0;
		GenericLabel label = new GenericLabel(side.name, 15);
		label.setBorder(new EmptyBorder(0, 0, 30, 0));
		pannel.add(label, c);
				
		c.gridy = 1;
		GenericComboBox<String> comboBox = new GenericComboBox<>(playerTypes);
		comboBox.addActionListener(new ComboBoxAdaptator(side.name, this));
		pannel.add(comboBox, c);

		c.gridy = 2;
		c.insets = new Insets(10, 0, 0, 0);
		pannel.add(side.usernameInput, c);

		add(pannel);
	}
	
	public void showInput(String side) {
		PlayerData p = (side == "Attaquant")? attaquant : defenseur;
		p.usernameInput.setVisible(true);
	}
	
	public void hideInput(String side) {
		PlayerData p = (side == "Attaquant")? attaquant : defenseur;
		p.usernameInput.setVisible(false);
	}
}

class PlayerData {
	public String name;
	public String username;
	public String type;
	public JTextField usernameInput;

	public PlayerData(String name) {
		this(name, "");
	}

	public PlayerData(String name, String username) {
		this.name = name;
		this.username = username;
		usernameInput = new GenericInput(this.username);
	}
}