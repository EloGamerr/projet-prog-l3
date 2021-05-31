package fr.prog.tablut.view.pages.load;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ButtonToggleAdaptator;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.components.generic.GenericRoundedPanel;

/**
 * A component that regroups every saved games in a list and shows them.
 * <p>Extends JPanel</p>
 * @see JPanel
 */
public class SavedGamesPannel extends JPanel {

	protected int width = 460;
	protected int height = 350;
	
	public GenericRoundedButton button_selected = null;
	
	private final String SAVE_LOCATION = System.getProperty("user.dir") + "data/saves/";
	
	/**
	 * Default constructor.
	 * <p>Creates the list of saved games and shows them.</p>
	 * <p>Applies the style "area" and "button.load".</p>
	 * @see Style
	 * @see ComponentStyle
	 */
	public SavedGamesPannel() {
		setOpaque(false);
		setBorder(new EmptyBorder(0, 0, 50, 0));
		setLayout(new GridBagLayout());

		GenericRoundedPanel wrapper = new GenericRoundedPanel();
		wrapper.setLayout(new GridBagLayout());
		wrapper.setStyle("area");

		Dimension size = new Dimension(width, height);

		wrapper.setPreferredSize(size);
		wrapper.setMaximumSize(size);
		wrapper.setMinimumSize(size);

		GenericRoundedButton button;
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;

		for(int i = 0; i < 6; i++) {
			c.gridy = i;
			button = new GenericRoundedButton("Partie " + (i+1) + " : (10/12/45 12:15) IA vs IA", width - 10, 35);
			button.setStyle("button.load");
			button.addActionListener(new ButtonToggleAdaptator(button, this));
			wrapper.add(button, c);
		}

		add(wrapper);
	}

	/**
	 * Triggered when the user clicks on a save.
	 * <p>Changes its style to button.selected.</p>
	 * @see Style
	 * @see ComponentStyle
	 * @param button the button that's been clicked on (a save)
	 */
	public void selected(GenericButton button) {
		// TODO: issue to fix : need to double-click to toggle the style
		if(button_selected != null) {
			button_selected.setStyle("button.selected");
		}
		
		button_selected = (GenericRoundedButton)button;
		button_selected.setBackground(Color.green);
	}
}
