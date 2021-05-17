package fr.prog.tablut.view.west;

import fr.prog.tablut.view.util.JTextChat;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class ChatWindow extends JPanel {

    private final WestWindow parent;

    private final Style defaultStyle;
    private final JTextChat jTextArea;
    private final JScrollPane scrollPane;

    public ChatWindow(WestWindow parent) {
        this.parent = parent;
        this.setBackground(new Color(200, 200, 200));
        jTextArea = new JTextChat();

        jTextArea.setEditable(false);
        jTextArea.setOpaque(false);
        jTextArea.setFont(new Font("Calibri", Font.PLAIN, 11));

        defaultStyle = jTextArea.getStyle("default");
        StyleConstants.setForeground(defaultStyle, Color.BLACK);

        for(int i = 0 ; i <= 100 ; i++) {
            this.addTextArea(i % 2 == 0 ? "L'attaquant " : "Le d\u00e9fenseur ", i % 2 == 0 ? Color.RED : Color.BLUE, "a boug\u00e9 le pion en E5 vers A1");
        }

        this.setLayout(new BorderLayout());

        JLabel jLabel = new JLabel("Derniers coups");
        this.add(jLabel, BorderLayout.NORTH);

        scrollPane = new JScrollPane(jTextArea);
        this.add(scrollPane, BorderLayout.SOUTH);
    }

    private void addTextArea(String player, Color playerColor, String text) {
        if(!jTextArea.getText().isEmpty())
            jTextArea.insertLine();

        jTextArea.insertTextEnd("[03:24] ", false, false, false, Color.BLACK);
        jTextArea.insertTextEnd(player, false, false, false, playerColor);
        jTextArea.insertTextEnd(text, false, false, false, Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        scrollPane.setPreferredSize(new Dimension(200, (this.parent.getSize().height - 26)));
        scrollPane.setSize(new Dimension(200, (this.parent.getSize().height - 26)));
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        super.paintComponent(g);
    }
}
