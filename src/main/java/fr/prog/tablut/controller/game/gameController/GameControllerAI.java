package fr.prog.tablut.controller.game.gameController;

import fr.prog.tablut.controller.game.ia.AIPlayer;
import fr.prog.tablut.model.game.Game;

public class GameControllerAI {
    private int timer;
    private final int speed;

    public GameControllerAI(int speed) {
        this.speed = speed;
        timer = speed;
    }

    /**
	 * @return True if we should repaint the window after the tick
	 */
    //Calculer le coup avant la fin du timer
    @Deprecated
    public boolean tick() {
    	// TODO
        if(Game.getInstance().getPlayingPlayer() instanceof AIPlayer && !Game.getInstance().isPaused()) {
            if(timer-- <= 0) {
                boolean shouldRepaint = Game.getInstance().getPlayingPlayer().play(Game.getInstance());
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
