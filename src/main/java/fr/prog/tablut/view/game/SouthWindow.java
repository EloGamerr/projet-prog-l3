package fr.prog.tablut.view.game;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.model.PlayerEnum;
import fr.prog.tablut.view.generic.GenericLabel;

import javax.swing.*;
import java.awt.*;

public class SouthWindow extends JPanel {
    private final GenericLabel jLabel;
    private final Game game;
    private final GameWindow gameWindow;

    public SouthWindow(Game game, GameWindow gameWindow) {
        this.game = game;
        this.gameWindow = gameWindow;

        jLabel = new GenericLabel("Au tour de l'attaquant", 15);
        this.add(jLabel);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;

        String text = "Au tour " + (this.game.getPlayer() == PlayerEnum.ATTACKER ? "de l'attaquant" : "du d\u00e9fenseur");
        jLabel.setText(text);
    }
}
