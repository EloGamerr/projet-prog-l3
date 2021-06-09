package fr.prog.tablut.controller.game.gameAdaptator;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import fr.prog.tablut.controller.adaptators.ActionAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.GamePage;


public class ButtonRestartAdaptator extends ActionAdaptator<GenericButton> {
	private final GameController gameController;
	private final GamePage gamePage;
	private final boolean isFinished;

	public ButtonRestartAdaptator(GenericButton button, GameController gameController, boolean isFinished, GamePage gamePage) {
		super(button);
		this.gameController = gameController;
		this.isFinished = isFinished;
		this.gamePage = gamePage;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		if(isFinished) {
			gamePage.hideVictoryPage();
			gameController.restart();
		}
		else {
			Object value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment recommencer la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if((int) value == 0) {
				gameController.restart();
			}
		}
	}
}
