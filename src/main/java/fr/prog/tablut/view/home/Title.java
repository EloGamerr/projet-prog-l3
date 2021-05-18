package fr.prog.tablut.view.home;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Title extends JPanel{
	private final JLabel label;
	
	public Title() {
		this.label = new JLabel("TABULT");
		this.label.setFont(new Font("Farro", Font.PLAIN, 70));
		this.add(label);
	}
}
