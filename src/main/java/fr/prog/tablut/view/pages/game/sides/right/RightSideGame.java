package fr.prog.tablut.view.pages.game.sides.right;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import fr.prog.tablut.controller.adaptators.ButtonPauseAdaptator;
import fr.prog.tablut.controller.adaptators.ButtonQuitGameAdaptator;
import fr.prog.tablut.controller.adaptators.ButtonRestartAdaptator;
import fr.prog.tablut.controller.adaptators.ButtonSaveAdaptator;
import fr.prog.tablut.controller.game.gameController.GameController;
import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.PageName;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;
import fr.prog.tablut.view.components.generic.GenericRoundedButton;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;

/**
 * Game's right side component that manages all buttons menu
 */
public class RightSideGame extends GameInterfaceSide {
	GameController gameController;
    GenericRoundedButton pause;

    /**
     * Creates the right side of the game's page
     * @param config The config to apply
     * @param d The dimension of the side
     * @param gameController The game's controller
     */
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

        shortcuts.setHref(PageName.HelpPage);
        quit.setHref(PageName.HomePage, new ButtonQuitGameAdaptator(quit, GenericObjectStyle.getGlobalWindow()));

        saveToNewFile.setAction(new ButtonSaveAdaptator(saveToNewFile, this));
        
        pause.setAction(new ButtonPauseAdaptator(pause, this));
        restart.setAction(new ButtonRestartAdaptator(restart, gameController, false, null));
        
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

    /**
     * Toggles the pause button text
     * @param isPaused The pause button's state
     */
    public void togglePauseButton(boolean isPaused) {
        pause.updateText(isPaused? "Reprendre" : "Pause");
    }

    /**
     * Toggles the pause button's style (enable or disable it)
     * @param enable The pause button's state
     */
    public void enablePauseButton(boolean enable) {
        pause.setStyle("button" + (enable? "" : ":disabled"));
    }
}
