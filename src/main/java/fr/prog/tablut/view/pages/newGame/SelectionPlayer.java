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

/**
 * The settings form component to create a new game.
 * <p>Extends JPanel</p>
 * @see JPanel
 */
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

	/**
	 * Default construtor.
	 * <p>Creates a new form, with 2 sides, "attaquant" and "defenseur",
	 * with their player's type and username.</p>
	 */
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

	/**
	 * Creates a form side for a player
	 * @param cc The GridBagConstraint for the side
	 * @param gridX The position of the side in the grid
	 * @param p The player's role
	 */
	private void createSide(GridBagConstraints cc, int gridX, PlayerData p) {
		cc.gridx = gridX;

		// wrapper panel
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new GridBagLayout());
		panel.setBorder(new EmptyBorder(0, 100, 0, 100));
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		
		// side title (player's role)
		c.gridy = 0;
		GenericLabel label = new GenericLabel(p.name, 15);
		label.setBorder(new EmptyBorder(0, 0, 30, 0));
		panel.add(label, c);
		
		// player's type
		c.gridy = 1;
		GenericComboBox<String> comboBox = new GenericComboBox<>(playerTypes);
		comboBox.addActionListener(new ComboBoxAdaptator(p.name, this));
		panel.add(comboBox, c);

		// player's username
		c.gridy = 2;
		c.insets = new Insets(10, 0, 0, 0);
		panel.add(p.usernameInput, c);

		add(panel);
	}
	
	/**
	 * Shows the player's username input
	 * @param side The player to show his input
	 */
	public void showInput(String side) {
		PlayerData p = (side == "Attaquant")? attaquant : defenseur;
		p.usernameInput.setVisible(true);
	}
	
	/**
	 * hides the player's username input
	 * @param side The player to hide his input
	 */
	public void hideInput(String side) {
		PlayerData p = (side == "Attaquant")? attaquant : defenseur;
		p.usernameInput.setVisible(false);
	}
}

/**
 * A struct of player's form data
 */
class PlayerData {
	public String name;
	public String username;
	public String type;
	public JTextField usernameInput;

	/**
	 * Default constructor.
	 * <p>Creates a player's data struct with the given role name and empty username</p>
	 * @param name The player role's name
	 */
	public PlayerData(String name) {
		this(name, "");
	}

	/**
	 * Default constructor.
	 * <p>Creates a player's data struct with the given role name and default username</p>
	 * @param name The player role's name
	 * @param username The default player's username
	 */
	public PlayerData(String name, String username) {
		this.name = name;
		this.username = username;
		usernameInput = new GenericInput(this.username);
	}
}