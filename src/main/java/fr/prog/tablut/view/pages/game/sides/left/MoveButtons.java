package fr.prog.tablut.view.pages.game.sides.left;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import fr.prog.tablut.controller.game.gameAdaptator.ButtonUndoRedoAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;

/**
 * The In-game button's menu
 */
public class MoveButtons extends GenericPanel {
    private final GenericRoundedButton undo;
    private final GenericRoundedButton redo;

    /**
     * Creates the ing-game button's menu
     * @param d The dimension of the menu
     * @param gameController The game's controller
     */
    public MoveButtons(Dimension d, GameController gameController) {
        super(new GridBagLayout());

        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);

        // button's dimension
        int btnWidth = 50;
        int btnHeight = 40;
        int imgSize = 50 * btnHeight / 100; // 50%, only modify the ratio
        int imgX = btnWidth / 2 - imgSize / 2;
        int imgY = btnHeight / 2 - imgSize / 2;
        
        // buttons undo/redo
        undo = new GenericRoundedButton("", btnWidth, btnHeight);
        redo = new GenericRoundedButton("", btnWidth, btnHeight);

        undo.setName("undo-button");
        redo.setName("redo-button");

        undo.setAction(new ButtonUndoRedoAdaptator(undo, gameController));
        redo.setAction(new ButtonUndoRedoAdaptator(redo, gameController));


        // button's style
        undo.setImage("theme/left_arrow.png", imgX, imgY, imgSize, imgSize);
        redo.setImage("theme/right_arrow.png", imgX, imgY, imgSize, imgSize);

        // disabled in early game : no move done
        undo.setStyle("button:disabled");
        redo.setStyle("button:disabled");
        
        // add button's in the view
        GridBagConstraints lc = new GridBagConstraints();

        lc.gridy = 0;

        lc.gridx = 0;
        add(undo);
        
        lc.gridx = 1;
        add(redo);
    }

    /**
     * Enables or disables the undo button
     * @param enable The undo button's state
     */
    public void enableUndoButton(boolean enable) {
        enableButton(undo, enable);
    }

    /**
     * Enables or disables the redo button
     * @param enable The redo button's state
     */
    public void enableRedoButton(boolean enable) {
        enableButton(redo, enable);
    }

    /**
     * Enables or disables the given button (undo or redo)
     * @param button The button to enable/disable
     * @param enable The state to set to the button
     */
    private void enableButton(GenericRoundedButton button, boolean enable) {
        String style = "button" + (enable? "" : ":disabled");

        if(!button.getStyle().replace(":hover", "").equals(style))
            button.setStyle(style);
    }
    
}
