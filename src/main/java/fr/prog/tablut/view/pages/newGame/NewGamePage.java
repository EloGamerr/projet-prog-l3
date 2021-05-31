package fr.prog.tablut.view.pages.newGame;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPannel;
import fr.prog.tablut.view.components.NavPage;

/**
 * The new game page. Extends Page class.
 * <p>The nesw game settings form. Once the player
 * clicks on the confirm button, starts the game.</p>
 * @see Page
 */
public class NewGamePage extends Page {
	/**
	 * Creates the new game page.
	 * @param config The configuration to set to the page
	 */
	public NewGamePage(WindowConfig config) {
		super(config);

		windowName = WindowName.NewGameWindow;

		NavPage page = new NavPage(
			"Nouvelle partie",
			"Choisissez les paramètres de partie qui vous conviennent puis cliquez sur confirmer",
			new BottomButtonPannel(WindowName.HomeWindow, WindowName.GameWindow, "Jouer !")
		);
		
		// game settings
		page.setContent(new SelectionPlayer());

		add(page);
	}
}