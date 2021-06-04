package fr.prog.tablut.view.pages.game.sides.left;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import fr.prog.tablut.controller.adaptators.ButtonUndoRedoAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.pages.game.sides.right.RightSideGame;

public class MoveButtons extends JLabel {
	private GameController gameController;
    private GenericRoundedButton undo, redo;

    public MoveButtons(Dimension d, GameController gameController, RightSideGame rightSide) {
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
        
        undo = new GenericRoundedButton("", btnWidth, btnHeight);
        redo = new GenericRoundedButton("", btnWidth, btnHeight);

        undo.setImage("left_arrow.png", imgX, imgY, imgSize, imgSize);
        redo.setImage("right_arrow.png", imgX, imgY, imgSize, imgSize);

        undo.setAction(new ButtonUndoRedoAdaptator(undo, this, rightSide));
        redo.setAction(new ButtonUndoRedoAdaptator(redo, this, rightSide));

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

    public void enableUndoButton(boolean enable) {
        enableButton(undo, enable);
    }

    public void enableRedoButton(boolean enable) {
        enableButton(redo, enable);
    }

    private void enableButton(GenericRoundedButton button, boolean enable) {
        String style = "button" + (enable? "" : ":disabled");

        if(button.getStyle().replace(":hover", "") != style)
            button.setStyle(style);
    }
}
