package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.GamePage;

/**
* Adaptateur pour recommencer la partie
*/
public class ButtonRestartAdaptator extends ActionAdaptator<GenericButton> {
	GameController gameController;
	GamePage gamePage;
	boolean isFinished;

	public ButtonRestartAdaptator(GenericButton button, GameController gameController, boolean isFinished, GamePage gamePage) {
		super(button);
		this.gameController = gameController;
		this.isFinished = isFinished;
		this.gamePage = gamePage;
	}
	
	
	@Override
	public void process(ActionEvent e) {

		//Si le jeu est fini on redémare directement le jeu
		if(isFinished) {
			gamePage.hideVictoryPage();
			gameController.restart();
		}
		//Sinon on affiche une fenètre de confirmation avant de redémarer
		else {
			Object value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment recommencer la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(value != null && (int) value == 0) {
				gameController.restart();
			}
		}
	}
}
