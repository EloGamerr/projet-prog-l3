package fr.prog.tablut.view.pages.game.sides.left;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import fr.prog.tablut.view.components.generic.GenericRoundedButton;

public class MoveButtons extends JLabel {
    public MoveButtons(Dimension d) {
        setOpaque(false);

        setLayout(new GridBagLayout());
        setSize(d);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
        GridBagConstraints lc = new GridBagConstraints();
        
        GenericRoundedButton cancel = new GenericRoundedButton("Undo", 100, 40);
        GenericRoundedButton redo = new GenericRoundedButton("Redo", 100, 40);

        cancel.setStyle("button.menuIngame");
        redo.setStyle("button.menuIngame");

        lc.gridy = 0;

        lc.gridx = 0;
        add(cancel);
        
        lc.gridx = 1;
        add(redo);
    }
}
