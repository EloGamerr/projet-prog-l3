package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.components.generic.GenericInput;

public class InputAdaptator implements ActionListener {
    GenericInput input;

    public InputAdaptator(GenericInput input) {
        this.input = input;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
		
    }
}
