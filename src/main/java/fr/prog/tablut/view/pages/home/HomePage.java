package fr.prog.tablut.view.pages.home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.view.components.Title;
import fr.prog.tablut.view.pages.Page;
import fr.prog.tablut.view.pages.ThemePage;
import fr.prog.tablut.view.window.PageName;
import fr.prog.tablut.view.window.WindowConfig;

/**
 * The home page. Extends Page class.
 * <p>Shows home's button.</p>
 * @see Page
 */
public class HomePage extends ThemePage {
	/**
	 * Creates the home page.
	 * @param config The configuration to set to the page
	 */
	public HomePage(WindowConfig config) {
		super(config, PageName.HomePage);

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
	}
}
