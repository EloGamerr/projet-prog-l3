package fr.prog.gaufre.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.gaufre.view.GameWindow;

public class AdaptateurSouris extends MouseAdapter {
	GameWindow g;
	Controller controller;
	
	public AdaptateurSouris(Controller controller, GameWindow g) {
		this.controller = controller;
		this.g = g;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int c = e.getX() / g.longueur_case();
		int l = e.getY() / g.largeur_case();
		System.out.println(String.format("Clik on point %d,%d", c,l));
		controller.click(l, c);
 	}
}
