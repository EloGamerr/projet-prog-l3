package fr.prog.tablut.view.home;

import javax.swing.JPanel;

import fr.prog.tablut.view.generic.GenericLabel;

@SuppressWarnings("serial")
public class Title extends JPanel{
	private final GenericLabel label;
	
	public Title() {
		this.label = new GenericLabel("TABULT", 70);
		this.add(label);
	}
}
