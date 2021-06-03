package fr.prog.tablut.view.pages.newGame;

import fr.prog.tablut.controller.adaptators.CreateGameAdaptator;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPanel;
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

		SelectionPlayer gameSettings = new SelectionPlayer();

		BottomButtonPanel panel = new BottomButtonPanel(WindowName.HomeWindow, WindowName.GameWindow, "Jouer !");

		panel.getButton2().setAction(new CreateGameAdaptator(panel.getButton2(), gameSettings));

		NavPage page = new NavPage(
			"Nouvelle partie",
			"Choisissez les param\u00e8tres de partie qui vous conviennent puis cliquez sur confirmer",
			panel
		);
		
		// game settings
		page.setContent(gameSettings);

		add(page);
	}
}