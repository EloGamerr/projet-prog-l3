package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.left.MoveButtons;

public class ButtonUndoRedoAdaptator extends ActionAdaptator<GenericButton> {
	MoveButtons sideGame;
	
	public ButtonUndoRedoAdaptator(GenericButton button, MoveButtons moveButtons) {
		super(button);
		this.sideGame = moveButtons;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		if(e.getSource() == sideGame.getComponent(0)) 
			sideGame.getGameController().undo();
        
		else if(e.getSource() == sideGame.getComponent(1))
			sideGame.getGameController().redo();
	}
}