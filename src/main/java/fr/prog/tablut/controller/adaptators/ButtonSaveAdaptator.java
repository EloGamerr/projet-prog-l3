package fr.prog.tablut.controller.adaptators;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.prog.tablut.model.Game;

import fr.prog.tablut.model.saver.GameSaver;

public class ButtonSaveAdaptator implements MouseListener {
	GameSaver save;
	
	public ButtonSaveAdaptator(Game game){
		if(game.getCurrentSavePath().matches(""))
			save = new GameSaver(game);
		else
			save = new GameSaver(game,game.getCurrentSavePath());
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		save.saveToFile();
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
