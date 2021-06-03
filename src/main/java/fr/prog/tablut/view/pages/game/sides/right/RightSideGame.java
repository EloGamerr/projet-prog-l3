package fr.prog.tablut.view.pages.game.sides.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.controller.adaptators.ButtonSaveAdaptator;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

public class RightSideGame extends GameInterfaceSide {
    public RightSideGame(WindowConfig config, Game game, Dimension d) {
        super(d);

        GenericRoundedButton save = new GenericRoundedButton("Sauvegarder", 200, 40);
        GenericRoundedButton pause = new GenericRoundedButton("Pause", 200, 40);
        GenericRoundedButton restart = new GenericRoundedButton("Recommencer la partie", 200, 40);
        GenericRoundedButton shortcuts = new GenericRoundedButton("Raccourcis", 200, 40);
        GenericRoundedButton quit = new GenericRoundedButton("Quitter la partie", 200, 40);

        save.setStyle("button.greenHover");
        quit.setStyle("button.redHover");

        shortcuts.setHref(WindowName.HelpWindow);
        quit.setHref(WindowName.HomeWindow);

        save.addMouseListener(new ButtonSaveAdaptator(game, this));



        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        c.insets = new Insets(10, 100, 10, 0);
        c.gridx = 0;
        
        c.gridy = 0; add(save, c);
        c.gridy = 1; add(pause, c);
        c.gridy = 2; add(restart, c);
        c.gridy = 3; add(shortcuts, c);
        c.gridy = 4; add(quit, c);
    }
}
