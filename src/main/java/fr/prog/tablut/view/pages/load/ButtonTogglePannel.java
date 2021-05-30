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

public class ButtonTogglePannel extends JPanel {
	
	public GenericRoundedButton button_selected = null;
	
	private final String SAVE_LOCATION = System.getProperty("user.dir") + "data/saves/";
	
	public ButtonTogglePannel() {
		setOpaque(false);
		setBorder(new EmptyBorder(0, 0, 50, 0));
		setLayout(new GridBagLayout());

		GenericRoundedPanel wrapper = new GenericRoundedPanel();
		wrapper.setLayout(new GridBagLayout());
		wrapper.setStyle("area");

		Dimension size = new Dimension(460, 350);

		wrapper.setPreferredSize(size);
		wrapper.setMaximumSize(size);
		wrapper.setMinimumSize(size);

		GenericRoundedButton button;
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;

		for(int i = 0; i < 6; i++) {
			c.gridy = i;
			button = new GenericRoundedButton("Partie " + (i+1) + " : (10/12/45 12:15) IA vs IA", 450, 35);
			button.setStyle("button.load");
			button.addActionListener(new ButtonToggleAdaptator(button, this));
			wrapper.add(button, c);
		}

		add(wrapper);
	}
	
	public void selected(GenericButton button) {
		if(button_selected != null) {
			button_selected.setBackground(new GenericRoundedButton().getBackground());
		}
		
		button_selected = (GenericRoundedButton)button;
		button_selected.setBackground(Color.green);
	}
}
