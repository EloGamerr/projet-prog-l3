package fr.prog.tablut.view.pages.home;

import javax.swing.JPanel;

import fr.prog.tablut.view.components.generic.GenericLabel;

@SuppressWarnings("serial")
public class Title extends JPanel {
	private final GenericLabel label;
	
	public Title() {
		label = new GenericLabel("TABLUT", 70);
		add(label);
		setOpaque(false);
	}
}
