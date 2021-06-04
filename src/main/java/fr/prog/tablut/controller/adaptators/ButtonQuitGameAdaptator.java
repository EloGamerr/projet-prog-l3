package fr.prog.tablut.controller.adaptators;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericButton;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class ButtonQuitGameAdaptator extends ActionAdaptator<GenericButton> {
    private final GlobalWindow globalWindow;

    public ButtonQuitGameAdaptator(GenericButton button, GlobalWindow globalWindow) {
        super(button);
        this.globalWindow = globalWindow;
    }

    @Override
    public void process(ActionEvent e) {
        Object value = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter la partie en cours ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if(value != null && (int) value == 0) {
            Game.resetInstance();
            globalWindow.getGamePage().getGridWindow().getGridView().setIsInAnim(false);
            globalWindow.changeWindow(entity.getHref());
        }
    }
}