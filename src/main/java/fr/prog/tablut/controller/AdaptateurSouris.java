package fr.prog.tablut.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.tablut.view.center.GridWindow;

public class AdaptateurSouris extends MouseAdapter {
	private final GridWindow gridWindow;
	private final Controller controller;
	
	public AdaptateurSouris(Controller controller, GridWindow gridWindow) {
		this.controller = controller;
		this.gridWindow = gridWindow;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

 	}
}
