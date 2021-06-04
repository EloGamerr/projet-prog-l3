package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericButton;

public class ButtonNavAdaptator extends ActionAdaptator<GenericButton> {
	private final PageName dest;
	private final GlobalWindow globalWindow;
	
	public ButtonNavAdaptator(GenericButton button, GlobalWindow globalWindow, PageName dest) {
		super(button);
		this.globalWindow = globalWindow;
		this.dest = dest;
	}

	@Override
	protected void process(ActionEvent e) {
		if(globalWindow != null) {
			if(dest == null)
				System.exit(0);
			else
				globalWindow.changeWindow(dest);
		}
	}
}
