package fr.prog.tablut.view.game;

import fr.prog.tablut.view.util.JTextChat;

import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JPanel {

    private final JTextChat jTextChat;
    private final JScrollPane scrollPane;

    public ChatWindow() {
        jTextChat = new JTextChat();
        jTextChat.setEditable(false);
        jTextChat.setFont(new Font("Calibri", Font.PLAIN, 12));
        jTextChat.setBackground(new Color(26, 29, 33));

        this.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel("Derniers coups");
        this.add(jLabel, BorderLayout.NORTH);

        scrollPane = new JScrollPane(jTextChat);
        this.add(scrollPane, BorderLayout.SOUTH);

        for(int i = 0 ; i <= 100 ; i++) {
            this.addTextArea((i % 2 == 0 ? "L'attaquant " : "Le d\u00e9fenseur ") + i + " ", i % 2 == 0 ? new Color(193, 69, 69) : new Color(87, 158, 189), "a boug\u00e9 le pion en E5 vers A1");
        }
    }

    private void addTextArea(String player, Color playerColor, String text) {
        if(!jTextChat.getText().isEmpty())
            jTextChat.insertLine();

        jTextChat.insertTextEnd("[03:24] ", false, false, false, Color.WHITE);
        jTextChat.insertTextEnd(player, false, false, false, playerColor);
        jTextChat.insertTextEnd(text, false, false, false, new Color(189, 116, 58));

        JScrollBar jScrollBar = scrollPane.getVerticalScrollBar();
        if(jScrollBar.getValue() + jScrollBar.getVisibleAmount() == jScrollBar.getMaximum()) {
            previousVisibleAmount = 0; // Push scrollBar to the bottom
            revalidate();
            repaint();
        }
    }

    private int previousHeight;
    private int previousVisibleAmount;
    @Override
    protected void paintComponent(Graphics g) {
        int newHeight = this.getParent().getSize().height - 26;
        if(previousHeight != newHeight) {
            scrollPane.setPreferredSize(new Dimension(250, newHeight));
            scrollPane.setSize(new Dimension(250, newHeight));
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
}
