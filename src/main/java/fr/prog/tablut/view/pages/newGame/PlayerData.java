package fr.prog.tablut.view.pages.newGame;

import fr.prog.tablut.controller.adaptators.InputAdaptator;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.view.components.generic.GenericComboBox;
import fr.prog.tablut.view.components.generic.GenericInput;

/**
 * A struct of player's form data
 */
public class PlayerData {
	public String name; // code name
    public String realName; // string name

    // components
	private GenericComboBox<PlayerTypeEnum> playerType;
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

		usernameInput = new GenericInput(username);
        playerType = new GenericComboBox<>(PlayerTypeEnum.values());

        // verify text length
        usernameInput.onKeyPress(new InputAdaptator(usernameInput));
	}

    /**
     * Returns the player's form input for his username
     * @return The username's input component
     */
	public GenericInput getUsernameInput() {
		return usernameInput;
	}

    /**
     * Returns the player's form comboBox for his type
     * @return The username's comboBox component
     */
    public GenericComboBox<PlayerTypeEnum> getComboBox() {
        return playerType;
    }

    /**
     * Returns the player's type
     * @return The username's type
     */
    public PlayerTypeEnum getPlayerType() {
        return (PlayerTypeEnum) getComboBox().getSelectedItem();
    }

    /**
     * Returns the player's username
     * @return The username's username
     */
    public String getPlayerUsername() {
        return usernameInput.getText();
    }
}