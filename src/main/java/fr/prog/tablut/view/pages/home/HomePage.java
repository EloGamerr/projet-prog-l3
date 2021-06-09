package fr.prog.tablut.view.pages.home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.components.ImageComponent;
import fr.prog.tablut.view.components.Title;

/**
 * The home page. Extends Page class.
 * <p>Shows home's button.</p>
 * @see Page
 */
public class HomePage extends Page {

	/**
	 * Creates the home page.
	 * @param config The configuration to set to the page
	 */
	public HomePage(WindowConfig config) {
		super(config);
		windowName = PageName.HomePage;

		setPLayout(new GridBagLayout());

		Title title = new Title("TABLUT");
		ButtonChoice button_choice = new ButtonChoice();
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(0, 0, 50, 0);
		
		c.gridx = 0;
		c.gridy = 1;
		add(title, c);
		
		c.gridx = 0;
		c.gridy = 2;
		add(button_choice, c);

        
        // setup background pannel
        final int iconSize = 500;
        ImageComponent blackTower = new ImageComponent("theme/transparent_black_tower.png", -iconSize/2, config.windowHeight/2 - iconSize/2, iconSize, iconSize);
        ImageComponent whiteTower = new ImageComponent("theme/transparent_white_tower.png", config.windowWidth-iconSize/2, config.windowHeight/2 - iconSize/2, iconSize, iconSize);

        blackTower.load();
        whiteTower.load();

        backgroundPanel.add(blackTower);
        backgroundPanel.add(whiteTower);
	}
}
