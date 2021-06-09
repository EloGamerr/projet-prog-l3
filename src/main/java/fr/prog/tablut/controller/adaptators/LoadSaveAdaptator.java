package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.pages.load.SavedGamesPanel;

//Adaptateur pour le panel de load
public class LoadSaveAdaptator extends ActionAdaptator<GenericButton> {
    private SavedGamesPanel savesPanel;

    public LoadSaveAdaptator(GenericButton button, SavedGamesPanel savesPanel) {
        super(button);
        this.savesPanel = savesPanel;
    }

    @Override
    protected void process(ActionEvent e) {
        // On charge la save séléctionée par l'utilisateur
        Game.getInstance().load(savesPanel.getSelectedIndex());
    }
}
