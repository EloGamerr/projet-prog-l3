package fr.prog.tablut.view.help;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import fr.prog.tablut.view.generic.GenericLabel;

public class HelpPannel extends JPanel{
	
	private final GridBagConstraints c = new GridBagConstraints();
	
	public HelpPannel() {
		this.setLayout(new GridBagLayout());
		
		createHelpTitle();
		
		createHelpLine("CRTL+S", "Sauvegarder");
		createHelpLine("Espace", "Pause (fonctionne uniquement en ordinateur contre ordinateur)");
		createHelpLine("CRTL+Z", "Annuler le dernier coup");
		createHelpLine("CRTL+Y", "Refaire le dernier coup");
		createHelpLine("CRTL+N", "Recommencer la partie");
	}
	
	private void createHelpTitle() {
		GenericLabel label_key = new GenericLabel("TOUCHES RACCOURCIS", 20);
		GenericLabel label_description = new GenericLabel("DESCRIPTION", 20);
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 50, 350);
		this.add(label_key,c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 50, 0);
		this.add(label_description,c);
		
		c.gridy = 1;
	}
	
	private void createHelpLine(String Shortcut, String Description) {
		GenericLabel label_key = new GenericLabel(Shortcut, 18);
		GenericLabel label_description = new GenericLabel(Description, 18);
		
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.insets = new Insets(0, 0, 30, 350);
		this.add(label_key,c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 30, 0);
		this.add(label_description,c);
		
		c.gridy = c.gridy + 1;
	}
}
