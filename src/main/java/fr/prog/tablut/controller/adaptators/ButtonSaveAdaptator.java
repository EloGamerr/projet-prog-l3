package fr.prog.tablut.controller.adaptators;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.prog.tablut.model.game.Game;

import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

public class ButtonSaveAdaptator implements MouseListener {
	GameSaver save;
	GameInterfaceSide leftSideGame;
	
	public ButtonSaveAdaptator(Game game, GameInterfaceSide leftSideGame) {
		save = game.getGameSaver();
		this.leftSideGame = leftSideGame;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == leftSideGame.getComponent(0)) {
			save.saveNewFile();
		
		}
		else if(e.getSource() == leftSideGame.getComponent(1)) {
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
