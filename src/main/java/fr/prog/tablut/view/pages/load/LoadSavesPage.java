package fr.prog.tablut.view.pages.load;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPanel;
import fr.prog.tablut.view.components.NavPage;

/**
 * The Load page. Extends Page class.
 * <p>Loaded games are shown in this page. The user have to click on one and confirm
 * To continue to play this game.</p>
 * @see Page
 */
public class LoadSavesPage extends Page {
	protected SavedGamesPanel panel;

	/**
	 * Creates the load page.
	 * @param config The configuration to set to the page
	 */
	public LoadSavesPage(WindowConfig config) {
		super(config);

		windowName = WindowName.LoadWindow;

		NavPage page = new NavPage(
			"Charger une partie",
			"Choisissez une sauvegarde à charger",
			new BottomButtonPanel(WindowName.HomeWindow, WindowName.GameWindow, "Jouer !")
		);
		
		panel = new SavedGamesPanel();

		// load games
		page.setContent(panel);

		add(page);
	}

	public SavedGamesPanel getPanel() {
		return panel;
	}
}
