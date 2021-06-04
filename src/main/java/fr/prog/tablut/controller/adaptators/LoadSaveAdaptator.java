package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;

public class LoadSaveAdaptator extends ActionAdaptator<GenericButton> {
    private SavedGamesPanel savesPanel;

    public LoadSaveAdaptator(GenericButton button, SavedGamesPanel savesPanel) {
        super(button);
        this.savesPanel = savesPanel;
    }

    @Override
    protected void process(ActionEvent e) {
        // créé une instance
        Game.getInstance().load(savesPanel.getSelectedIndex());
    }
}
