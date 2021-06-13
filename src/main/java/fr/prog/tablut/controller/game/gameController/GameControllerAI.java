package fr.prog.tablut.controller.game.gameController;

import java.util.Objects;

import javax.swing.SwingUtilities;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.ai.AIPlayer;
import fr.prog.tablut.view.pages.game.GamePage;

public class GameControllerAI {
    private final GamePage gamePage;
    private CooldownMove cdMove = null;
    private int cdMoveDuration = 0;
    private boolean pending = false;
    private long timestamp_beforeGetMove = 0;
    private long timestamp_afterGetMove = 0;

    public GameControllerAI(int speed, GamePage gamePage) {
        this.gamePage = gamePage;
        this.cdMoveDuration = speed;
    }

    /**
     * Calculer le coup avant la fin du timer
	 * @return True if The AI played or not
	 */
    public boolean tick() {
        if(myTurn() && !pending) {
            if(cdMove == null) {
                play(getPlayingAI(), Game.getInstance());
                return true;
            }
            else if(cdMove.tick()) {
                return true;
            }
        }

        return false;
    }

    public boolean tickAnimation() {
        if(pending || cdMove == null) {
            return false;
        }

        if(!cdMove.getAI().checkAnim(cdMove.getGame())) {
            cdMove = null;
            return false;
        }
        
        return true;
    }

    /**
     * Returns either it's to an AI to play or not, and if it's possible
     * @return
     */
    private boolean myTurn() {
        Game game = Game.getInstance();
        return !game.isWon() && !game.getPlayingPlayer().isHuman() && !game.isPaused();
    }

    /**
     * Returns the current playing AI
     * @return
     */
    private AIPlayer getPlayingAI() {
        if(Game.getInstance().getPlayingPlayer().isHuman())
            return null;

        return ((AIPlayer)Game.getInstance().getPlayingPlayer());
    }

    /**
     * Plays the AI on the current given game
     * @param ai The AI which has to play
     * @param game The game the AI has to play in
     */
    private void play(AIPlayer ai, Game game) {
        pending = true;
        timestamp_beforeGetMove = System.currentTimeMillis();

        // search for the best move in a new thread so the interface isn't freezed
        new Thread(() -> {
            Movement movement = ai.play(game);

            if(movement != null && game.move(movement.getFromC(), movement.getFromL(), movement.getToC(), movement.getToL())) {
                SwingUtilities.invokeLater(() -> {
                    pending = false;
                    timestamp_afterGetMove = System.currentTimeMillis();
                    int diff = (int)(timestamp_afterGetMove - timestamp_beforeGetMove);
                    cdMove = new CooldownMove(ai, game, movement, Math.max(0, cdMoveDuration - diff), gamePage);
                });
            }
            else {
                ai.unlockThread();
                cdMove = null;
            }
        }).start();
    }

    public boolean isAnimationPending() {
        return pending;
    }

    public boolean isAnimationProcessing() {
        return cdMove != null && cdMove.hasRun();
    }
}

class CooldownMove {
    private AIPlayer ai;
    private Game game;
    private Movement move;
    private GamePage gamePage;

    private int time = 0;
    private boolean hasRun = false;

    public CooldownMove(AIPlayer ai, Game game, Movement move, int duration, GamePage gamePage) {
        this.ai = ai;
        this.game = game;
        this.move = move;
        this.time = duration;
        this.gamePage = gamePage;
    }

    public boolean tick() {
        if(ai == null || game == null || move == null || gamePage == null)
            return false;


        if(time-- <= 0 && !hasRun) {
            hasRun = true;
            run(ai, game, move);
            return true;
        }

        return false;
    }

    public AIPlayer getAI() {
        return ai;
    }

    public Game getGame() {
        return game;
    }

    public Movement getMove() {
        return move;
    }

    public boolean hasRun() {
        return hasRun;
    }

    private void run(AIPlayer ai, Game game, Movement move) {
        if(ai.unlockThread())
            ai.createAnimation(Objects.requireNonNull(move).getFrom(), move.getTo(), gamePage);
    }
}