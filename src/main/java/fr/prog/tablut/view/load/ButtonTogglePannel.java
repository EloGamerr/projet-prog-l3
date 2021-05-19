package fr.prog.tablut.view.load;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.prog.tablut.controller.adaptators.ButtonToggleAdaptator;
import fr.prog.tablut.view.generic.GenericButton;

public class ButtonTogglePannel extends JPanel{
	
	public GenericButton button_selected = null;
	
	private final String SAVE_LOCATION = System.getProperty("user.dir") + "data/saves/";
	
	public ButtonTogglePannel() {
		// TODO Auto-generated constructor stub
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		GenericButton button;
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.insets = new Insets(0, 0, 0, 0);
		for(int i = 0; i < 6; i++) {
			c.gridy = i;
			button = new GenericButton("Button number " + i,new Dimension(300,30));
			button.addActionListener(new ButtonToggleAdaptator(button, this));
			this.add(button,c);
		}
	}
	
	public void selected(GenericButton button) {
		if(button_selected != null) {
			button_selected.setBackground(new GenericButton("").getBackground());
		}
		
		button_selected = button;
		button_selected.setBackground(Color.green);
		
	}
}
