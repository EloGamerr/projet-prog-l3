package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.right.RightSideGame;

import javax.swing.*;

public class ButtonSaveAdaptator extends ActionAdaptator<GenericButton> {
	RightSideGame sideGame;
	
	public ButtonSaveAdaptator(GenericButton button, RightSideGame sideGame) {
		super(button);
		this.sideGame = sideGame;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		Object value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment sauvegarder la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if(value != null && (int) value == 0) {
			sideGame.getGameController().save();
		}
	}
}
