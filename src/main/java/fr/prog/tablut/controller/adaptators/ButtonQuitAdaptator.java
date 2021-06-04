package fr.prog.tablut.controller.adaptators;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;

import java.awt.event.ActionEvent;

public class ButtonQuitAdaptator extends ActionAdaptator<GenericButton> {

    public ButtonQuitAdaptator(GenericButton button) {
        super(button);
    }

    @Override
    public void process(ActionEvent e) {
        Game.resetInstance();
    }
}