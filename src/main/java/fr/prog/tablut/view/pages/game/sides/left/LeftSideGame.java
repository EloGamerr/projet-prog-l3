package fr.prog.tablut.view.pages.game.sides.left;

import java.awt.Dimension;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.pages.game.sides.GameInterfaceSide;
import fr.prog.tablut.view.pages.game.sides.left.moveHistory.MoveHistoryPanel;

/**
 * The left side panel of the game page.
 * <p>Regroups the move history chat and the undo/redo buttons</p>
 * @see GameInterfaceSide
 */
public class LeftSideGame extends GameInterfaceSide {
    private final MoveHistoryPanel historyPanel;
    private final MoveButtons buttons;

    /**
     * Creates the left side panel of the game
     * @see GamePage
     * @param d The dimension of the panel
     * @param gamePage The game page
     */
    public LeftSideGame(Dimension d, GamePage gamePage) {
        super(d);

        Dimension mbd = new Dimension(d.width, 40);

        buttons = new MoveButtons(mbd, gamePage.getGameController());
        historyPanel = new MoveHistoryPanel(new Dimension(d.width - 10, d.height - mbd.height * 2 - 25), gamePage);

        add(historyPanel);
        add(buttons);
    }

    /**
     * Returns the moveButtons panel (undo/redo)
     * @see MoveButtons
     * @return The moveButtons panel
     */
    public MoveButtons getMoveButtons() {
        return buttons;
    }

    /**
     * Returns the moveHistory panel
     * @see MoveHistoryPanel
     * @return The moveHistory panel
     */
    public MoveHistoryPanel getMoveHistoryPanel() {
        return historyPanel;
    }

    /**
     * Updates the left side panel.
     * <p>Clears the chat, and disables buttons.</p>
     */
    public void reset() {
        buttons.enableRedoButton(Game.getInstance().hasNextMove());
		buttons.enableUndoButton(Game.getInstance().hasPreviousMove());
        historyPanel.syncChat();
    }

    /**
     * Clears the move history chat
     * @see MoveHistoryPanel
     */
    public void clearChat() {
        historyPanel.clearChat();
    }
}