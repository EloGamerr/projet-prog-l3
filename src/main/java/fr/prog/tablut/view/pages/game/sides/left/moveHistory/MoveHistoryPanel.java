package fr.prog.tablut.view.pages.game.sides.left.moveHistory;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.model.game.Game;

public class MoveHistoryPanel extends JPanel {
    private final HistoryChatField historyChat;
    private final JScrollPane scrollPane;
    private int previousHeight = -1;
    private int previousVisibleAmount;

    public MoveHistoryPanel() {
        this(0, 0, null);
    }

    public MoveHistoryPanel(int width, int height, Game game) {
        setOpaque(false);

        JLabel wrapper = new JLabel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BorderLayout());

        historyChat = new HistoryChatField(game, new Dimension(width, height));
        //historyChat.setOpaque(false);
        historyChat.setFont(new Font("Calibri", Font.PLAIN, 12));

        setBorder(new EmptyBorder(20, 0, 0, 0));
        
        scrollPane = new JScrollPane(historyChat);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

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

        /* for(int i = 0 ; i < 5 ; i++) {
            append((i % 2 == 0 ? "L'attaquant " : "Le d\u00e9fenseur ") + i + " ", i % 2 == 0 ? new Color(193, 69, 69) : new Color(87, 158, 189), "a boug\u00e9 le pion en E5 vers A1");
        }
        append("Le test", Color.BLUE, "Est en test"); */
    }

    /* protected void append(String player, Color playerColor, String text) {
        historyChat.append(player, playerColor, text);

        JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();
        if(jScrollBar.getValue() + jScrollBar.getVisibleAmount() == jScrollBar.getMaximum()) {
            previousVisibleAmount = 0; // Push scrollBar to the bottom
            revalidate();
            repaint();
        }
    } */

    @Override
    protected void paintComponent(Graphics g) {
        /* int newHeight = historyChat.getLinesNumber() * 14 + 50;//getHeight() - 30;//getParent().getSize().height - 26;

        if(previousHeight != newHeight) {
            scrollPane.setPreferredSize(new Dimension(getWidth(), newHeight));
            scrollPane.setSize(new Dimension(getWidth(), newHeight));
            revalidate(); // Update layout because scrollPane changed
            previousHeight = newHeight;
        } */

        JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();
        // If visible amount of the scrollbar changed, so we have to change value to avoid some problems with scrollbar.
        // We set the scrollbar at the bottom because we want to see the more recent last events.
        if(previousVisibleAmount != jScrollBar.getVisibleAmount()) {
            jScrollBar.setValue(jScrollBar.getMaximum());
            previousVisibleAmount = jScrollBar.getVisibleAmount();
        }

        super.paintComponent(g);
    }
}
