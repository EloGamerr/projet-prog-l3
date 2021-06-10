package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;
import fr.prog.tablut.view.window.GlobalWindow;

/**
* Adaptateur pour le panel de load
*/
public class LoadSaveAdaptator extends ActionAdaptator<GenericButton> {
    private final SavedGamesPanel savesPanel;
    private final GlobalWindow globalWindow;

    public LoadSaveAdaptator(GenericButton button, SavedGamesPanel savesPanel, GlobalWindow globalWindow) {
        super(button);
        this.savesPanel = savesPanel;
        this.globalWindow = globalWindow;
    }

    @Override
    protected void process(ActionEvent e) {
        // On charge la save séléctionée par l'utilisateur
        if(Game.getInstance().load(savesPanel.getSelectedIndex())) {
            globalWindow.changeWindow(entity.getHref());
        }
    }
}
