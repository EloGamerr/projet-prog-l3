package fr.prog.tablut.view.pages.game.sides.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.controller.adaptators.ButtonPauseAdaptator;
import fr.prog.tablut.controller.adaptators.ButtonRestartAdaptator;
import fr.prog.tablut.controller.adaptators.ButtonSaveAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

public class RightSideGame extends GameInterfaceSide {
	GameController gameController;
    public RightSideGame(WindowConfig config, Dimension d, GameController gameController) {
        super(d);
        this.gameController = gameController;
        
        GenericRoundedButton saveToNewFile = new GenericRoundedButton("Nouvelle sauvegarde", 200, 40);
        GenericRoundedButton saveToFile = new GenericRoundedButton("Ecraser la sauvegarde", 200, 40);
        GenericRoundedButton pause = new GenericRoundedButton("Pause", 200, 40);
        GenericRoundedButton restart = new GenericRoundedButton("Recommencer la partie", 200, 40);
        GenericRoundedButton shortcuts = new GenericRoundedButton("Raccourcis", 200, 40);
        GenericRoundedButton quit = new GenericRoundedButton("Quitter la partie", 200, 40);
        
        saveToNewFile.setStyle("button.greenHover");
        saveToFile.setStyle("button.greenHover");
        quit.setStyle("button.redHover");

        shortcuts.setHref(WindowName.HelpWindow);
        quit.setHref(WindowName.HomeWindow);
        
        saveToFile.addActionListener(new ButtonSaveAdaptator(saveToFile, this));
        saveToNewFile.addActionListener(new ButtonSaveAdaptator(saveToNewFile, this));
        
        pause.addActionListener(new ButtonPauseAdaptator(pause, this));
        restart.addActionListener(new ButtonRestartAdaptator(pause, gameController));
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        c.insets = new Insets(10, 100, 10, 0);
        c.gridx = 0;
        
        c.gridy = 0; add(saveToNewFile, c);
        c.gridy = 1; add(saveToFile, c);
        c.gridy = 2; add(pause, c);
        c.gridy = 3; add(restart, c);
        c.gridy = 4; add(shortcuts, c);
        c.gridy = 5; add(quit, c);
    }
}
