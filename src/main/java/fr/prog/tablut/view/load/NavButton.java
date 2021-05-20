package fr.prog.tablut.view.load;

import java.awt.Dimension;

import javax.swing.JPanel;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.generic.GenericButton;

public class NavButton extends JPanel{
	private final GenericButton returnButton;
	private final GenericButton nextButton;
	
	public NavButton(GlobalWindow globalWindow) {
		// TODO Auto-generated constructor stub
		this.returnButton = new GenericButton("Retour",GenericButton.GO(WindowName.HomeWindow), new Dimension(150,30));
		this.nextButton = new GenericButton("Jouer !",GenericButton.GO(WindowName.GameWindow), new Dimension(150,30));
		
		this.add(returnButton);
		this.add(nextButton);
	}
}
