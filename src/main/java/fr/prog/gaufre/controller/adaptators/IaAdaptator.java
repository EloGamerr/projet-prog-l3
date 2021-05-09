package fr.prog.gaufre.controller.adaptators;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import fr.prog.gaufre.controller.Controller;

public class IaAdaptator extends MouseAdapter{
	private String[] allOptions = {"Joueur ", "IA aléat", "IA win/loose" , "IA min/max"};
	private int joueur;
	private Controller controller;
	private int current_player = 0;
	JButton button;
	
	public IaAdaptator(Controller controller, int joueur, JButton button) {
		// TODO Auto-generated constructor stub
		this.controller = controller;
		this.joueur = joueur;
		this.allOptions[0] = this.allOptions[0] + Integer.toString(joueur);
		this.button = button;
	}
	
	public void mouseClicked(MouseEvent e) {
		current_player = (current_player + 1) % allOptions.length;
		this.button.setText(allOptions[current_player]);
	}
}
