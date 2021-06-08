package fr.prog.tablut.view.pages.load;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.controller.adaptators.ButtonDeleteSaveAdaptator;
import fr.prog.tablut.controller.adaptators.ButtonLoadAdaptator;
import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.structures.Couple;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.components.generic.GenericRoundedPanel;
import fr.prog.tablut.view.components.generic.GenericScrollPane;
import fr.prog.tablut.view.components.generic.TextAlignment;

/**
 * A component that regroups every saved games in a list and shows them.
 * <p>Extends GenericPanel</p>
 * @see GenericPanel
 */
public class SavedGamesPanel extends GenericPanel {
	private final int width = 460;
	private final int height = 350;
	private final int btnHeight = 35;
	private int index_selected = 0;
    private final GenericPanel wrapper;
    private final GenericRoundedPanel wrapperContainer;
	private GenericRoundedButton buttonToLightup;
    private GenericPanel wrapperInner = null;
	
	public GenericRoundedButton button_selected = null;
	
	/**
	 * Default constructor.
	 * <p>Creates the list of saved games and shows them.</p>
	 * <p>Applies the style "area" and "button.load".</p>
	 * @see Style
	 * @see ComponentStyle
	 */
	public SavedGamesPanel(GenericRoundedButton btnToLightup) {
		super(new GridBagLayout());
        setBorder(new EmptyBorder(0, 0, 50, 0));

        // the confirm button that's enabled only if a save is chosen
		buttonToLightup = btnToLightup;

		Dimension size = new Dimension(width, height);

        // the rounded wrapper that's drawn
		wrapperContainer = new GenericRoundedPanel();
		wrapperContainer.setLayout(new BorderLayout());
		wrapperContainer.setStyle("area");
		
		wrapperContainer.setPreferredSize(size);
		wrapperContainer.setMaximumSize(size);
		wrapperContainer.setMinimumSize(size);
		
        // invisible inside wrapper that's used to organisze items
		wrapper = new GenericPanel(new GridBagLayout());
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
	public void select(GenericButton button, int index) {
        // remove style of previously chose save
		if(button_selected != null)
			button_selected.setStyle("button.load");
		
        // update the style of chose one
		button_selected = (GenericRoundedButton)button;
		button_selected.setStyle("button.load:selected");
		index_selected = index;

        // enable the confirm button
		enableConfirmButton();
	}

    /**
     * Enables the confirm button
     */
    public void enableConfirmButton() {
        if(buttonToLightup.getStyle() != "button.green")
			buttonToLightup.setStyle("button.green");
    }

    /**
     * Disables the confirm button
     */
    public void disableConfirmButton() {
        if(buttonToLightup.getStyle() != "button.green:disabled")
            buttonToLightup.setStyle("button.green:disabled");
    }

    /**
     * Returns the index of the selected save
     * @return The index of the selected save in the list
     */
	public int getSelectedIndex() {
		return index_selected;
	}

    /**
     * Updates the content in the list of the saves
     */
    public void updateContent() {
		ArrayList<Couple<String, Integer>> saves = GameSaver.getInstance().getSavesNames();
        
        wrapper.removeAll();
        
        // no save found
		if(saves.size() == 0) {
            GenericLabel label = new GenericLabel("Aucune partie sauvegard\u00e9e", 12);
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

            // in the case there're more saves than the container, create a scrollPane
            wrapperInner = new GenericPanel(new GridBagLayout());
            GenericScrollPane scrollPane = new GenericScrollPane(wrapperInner);
            GenericRoundedButton buttonLoad;
            GenericRoundedButton buttonDelete;

            final boolean isOverflowing = saves.size() * (btnHeight+2) >= height;
            int btnWidth = width - 10;

            if(isOverflowing) {
                scrollPane.setPreferredSize(new Dimension(width - 5, height - 5));
                btnWidth -= 25;
            }
            else {
                // delete wrapperInner (then the scrollPane and all its items)
                wrapperInner.removeAll();
                wrapperInner = null;
            }
            
            // for each save, create a new button to select it, and one to delete it
            for(int i=0; i < saves.size(); i++) {
                c.gridy = i;
                String saveName = saves.get(i).getFirst();

                GenericPanel saveBtn = new GenericPanel(new GridBagLayout());
                saveBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
                GridBagConstraints cs = new GridBagConstraints();

                cs.gridx = 0;
                cs.gridy = 0;

                buttonLoad = new GenericRoundedButton(saveName, btnWidth - btnHeight, btnHeight);
                buttonLoad.setStyle("button.load");
                buttonLoad.alignText(TextAlignment.LEFT);
                buttonLoad.addActionListener(new ButtonLoadAdaptator(buttonLoad, saves.get(i).getSecond(), this));

                buttonDelete = new GenericRoundedButton("", btnHeight, btnHeight);
                buttonDelete.setStyle("button.load");
                buttonDelete.setImage("theme/cross.png", 10, 10, btnHeight - 20, btnHeight - 20);
                buttonDelete.addActionListener(new ButtonDeleteSaveAdaptator(buttonDelete, saves.get(i).getSecond(), saveBtn, this));

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
                // align content to the top pushing these with an invisible panel
                // that takes the rest of the place
                GenericPanel emptyPanel = new GenericPanel();
                c.gridy = saves.size();
                c.weighty = 1;

			    wrapper.add(emptyPanel, c);
            }
		}
    }

    /**
     * Delete the given save component
     * @param saveButtonToDelete
     */
    public void deleteSave(JPanel saveButtonToDelete) {
        final boolean win = wrapperInner == null;

        JPanel w = win? wrapper : wrapperInner;

        w.remove(saveButtonToDelete);

        int n = w.getComponentCount();

        // recalculate the number of saves to reorganize these.
        if(!win && n * (btnHeight+2) < height) {
            Component cpnts[] = w.getComponents();

            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.NORTH;
            c.gridx = 0;
            c.weightx = 1;
            c.weighty = 0;
            int i;
            
            for(i=0; i < cpnts.length; i++) {
                GenericPanel b = (GenericPanel)cpnts[i];
                c.gridy = i;
                wrapper.add(b, c);
            }

            // align content to the top
            GenericPanel emptyPanel = new GenericPanel();
            c.gridy = i;
            c.weighty = 1;

            wrapper.add(emptyPanel, c);
            wrapper.remove(0);

            wrapperInner.removeAll();
            wrapperInner = null;
        }

        // it deleted the last save - no more save
        if(n == 1) {
            GenericLabel label = new GenericLabel("Aucune partie sauvegard\u00e9e", 12);
            label.setBorder(new EmptyBorder(height/2 - 20, 0, 0, 0));
            wrapper.add(label);
        }

        revalidate();
        repaint();
    }
}