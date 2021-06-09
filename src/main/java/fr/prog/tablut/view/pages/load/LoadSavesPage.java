package fr.prog.tablut.view.pages.load;

import fr.prog.tablut.controller.adaptators.LoadSaveAdaptator;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.BottomButtonPanel;
import fr.prog.tablut.view.components.ImageComponent;
import fr.prog.tablut.view.components.NavPage;

/**
 * The Load page. Extends Page class.
 * <p>Loaded games are shown in this page. The user have to click on one and confirm
 * To continue to play this game.</p>
 * @see Page
 */
public class LoadSavesPage extends Page {
	protected final SavedGamesPanel panel;
	protected final BottomButtonPanel bottomPanel;

	/**
	 * Creates the load page.
	 * @param config The configuration to set to the page
	 */
	public LoadSavesPage(WindowConfig config) {
		super(config);
		windowName = PageName.LoadPage;

		
		bottomPanel = new BottomButtonPanel(PageName.HomePage, PageName.GamePage, "Jouer !");
		panel = new SavedGamesPanel(bottomPanel.getButton2());

		bottomPanel.getButton2().setStyle("button.green:disabled");
		bottomPanel.getButton2().setAction(new LoadSaveAdaptator(bottomPanel.getButton2(), panel));

		NavPage page = new NavPage(
			"Charger une partie",
			"Choisissez une sauvegarde \u00e0 charger",
			bottomPanel
		);
		
		// load games
		page.setContent(panel);

		add(page);

        
        // setup background pannel
        final int iconSize = 500;
        ImageComponent blackTower = new ImageComponent("theme/transparent_black_tower.png", -iconSize/2, config.windowHeight/2 - iconSize/2, iconSize, iconSize);
        ImageComponent whiteTower = new ImageComponent("theme/transparent_white_tower.png", config.windowWidth-iconSize/2, config.windowHeight/2 - iconSize/2, iconSize, iconSize);

        blackTower.load();
        whiteTower.load();

        backgroundPanel.add(blackTower);
        backgroundPanel.add(whiteTower);
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
