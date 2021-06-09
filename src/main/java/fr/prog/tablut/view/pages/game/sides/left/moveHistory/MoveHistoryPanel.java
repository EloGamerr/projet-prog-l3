package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.border.EmptyBorder;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.components.generic.GenericScrollPane;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;


import fr.prog.tablut.view.pages.game.GamePage;

/**
 * The move history chat's component.
 * @see GenericPanel
 */
public class MoveHistoryPanel extends GenericPanel {
    private final HistoryChatField historyChat;
    private final JScrollPane scrollPane;
    private int previousHeight = -1;
    private int previousVisibleAmount;

    /**
     * Creates an empty move history panel with given size and central game page manager
     * @param d The move history chat's dimension
     * @param gamePage The game page, to communicate with other components
     */
    public MoveHistoryPanel(Dimension d, GamePage gamePage) {
        super(d);
        setBorder(new EmptyBorder(20, 0, 0, 0));

        GenericPanel wrapper = new GenericPanel(new BorderLayout(), d);
        wrapper.setBorder(new EmptyBorder(0, 0, 40, 0));

        historyChat = new HistoryChatField(gamePage, d);
        historyChat.setFont(new Font("Calibri", Font.PLAIN, 12));
        
        scrollPane = new GenericScrollPane(historyChat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        wrapper.add(scrollPane, BorderLayout.SOUTH);
        add(wrapper);
    }

    /**
     * Updates the chat's size, depending of its content, to resize the scrollPane
     */
    @Override
    protected void paintComponent(Graphics g) {
        int newHeight = Math.min(historyChat.getMovesNumber() * historyChat.getLabelHeight(), getHeight() - 50);

        if(previousHeight != newHeight) {
            Dimension d = new Dimension(getWidth(), newHeight);
            scrollPane.setPreferredSize(d);
            scrollPane.setSize(d);
            revalidate();
            previousHeight = newHeight;
        }

        JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();

        if(previousVisibleAmount != jScrollBar.getMaximum())
            jScrollBar.setValue((previousVisibleAmount = jScrollBar.getMaximum()));

        super.paintComponent(g);
    }

    /**
     * Add a new action in the chat. It will search the last action stored in the game.
     * <p>Refreshes the view</p>
     */
    public void addAction() {
        historyChat.addAction();
		revalidate();
		repaint();
    }

    public void addAction(int moveIndex) {
        if(Game.getInstance().getMovementsNumber() > moveIndex) {
            historyChat.addAction(moveIndex);
            revalidate();
            repaint();
        }
    }

    /**
     * Removes an action in the chat
     */
    public void undo() {
        historyChat.undo();
    }

    /**
     * Add a previously removed action in the chat
     */
    public void redo() {
        historyChat.redo();
    }

    /**
     * Clears the chat and resize its container
     */
    public void clearChat() {
        scrollPane.setSize(new Dimension(getWidth(), historyChat.getLabelHeight() * 2));
        historyChat.clear();
    }

    /**
     * Returns the size of the list of moves of the history chat
     * @return The size of the list of moves of the history chat
     */
    public int historyLength() {
        return historyChat.getHistoryLength();
    }

    /**
     * Removes an action in the chat history
     */
    public void removeAction(int i) {
        historyChat.removeAction(i);
    }

    /**
     * Sets the cursor's type when hovering the component
     */
    public void setCursorType(String cursorType) {
        historyChat.setCursorType(cursorType);
    }

    /**
     * Returns the number of moves available in the chat history
     * @return The number of moves abailable in the chat history
     */
    public int getMovesNumber() {
        return historyChat.getMovesNumber();
    }

    /**
     * Defines either a action's label at position i must be hovered's style or not
     * @param i The action's position
     * @param hover Either the action's label is hovered or not
     */
    public void setHoveringAction(int i, boolean hover) {
        historyChat.setHoveringAction(i, hover);
    }

    public void syncChat() {
        if(Game.getInstance() != null) {
            clearChat();
            int n = Game.getInstance().getMovementsNumber();

            for(int i=0; i < n; i++) {
                addAction(i);
            }
        }
    }
}
