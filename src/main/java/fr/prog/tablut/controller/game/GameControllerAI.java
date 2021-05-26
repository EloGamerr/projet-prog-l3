package fr.prog.tablut.controller.game;

import fr.prog.tablut.model.Game;

public class GameControllerAI {
    private final Game game;
    private int timer;
    private final int speed;

    public GameControllerAI(Game game, int speed) {
        this.game = game;
        this.speed = speed;
        timer = speed;
    }

    public void tick() {
        if(game.getPlayingPlayer() instanceof AIPlayer) {
            if(timer-- <= 0) {
                game.getPlayingPlayer().play(game);
                timer = speed;
            }
        }
        else {
            timer = speed;
        }
    }
}
