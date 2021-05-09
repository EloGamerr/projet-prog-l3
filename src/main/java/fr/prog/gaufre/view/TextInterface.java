package fr.prog.gaufre.view;

import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.prog.gaufre.controller.Controller;

public class TextInterface extends JPanel{
	public TextInterface(Controller controller) {
		JTextField textField = new JTextField("Coucou", 30);
		this.add(textField);
	}
	
	
}
