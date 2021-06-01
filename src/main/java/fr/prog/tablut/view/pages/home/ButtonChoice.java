package fr.prog.tablut.view.pages.home;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;

/**
 * The home panel that contains all buttons.
 * <p>Extends JPanel</p>
 * @see JPanel
 */
public class ButtonChoice extends JPanel {
	/**
	 * Default constructor.
	 * <p>Creates a panel of home buttons.</p>
	 */
	public ButtonChoice() {
		setOpaque(false);
		setLayout(new GridBagLayout());

		int bWidth, bHeight;
		bWidth = 396;
		bHeight = 35;

		// list of the buttons in the home page
		LinkedHashMap<String, WindowName> buttons = new LinkedHashMap<>() {{
			put("Nouvelle partie", WindowName.NewGameWindow);
			put("Charger partie", WindowName.LoadWindow);
			put("Raccourcis", WindowName.HelpWindow);
			put("Quitter", null);
		}};

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;

		int i = 1;

		// create and add each buttons in the grid container
		for(Map.Entry<String, WindowName> button : buttons.entrySet()) {
            String label = button.getKey();
            WindowName href = button.getValue();

			GenericRoundedButton btn = new GenericRoundedButton(label, bWidth, bHeight);
			btn.setStyle("button.home");
			btn.setHref(href);

			c.gridy = i++;
			add(btn, c);
		}
	}
}
	