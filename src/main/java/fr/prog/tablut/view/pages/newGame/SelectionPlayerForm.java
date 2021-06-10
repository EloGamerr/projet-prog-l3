package fr.prog.tablut.view.pages.newGame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ComboBoxAdaptator;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericPanel;

/**
 * The settings form component to create a new game.
 * <p>Extends GenericPanel</p>
 * @see GenericPanel
 */
public class SelectionPlayerForm extends GenericPanel {
    // form data of the first player (data that's not sent to model)
	protected final PlayerData attacker = new PlayerData("attacker", "Attaquant", "Joueur 1");
    // form data of the second player (data that's not sent to model)
	protected final PlayerData defender = new PlayerData("defender", "D\u00e9fenseur", "Joueur 2");

	/**
	 * Default construtor.
	 * <p>Creates a new form, with 2 sides, "attaquant" and "defenseur",
	 * with their player's type and username.</p>
	 */
	public SelectionPlayerForm() {
		super(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		createSide(c, 0, attacker);

		c.insets = new Insets(100, 0, 0, 0);
		add(new GenericLabel("VS", 20));

		createSide(c, 1, defender);
	}

	/**
	 * Creates a form side for a player
	 * @param cc The GridBagConstraint for the side
	 * @param n The position of the side in the grid
	 * @param p The player's role
	 */
	private void createSide(GridBagConstraints cc, int n, PlayerData p) {
		cc.gridx = n * 2;

		// wrapper panel
		GenericPanel panel = new GenericPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(new EmptyBorder(0, 100, 0, 100));
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		
		// side title (player's role)
		c.gridy = 0;
		GenericLabel label = new GenericLabel(p.realName, 15);
		label.setBorder(new EmptyBorder(0, 0, 30, 0));
		panel.add(label, c);
		
		// player's type
		c.gridy = 1;
		p.getComboBox().addActionListener(new ComboBoxAdaptator(p.name, this));
		panel.add(p.getComboBox(), c);

		// player's username
		c.gridy = 2;
		c.insets = new Insets(10, 0, 0, 0);
		panel.add(p.getUsernameInput(), c);

		add(panel);
	}
	
	/**
	 * Shows the player's username input
	 * @param side The player to show his input
	 */
	public void showInput(String side) {
		PlayerData p = (side.equals("attacker"))? attacker : defender;
		p.getUsernameInput().enable();
	}
	
	/**
	 * hides the player's username input
	 * @param side The player to hide his input
	 */
	public void hideInput(String side) {
		PlayerData p = (side.equals("attacker"))? attacker : defender;
		p.getUsernameInput().disable();
	}

    /**
     * Returns the attacker's type
     * @return The attacker's type
     */
	public PlayerTypeEnum getPlayerType1() {
		return attacker.getPlayerType();
	}

    /**
     * Returns the defender's type
     * @return The defender's type
     */
	public PlayerTypeEnum getPlayerType2() {
		return defender.getPlayerType();
	}
	
    /**
     * Returns the defender's username
     * @return The defender's username
     */
	public String getAttackerName() {
		return attacker.getPlayerUsername();
	}
	
    /**
     * Returns the attacker's username
     * @return The attacker's username
     */
	public String getDefenderName() {
		return defender.getPlayerUsername();
	}
}


	
