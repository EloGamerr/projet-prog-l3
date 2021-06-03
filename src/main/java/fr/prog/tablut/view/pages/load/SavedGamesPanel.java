package fr.prog.tablut.view.pages.load;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ButtonLoadAdaptator;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.components.generic.GenericRoundedPanel;
import fr.prog.tablut.view.components.generic.GenericScrollPane;

/**
 * A component that regroups every saved games in a list and shows them.
 * <p>Extends JPanel</p>
 * @see JPanel
 */
public class SavedGamesPanel extends JPanel {

	private final int width = 460;
	private final int height = 350;
	private final int btnHeight = 35;
	private int index_selected = 0;
	private GenericRoundedButton buttonToLightup;
    private final GenericRoundedPanel wrapperContainer;
    private final JPanel wrapper;
	
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

		wrapperContainer = new GenericRoundedPanel();
		wrapperContainer.setLayout(new BorderLayout());
		wrapperContainer.setStyle("area");
		
		wrapperContainer.setPreferredSize(size);
		wrapperContainer.setMaximumSize(size);
		wrapperContainer.setMinimumSize(size);
		
		wrapper = new JPanel();
		wrapper.setLayout(new GridBagLayout());
		wrapper.setOpaque(false);
		wrapper.setBorder(new EmptyBorder(3, 0, 3, 0));

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

    public void updateContent() {
		int i = 1;
        ArrayList<String> saves = new ArrayList<String>();
		String savePath = Paths.get(savesPath, savePrefix + i + saveSuffix).toString();
		File f = new File(savePath);

        // recover saves
		while(f.isFile()) {
            saves.add("Partie " + i);
			i++;
			savePath = Paths.get(savesPath, savePrefix + i + saveSuffix).toString();
			f = new File(savePath);
		}


        wrapper.removeAll();

        GenericRoundedButton button;
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 0;
        
        // no save found
		if(i == 1) {
            c.weightx = 0;
            GenericLabel label = new GenericLabel("Aucune partie sauvegardée", 12);
            label.setBorder(new EmptyBorder(height/2 - 20, 0, 0, 0));
			wrapper.add(label);
		}
        
        // list saves
		else {
            // calculate if number of saves overflow the wrapper
            // in that case, create a JScrollPane

            JPanel wrapperInner = new JPanel();
            GenericScrollPane scrollPane = new GenericScrollPane(wrapperInner);

            final boolean isOverflowing = saves.size() * (btnHeight+2) >= height;
            int btnWidth = width - 10;

            if(isOverflowing) {
		        wrapperInner.setLayout(new GridBagLayout());
                wrapperInner.setOpaque(false);

                scrollPane.setPreferredSize(new Dimension(width - 5, height - 5));
                btnWidth -= 25;
            }

            
            for(i=0; i < saves.size(); i++) {
                c.gridy = i;

                button = new GenericRoundedButton(saves.get(i), btnWidth, btnHeight);
                button.setStyle("button.load");
                button.addActionListener(new ButtonLoadAdaptator(button, i, this));

                if(isOverflowing)
                    wrapperInner.add(button, c);
                else
                    wrapper.add(button, c);
            }
            
            if(isOverflowing) {
                wrapper.add(scrollPane);
            }
            else {
                // align content to the top
                JPanel emptyPanel = new JPanel();
                emptyPanel.setOpaque(false);
                c.gridy = i;
                c.weighty = 1;

			    wrapper.add(emptyPanel, c);
            }
		}
    }
}