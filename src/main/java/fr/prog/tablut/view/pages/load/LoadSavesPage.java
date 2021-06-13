package fr.prog.tablut.view.pages.load;

import fr.prog.tablut.controller.adaptators.LoadSaveAdaptator;
import fr.prog.tablut.view.components.BottomButtonPanel;
import fr.prog.tablut.view.components.NavPage;
import fr.prog.tablut.view.pages.Page;
import fr.prog.tablut.view.pages.ThemePage;
import fr.prog.tablut.view.window.GlobalWindow;
import fr.prog.tablut.view.window.PageName;
import fr.prog.tablut.view.window.WindowConfig;

/**
 * The Load page. Extends Page class.
 * <p>Loaded games are shown in this page. The user have to click on one and confirm
 * To continue to play this game.</p>
 * @see Page
 */
public class LoadSavesPage extends ThemePage {
	protected final SavedGamesPanel panel;
	protected final BottomButtonPanel bottomPanel;

	/**
	 * Creates the load page.
	 * @param config The configuration to set to the page
	 */
	public LoadSavesPage(WindowConfig config, GlobalWindow globalWindow) {
		super(config, PageName.LoadPage);

		bottomPanel = new BottomButtonPanel(PageName.HomePage, PageName.GamePage, "Jouer !");
		panel = new SavedGamesPanel(bottomPanel.getButton2());

		bottomPanel.getButton2().setStyle("button.green:disabled");
		bottomPanel.getButton2().setHrefAction(new LoadSaveAdaptator(bottomPanel.getButton2(), panel, globalWindow));

		NavPage page = new NavPage(
			"Charger une partie",
			"Choisissez une sauvegarde \u00e0 charger",
			bottomPanel
		);

		// load games
		page.setContent(panel);

		add(page);
	}

    /**
     * Updates the saves searching in the save folder.
     * <p>Fired every time the user goes on the page</p>
     */
    @Override
    public void update() {
        panel.updateContent();
    }
}
