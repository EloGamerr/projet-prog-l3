package fr.prog.tablut.view.newGame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.prog.tablut.controller.adaptators.ComboBoxAdaptator;
import fr.prog.tablut.view.generic.GenericLabel;

public class SelectionPlayer extends JPanel {
	JTextField pseudoAttaquant;
	JTextField pseudoDefenseur;
	GridBagConstraints c = new GridBagConstraints();
	public SelectionPlayer() {
		this.setLayout(new GridBagLayout());
		
		createSide("Attaquant");
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(100, 0, 0, 0);
		
		this.add(new GenericLabel("VS", 20));
		createSide("D�fenseur");
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
		comboBox.setPreferredSize(new Dimension(200,30));
		comboBox.addActionListener(new ComboBoxAdaptator(side,this));
		this.add(comboBox,c);
		
		c.gridy = 2;
		c.insets = new Insets(20, side == "Attaquant" ? 0 : 50, 0, side == "Attaquant" ? 50 : 0);
		if(side == "Attaquant") {
			pseudoAttaquant = new JTextField();
			pseudoAttaquant.setPreferredSize(new Dimension(200,30));
			pseudoAttaquant.setText("Joueur 1");
			this.add(pseudoAttaquant,c);
		}
		
		else {
			pseudoDefenseur = new JTextField();
			pseudoDefenseur.setPreferredSize(new Dimension(200,30));
			pseudoDefenseur.setText("Joueur 2");
			this.add(pseudoDefenseur,c);
		}
	}
	
	public void showInput(String side) {
		if(side == "Attaquant") {
			pseudoAttaquant.setVisible(true);
		}
		else {
			pseudoDefenseur.setVisible(true);
		}
	}
	
	public void hideInput(String side) {
		if(side == "Attaquant") {
			pseudoAttaquant.setVisible(false);
		}
		else {
			pseudoDefenseur.setVisible(false);
		}
	}
}