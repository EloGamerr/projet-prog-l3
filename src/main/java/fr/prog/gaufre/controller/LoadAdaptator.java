package fr.prog.gaufre.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadAdaptator extends MouseAdapter{
	Controller controller;
	public LoadAdaptator(Controller controller) {
		// TODO Auto-generated constructor stub
		this.controller = controller;
	}
	
	public void mouseClicked(MouseEvent e) {
		controller.load();
	}
}
