package fr.prog.tablut.view.pages.load;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ButtonLoadAdaptator;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.components.generic.GenericRoundedPanel;

/**
 * A component that regroups every saved games in a list and shows them.
 * <p>Extends JPanel</p>
 * @see JPanel
 */
public class SavedGamesPanel extends JPanel {

	protected final int width = 460;
	protected final int height = 350;
	protected final int btnHeight = 35;
	protected int index_selected = 0;
	protected GenericRoundedButton buttonToLightup;
	
	public GenericRoundedButton button_selected = null;
	
	private final String savesPath = Paths.get(System.getProperty("user.dir"), "saves").toString();
	private static final String savePrefix = "save-";
	private static final String saveSuffix = ".sv";
	
	/**
	 * Default constructor.
	 * <p>Creates the list of saved games and shows them.</p>
	 * <p>Applies the style "area" and "button.load".</p>
	 * @see Style
	 * @see ComponentStyle
	 */
	public SavedGamesPanel(GenericRoundedButton btnToLightup) {
		setOpaque(false);
		setBorder(new EmptyBorder(0, 0, 50, 0));
		setLayout(new GridBagLayout());

		buttonToLightup = btnToLightup;

		Dimension size = new Dimension(width, height);

		GenericRoundedPanel wrapperContainer = new GenericRoundedPanel();
		wrapperContainer.setLayout(new BorderLayout());
		wrapperContainer.setStyle("area");
		
		wrapperContainer.setPreferredSize(size);
		wrapperContainer.setMaximumSize(size);
		wrapperContainer.setMinimumSize(size);
		
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new GridBagLayout());
		wrapper.setOpaque(false);
		wrapper.setBorder(new EmptyBorder(5, 0, 5, 0));

		GenericRoundedButton button;
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 0;

		int i = 1;

		String savePath = Paths.get(savesPath, savePrefix + i + saveSuffix).toString();
		File f = new File(savePath);

		while(f.isFile()) {
			c.gridy = i;
			button = new GenericRoundedButton("Save n\u00B0" + i, width - 10, btnHeight);
			button.setStyle("button.load");
			button.addActionListener(new ButtonLoadAdaptator(button, i, this));
			wrapper.add(button, c);
			i++;
			savePath = Paths.get(savesPath, savePrefix + i + saveSuffix).toString();
			f = new File(savePath);
		}

		if(i == 1) {
			// no save found
			wrapper.add(new GenericLabel("No save found", 12));
		}

		else {
			// align content to the top
			JPanel emptyPanel = new JPanel();
			emptyPanel.setOpaque(false);
			c.gridy = i;
			c.weighty = 1;
			wrapper.add(emptyPanel, c);
		}

		wrapperContainer.add(wrapper, BorderLayout.NORTH);
		add(wrapperContainer);
	}

	/**
	 * Triggered when the user clicks on a save.
	 * <p>Changes its style to button.selected.</p>
	 * @see Style
	 * @see ComponentStyle
	 * @param button the button that's been clicked on (a save)
	 */
	public void selected(GenericButton button, int index) {
		//TODO : issue to fix : need to double-click to toggle the style
		if(button_selected != null) {
			button_selected.setStyle("button.load");
		}
		
		button_selected = (GenericRoundedButton)button;
		button_selected.setStyle("button.load:selected");
		index_selected = index;

		if(buttonToLightup.getStyle() != "button.green")
			buttonToLightup.setStyle("button.green");
	}

	public int getSelectedIndex() {
		return index_selected;
	}
}
