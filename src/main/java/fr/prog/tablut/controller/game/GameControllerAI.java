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

    /**
	 * @return True if we should repaint the window after the tick
	 */

    @Deprecated
    //Calculer le coup avant la fin du timer
    public boolean tick() {
    	// TODO
        if(game.getPlayingPlayer() instanceof AIPlayer) {
            if(timer-- <= 0) {
                boolean shouldRepaint = game.getPlayingPlayer().play(game);
                timer = speed;
                return shouldRepaint;
            }
        }
        else {
            timer = speed;
        }

        return false;
    }
}
