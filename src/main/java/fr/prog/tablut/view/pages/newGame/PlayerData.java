package fr.prog.tablut.view.pages.newGame;

import fr.prog.tablut.controller.adaptators.InputAdaptator;
import fr.prog.tablut.view.components.generic.GenericInput;

/**
 * A struct of player's form data
 */
public class PlayerData {
	public String name;
    public String realName;
	public String username;
	public String type;

	private GenericInput usernameInput;

	/**
	 * Default constructor.
	 * <p>Creates a player's data struct with the given role name and empty username</p>
	 * @param name The player role's name
	 */
	public PlayerData(String name) {
		this(name, name, "");
	}

	/**
	 * Default constructor.
	 * <p>Creates a player's data struct with the given role name and default username</p>
	 * @param name The player role's name
	 * @param username The default player's username
	 */
	public PlayerData(String name, String realName, String username) {
		this.name = name;
        this.realName = realName;
		this.username = username;
		usernameInput = new GenericInput(this.username);

        // verify text length
        usernameInput.onKeyPress(new InputAdaptator(usernameInput));
	}

	public GenericInput getUsernameInput() {
		return usernameInput;
	}
}