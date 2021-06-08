package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericPanel;
import fr.prog.tablut.view.components.generic.GenericScrollPane;

import javax.swing.JScrollPane;
import javax.swing.JScrollBar;


import fr.prog.tablut.view.pages.game.GamePage;

public class MoveHistoryPanel extends GenericPanel {
    private final HistoryChatField historyChat;
    private final JScrollPane scrollPane;
    private int previousHeight = -1;
    private int previousVisibleAmount;

    public MoveHistoryPanel() {
        this(0, 0, null);
    }

    public MoveHistoryPanel(int width, int height, GamePage gamePage) {
        super();
        setBorder(new EmptyBorder(20, 0, 0, 0));

        GenericPanel wrapper = new GenericPanel(new BorderLayout());
        wrapper.setBorder(new EmptyBorder(0, 0, 40, 0));

        historyChat = new HistoryChatField(gamePage);
        historyChat.setFont(new Font("Calibri", Font.PLAIN, 12));
        
        scrollPane = new GenericScrollPane(historyChat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        wrapper.add(scrollPane, BorderLayout.SOUTH);
        add(wrapper);

        if(width > 0 && height > 0) {
            final Dimension d = new Dimension(width, height);
            setSize(d);
            setPreferredSize(d);
            setMaximumSize(d);
            setMinimumSize(d);

            wrapper.setSize(d);
            wrapper.setPreferredSize(d);
            wrapper.setMaximumSize(d);
            wrapper.setMinimumSize(d);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int newHeight = historyChat.getMovesNumber() * historyChat.getLabelHeight();

        if(previousHeight != newHeight) {
            Dimension d = new Dimension(getWidth(), newHeight);
            scrollPane.setPreferredSize(d);
            scrollPane.setSize(d);
            revalidate(); // Update layout because scrollPane changed
            previousHeight = newHeight;
        }

        JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();
        // If visible amount of the scrollbar changed, so we have to change value to avoid some problems with scrollbar.
        // We set the scrollbar at the bottom because we want to see the more recent last events.
        if(previousVisibleAmount != jScrollBar.getVisibleAmount()) {
            jScrollBar.setValue(jScrollBar.getMaximum());
            previousVisibleAmount = jScrollBar.getVisibleAmount();
        }

        super.paintComponent(g);
    }

    public void addAction() {
        historyChat.addAction();

        JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();
        if(jScrollBar.getValue() + jScrollBar.getVisibleAmount() == jScrollBar.getMaximum()) {
            previousVisibleAmount = 0; // Push scrollBar to the bottom
        }
        
		revalidate();
		repaint();
    }

    public void undo() {
        historyChat.undo();
    }

    public void redo() {
        historyChat.redo();
    }

    public void clearChat() {
        scrollPane.setSize(new Dimension(getWidth(), historyChat.getLabelHeight() * 2));
        historyChat.clear();
    }
}
