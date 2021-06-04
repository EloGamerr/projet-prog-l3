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

        int btnWidth = 50;
        int btnHeight = 40;
        int imgSize = 50 * btnHeight / 100; // 50%, only modify the ratio
        int imgX = btnWidth / 2 - imgSize / 2;
        int imgY = btnHeight / 2 - imgSize / 2;
        
        GenericRoundedButton undo = new GenericRoundedButton("", btnWidth, btnHeight);
        GenericRoundedButton redo = new GenericRoundedButton("", btnWidth, btnHeight);

        undo.setImage("left_arrow.png", imgX, imgY, imgSize, imgSize);
        redo.setImage("right_arrow.png", imgX, imgY, imgSize, imgSize);

        undo.setAction(new ButtonUndoRedoAdaptator(undo, this));
        redo.setAction(new ButtonUndoRedoAdaptator(redo, this));

        // disabled in early game : no move done
        undo.setStyle("button:disabled");
        redo.setStyle("button:disabled");
        
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
