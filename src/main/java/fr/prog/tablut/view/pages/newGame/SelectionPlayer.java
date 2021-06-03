package fr.prog.tablut.view.pages.newGame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ComboBoxAdaptator;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.view.components.generic.GenericComboBox;
import fr.prog.tablut.view.components.generic.GenericInput;
import fr.prog.tablut.view.components.generic.GenericLabel;

/**
 * The settings form component to create a new game.
 * <p>Extends JPanel</p>
 * @see JPanel
 */
public class SelectionPlayer extends JPanel {

	protected PlayerData attacker = new PlayerData("attacker", "Player 1");
	protected PlayerData defender = new PlayerData("defender", "Player 2");
	protected GenericComboBox<PlayerTypeEnum> comboBox1;
	protected GenericComboBox<PlayerTypeEnum> comboBox2;

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
		GenericComboBox<PlayerTypeEnum> cb = new GenericComboBox<>(PlayerTypeEnum.values());
		cb.addActionListener(new ComboBoxAdaptator(p.name, this));
		panel.add(cb, c);

		if(n == 0) 	comboBox1 = cb;
		else		comboBox2 = cb;

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
		PlayerData p = (side == "attacker")? attacker : defender;
		p.usernameInput.setVisible(true);
	}
	
	/**
	 * hides the player's username input
	 * @param side The player to hide his input
	 */
	public void hideInput(String side) {
		PlayerData p = (side == "attacker")? attacker : defender;
		p.usernameInput.setVisible(false);
	}

	public PlayerTypeEnum getPlayerType1() {
		return (PlayerTypeEnum) this.comboBox1.getSelectedItem();
	}

	public PlayerTypeEnum getPlayerType2() {
		return (PlayerTypeEnum) this.comboBox2.getSelectedItem();
	}
	
	public PlayerData getDefender() {
		return defender;
	}
	
	public PlayerData getAttaquant() {
		return attacker;
	}
}


	
