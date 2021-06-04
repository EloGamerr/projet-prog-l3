package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericButton;

import javax.swing.*;


public class ButtonRestartAdaptator extends ActionAdaptator<GenericButton> {
	GameController gameController;
	
	public ButtonRestartAdaptator(GenericButton button, GameController gameController) {
		super(button);
		this.gameController = gameController;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		Object value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment recommencer la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if(value != null && (int) value == 0) {
			gameController.restart();
		}
	}
}
