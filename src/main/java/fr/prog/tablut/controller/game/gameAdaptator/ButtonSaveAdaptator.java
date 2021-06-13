package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.ActionEvent;

import fr.prog.tablut.controller.adaptators.ActionAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericButton;

import javax.swing.JOptionPane;

public class ButtonSaveAdaptator extends ActionAdaptator<GenericButton> {
	private final GameController gameController;

	public ButtonSaveAdaptator(GenericButton button, GameController gameController) {
		super(button);
		this.gameController = gameController;
	}


	@Override
	public void process(ActionEvent e) {
		Object value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment sauvegarder la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if((int) value == 0) {
			gameController.save();
		}
	}
}
