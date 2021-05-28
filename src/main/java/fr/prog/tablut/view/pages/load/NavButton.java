package fr.prog.tablut.view.pages.load;

import java.awt.Dimension;

import javax.swing.JPanel;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericButton;

public class NavButton extends JPanel {
	private final GenericButton returnButton;
	private final GenericButton nextButton;
	
	public NavButton(GlobalWindow globalWindow) {
		setOpaque(false);
		returnButton = new GenericButton("Retour", GenericButton.GO(WindowName.HomeWindow), new Dimension(150, 30));
		nextButton = new GenericButton("Jouer !", GenericButton.GO(WindowName.GameWindow), new Dimension(150, 30));
		
		add(returnButton);
		add(nextButton);
	}
}
