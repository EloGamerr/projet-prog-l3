package fr.prog.tablut.view.newGame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import fr.prog.tablut.controller.adaptators.ComboBoxAdaptator;
import fr.prog.tablut.view.generic.GenericLabel;

public class SelectionPlayer extends JPanel {
	GridBagConstraints c = new GridBagConstraints();
	public SelectionPlayer() {
		this.setLayout(new GridBagLayout());
		
		createSide("Attaquant");
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(100, 0, 0, 0);
		
		this.add(new GenericLabel("VS", 20));
		createSide("Deffenseur");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createSide(String side) {
		c.gridx = side == "Attaquant" ? 0 : 2;
		c.gridy = 0;
		c.insets = new Insets(100, side == "Attaquant" ? 0 : 50, 0, side == "Attaquant" ? 50 : 0);
		String[] choices = {
				"Humain",
				"Ordinateur facile",
				"Ordinateur moyen",
				"Ordinateur difficile"
		};
		this.add(new GenericLabel(side, 15),c);
		
		c.insets = new Insets(0, side == "Attaquant" ? 0 : 50, 0, side == "Attaquant" ? 50 : 0);
		c.gridy = 1;
		
		JComboBox comboBox = new JComboBox(choices);
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(new ComboBoxAdaptator(side,this));
		this.add(comboBox,c);
	}
}