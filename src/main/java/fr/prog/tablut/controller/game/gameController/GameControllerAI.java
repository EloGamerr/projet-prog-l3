package fr.prog.tablut.controller.game.gameController;

import fr.prog.tablut.controller.game.ia.AIPlayer;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.GamePage;

public class GameControllerAI {
    private int timer;
    private final int speed;
    private GamePage gamePage;

    public GameControllerAI(int speed, GamePage gamePage) {
        this.speed = speed;
        timer = speed;
        this.gamePage = gamePage;
    }

    /**
	 * @return True if we should repaint the window after the tick
	 */
    //Calculer le coup avant la fin du timer
    public boolean tick() {
        if(!Game.getInstance().isWon() && Game.getInstance().getPlayingPlayer() instanceof AIPlayer && !Game.getInstance().isPaused()) {
            if(timer-- <= 0) {
                boolean shouldRepaint = Game.getInstance().getPlayingPlayer().play(Game.getInstance(),gamePage);
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
