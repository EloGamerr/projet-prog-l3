package fr.prog.tablut.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import fr.prog.tablut.view.game.GridWindow;

public class AdaptateurSouris extends MouseAdapter {
	private final GridWindow gridWindow;
	private final Controller controller;
	
	public AdaptateurSouris(Controller controller, GridWindow gridWindow) {
		this.controller = controller;
		this.gridWindow = gridWindow;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			int	l = gridWindow.getRowFromYCoord(e.getY());
			int c = gridWindow.getColFromXCoord(e.getX());
			controller.click(l, c);
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
			controller.undoSelect();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		controller.mouseMoved(e.getPoint());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		controller.mouseMoved(e.getPoint());
	}
}
