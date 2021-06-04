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
    GenericRoundedButton pause;

    public RightSideGame(WindowConfig config, Dimension d, GameController gameController) {
        super(d);
        this.gameController = gameController;
        
        GenericRoundedButton saveToNewFile = new GenericRoundedButton("Sauvegarder la partie", 200, 40);
        pause = new GenericRoundedButton("Pause", 200, 40);
        GenericRoundedButton restart = new GenericRoundedButton("Recommencer la partie", 200, 40);
        GenericRoundedButton shortcuts = new GenericRoundedButton("Raccourcis", 200, 40);
        GenericRoundedButton quit = new GenericRoundedButton("Quitter la partie", 200, 40);
        
        saveToNewFile.setStyle("button.greenHover");
        quit.setStyle("button.redHover");

        shortcuts.setHref(WindowName.HelpWindow);
        quit.setHref(WindowName.HomeWindow);
        
        saveToNewFile.setAction(new ButtonSaveAdaptator(saveToNewFile, this));
        
        pause.setAction(new ButtonPauseAdaptator(pause, this));
        restart.setAction(new ButtonRestartAdaptator(pause, gameController));
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        c.insets = new Insets(10, 100, 10, 0);
        c.gridx = 0;
        
        c.gridy = 0; add(saveToNewFile, c);
        c.gridy = 1; add(pause, c);
        c.gridy = 2; add(restart, c);
        c.gridy = 3; add(shortcuts, c);
        c.gridy = 4; add(quit, c);
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public GenericRoundedButton getPauseButton() {
        return this.pause;
    }

    public void togglePauseButton(boolean isPaused) {
        pause.updateText(isPaused? "Reprendre" : "Pause");
    }

    public void enablePauseButton(boolean enable) {
        pause.setStyle("button" + (enable? "" : ":disabled"));
    }
}
