package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericButton;

/**
* Adaptateur pour les boutons de navigations
*/
public class ButtonNavAdaptator extends ActionAdaptator<GenericButton> {
	private final PageName dest;
	private final GlobalWindow globalWindow;
	
	/**
	* @param button Le component bouton
	* @param globalWindow l'instance de la globalWindow
	* @param savedGamesPanel La page de destination
	*/
	public ButtonNavAdaptator(GenericButton button, GlobalWindow globalWindow, PageName dest) {
		super(button);
		this.globalWindow = globalWindow;
		this.dest = dest;
	}

	@Override
	protected void process(ActionEvent e) {
		//Si la globalWindow est active on va activer le changement de page
		if(globalWindow != null) {
			if(dest == null)
				System.exit(0);
			else
				globalWindow.changeWindow(dest);
		}
	}
}
