package fr.prog.tablut.view.load;

import java.awt.Dimension;

import javax.swing.JPanel;

import fr.prog.tablut.controller.adaptators.ButtonNavAdaptator;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.generic.GenericButton;

public class NavButton extends JPanel{
	private final GenericButton returnButton;
	private final GenericButton nextButton;
	
	public NavButton(GlobalWindow globalWindow) {
		// TODO Auto-generated constructor stub
		this.returnButton = new GenericButton("Retour",new ButtonNavAdaptator(globalWindow, "HomeWindow"), new Dimension(150,30));
		this.nextButton = new GenericButton("Jouer !",new ButtonNavAdaptator(globalWindow, "GameWindow"), new Dimension(150,30));
		
		this.add(returnButton);
		this.add(nextButton);
	}
}
