package fr.prog.tablut.view.pages.newGame;

import javax.swing.JTextField;

import fr.prog.tablut.view.components.generic.GenericInput;

/**
 * A struct of player's form data
 */
public class PlayerData {
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

	public JTextField getUsernameInput() {
		return usernameInput;
	}
}
	