package fr.prog.tablut.controller.adaptators;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.prog.tablut.model.Game;

import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.view.game.EastWindow;

public class ButtonSaveAdaptator implements MouseListener {
	GameSaver save;
	EastWindow eastWindow;
	
	public ButtonSaveAdaptator(Game game, EastWindow eastWindow){
		save = game.getGameSaver();
		this.eastWindow = eastWindow;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == eastWindow.getComponent(0)) {
			save.saveNewFile();
		
		}
		else if(e.getSource() == eastWindow.getComponent(1)) {
			save.saveToFile();
		}
			
	}

	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

}
