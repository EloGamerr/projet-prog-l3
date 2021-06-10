package fr.prog.tablut.view.pages.home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.window.PageName;

/**
 * The home panel that contains all buttons.
 * <p>Extends GenericPanel</p>
 * @see GenericPanel
 */
public class ButtonChoice extends GenericPanel {
	/**
	 * Default constructor.
	 * <p>Creates a panel of home buttons.</p>
	 */
	public ButtonChoice() {
		super(new GridBagLayout());

		int bWidth, bHeight;
		bWidth = 396;
		bHeight = 35;

		// list of the buttons in the home page
		LinkedHashMap<String, PageName> buttons = new LinkedHashMap<String, PageName>() {{
			put("Nouvelle partie", PageName.NewGamePage);
			put("Charger partie", PageName.LoadPage);
			put("Raccourcis", PageName.HelpPage);
			put("Quitter", null);
		}};

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;

		int i = 1;

		// create and add each buttons in the grid container
		for(Map.Entry<String, PageName> button : buttons.entrySet()) {
            String label = button.getKey();
            PageName href = button.getValue();

			GenericRoundedButton btn = new GenericRoundedButton(label, bWidth, bHeight);
			btn.setStyle("button.home");
			btn.setHref(href);

			c.gridy = i++;
			add(btn, c);
		}
	}
}
	