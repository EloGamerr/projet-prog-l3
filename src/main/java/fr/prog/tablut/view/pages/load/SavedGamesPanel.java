package fr.prog.tablut.view.pages.load;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ButtonDeleteSaveAdaptator;
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
    private JPanel wrapperInner = null;
	
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
        
        // no save found
		if(i == 1) {
            GenericLabel label = new GenericLabel("Aucune partie sauvegardée", 12);
            label.setBorder(new EmptyBorder(height/2 - 20, 0, 0, 0));
			wrapper.add(label);
		}
        
        // list saves
		else {
            // calculate if number of saves overflow the wrapper
            // in that case, create a JScrollPane

            GridBagConstraints c = new GridBagConstraints();

            c.anchor = GridBagConstraints.NORTH;
            c.gridx = 0;
            c.weightx = 1;
            c.weighty = 0;

            wrapperInner = new JPanel();
            GenericScrollPane scrollPane = new GenericScrollPane(wrapperInner);
            GenericRoundedButton buttonLoad;
            GenericRoundedButton buttonDelete;

            final boolean isOverflowing = saves.size() * (btnHeight+2) >= height;
            int btnWidth = width - 10;

            if(isOverflowing) {
		        wrapperInner.setLayout(new GridBagLayout());
                wrapperInner.setOpaque(false);

                scrollPane.setPreferredSize(new Dimension(width - 5, height - 5));
                btnWidth -= 25;
            }
            else {
                wrapperInner.removeAll();
                wrapperInner = null;
            }
            
            for(i=0; i < saves.size(); i++) {
                c.gridy = i;
                String saveName = saves.get(i); // TODO: set the real save name with date/time/vs

                JPanel saveBtn = new JPanel();
                saveBtn.setOpaque(false);
                saveBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
                saveBtn.setLayout(new GridBagLayout());
                GridBagConstraints cs = new GridBagConstraints();

                cs.gridx = 0;
                cs.gridy = 0;

                buttonLoad = new GenericRoundedButton(saveName, btnWidth - btnHeight, btnHeight);
                buttonLoad.setStyle("button.load");
                buttonLoad.addActionListener(new ButtonLoadAdaptator(buttonLoad, i, this));

                buttonDelete = new GenericRoundedButton("", btnHeight, btnHeight);
                buttonDelete.setStyle("button.load");
                buttonDelete.setImage("cross.png", 10, 10, btnHeight - 20, btnHeight - 20);
                buttonDelete.addActionListener(new ButtonDeleteSaveAdaptator(buttonDelete, i, saveBtn, this));

                saveBtn.add(buttonLoad, cs);
                cs.gridx = 2;
                saveBtn.add(buttonDelete, cs);


                if(isOverflowing)
                    wrapperInner.add(saveBtn, c);
                else
                    wrapper.add(saveBtn, c);
            }
            
            if(isOverflowing)
                wrapper.add(scrollPane);

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

    public void deleteSave(JPanel saveButtonToDelete) {
        final boolean win = wrapperInner == null;
        JPanel w = win? wrapper : wrapperInner;

        w.remove(saveButtonToDelete);

        int n = w.getComponentCount();

        if(!win && n * (btnHeight+2) < height) {
            Component cpnts[] = w.getComponents();

            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.NORTH;
            c.gridx = 0;
            c.weightx = 1;
            c.weighty = 0;
            int i;
            
            for(i=0; i < cpnts.length; i++) {
                JPanel b = (JPanel)cpnts[i];
                c.gridy = i;
                wrapper.add(b, c);
            }

            // align content to the top
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            c.gridy = i;
            c.weighty = 1;

            wrapper.add(emptyPanel, c);
            wrapper.remove(0);

            wrapperInner.removeAll();
            wrapperInner = null;
        }

        if(n == 1) {
            GenericLabel label = new GenericLabel("Aucune partie sauvegardée", 12);
            label.setBorder(new EmptyBorder(height/2 - 20, 0, 0, 0));
            wrapper.add(label);
        }
    }
}