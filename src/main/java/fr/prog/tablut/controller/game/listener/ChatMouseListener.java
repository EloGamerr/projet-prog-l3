package fr.prog.tablut.controller.game.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.pages.game.sides.left.moveHistory.HistoryChatField;

public class ChatMouseListener extends MouseAdapter {
    private final JLabel chatAction;
    private final HistoryChatField historyChat;
    private final GamePage gamePage;

    public ChatMouseListener(JLabel chatAction, HistoryChatField historyChat, GamePage gamePage) {
        this.chatAction = chatAction;
        this.historyChat = historyChat;
        this.gamePage = gamePage;
    }

    /**
     * Triggered when mouse's over
     * Replace the current board's context
     */
    @Override
    public void mouseExited(MouseEvent e) {
        gamePage.exitPreviewAt(historyChat.getActionPosition(chatAction));
    }

    /**
     * Triggered when mouse's hovering the chat
     * Changes the board's context to hovered movement
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        gamePage.setPreviewAt(historyChat.getActionPosition(chatAction));
    }

    /**
     * Triggered when player selected an older move
     * Change board's context and confirm it
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        gamePage.confirmPreviewAt(historyChat.getActionPosition(chatAction));
    }
}
