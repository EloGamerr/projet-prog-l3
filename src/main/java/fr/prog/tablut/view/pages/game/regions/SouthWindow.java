package fr.prog.tablut.view.pages.game.regions;

import java.awt.*;

import javax.swing.*;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.pages.game.GamePage;

public class SouthWindow extends JPanel {
    private final GenericLabel jLabel;
    private final Game game;
    private final GamePage gamePage;

    public SouthWindow(Game game, GamePage gamePage) {
        this.game = game;
        this.gamePage = gamePage;

        jLabel = new GenericLabel("Au tour de l'attaquant", 15);
        add(jLabel);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;

        String text = "Au tour " + (game.getPlayingPlayerEnum() == PlayerEnum.ATTACKER ? "de l'attaquant" : "du d\u00e9fenseur");
        jLabel.setText(text);
    }
}
