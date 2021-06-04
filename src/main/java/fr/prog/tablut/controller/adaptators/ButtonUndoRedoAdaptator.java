package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.game.sides.left.MoveButtons;
import fr.prog.tablut.view.pages.game.sides.right.RightSideGame;

public class ButtonUndoRedoAdaptator extends ActionAdaptator<GenericButton> {
	MoveButtons sideGame;
	RightSideGame rightSide;

	public ButtonUndoRedoAdaptator(GenericButton button, MoveButtons moveButtons, RightSideGame rightSide) {
		super(button);
		this.sideGame = moveButtons;
		this.rightSide = rightSide;
	}
	
	
	@Override
	public void process(ActionEvent e) {
		if(e.getSource() == sideGame.getComponent(0))
			sideGame.getGameController().undo();
        
		else if(e.getSource() == sideGame.getComponent(1))
			sideGame.getGameController().redo();

		rightSide.togglePauseButton(Game.getInstance().isPaused());
	}
}