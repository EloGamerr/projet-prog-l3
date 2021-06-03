package fr.prog.tablut.view.pages.game.sides.left;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import fr.prog.tablut.controller.adaptators.ButtonUndoRedoAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;

public class MoveButtons extends JLabel {
	GameController gameController;
    public MoveButtons(Dimension d, GameController gameController) {
        setOpaque(false);
        this.gameController = gameController;

        setLayout(new GridBagLayout());
        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
        GridBagConstraints lc = new GridBagConstraints();
        
        GenericRoundedButton undo = new GenericRoundedButton("Undo", 100, 40);
        GenericRoundedButton redo = new GenericRoundedButton("Redo", 100, 40);

        undo.addActionListener(new ButtonUndoRedoAdaptator(undo, this));
        redo.addActionListener(new ButtonUndoRedoAdaptator(redo, this));
        
        lc.gridy = 0;

        lc.gridx = 0;
        add(undo);
        
        lc.gridx = 1;
        add(redo);
    }
	public GameController getGameController() {
		return gameController;
	}
}
