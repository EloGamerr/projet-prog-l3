package fr.prog.tablut.view.util;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

//Source : https://www.developpez.net/forums/d158052/java/interfaces-graphiques-java/awt-swing/swing-chat-jtextpane-jeditorpane/
public class JTextChat extends JTextPane {

    private final StyledDocument styledDocument = getStyledDocument();
    protected float COMPONENT_ALIGNEMENT = 0.82f;

    public JTextChat() {
        super();
    }

    private static String getNameStyle(boolean bold, boolean italic, boolean underline, Color color) {

        StringBuilder sb = new StringBuilder();

        if(bold) 		sb.append("1");
        else 			sb.append("0");
        if(italic) 		sb.append("1");
        else 			sb.append("0");
        if(underline) 	sb.append("1");
        else 			sb.append("0");
        sb.append(color.getRGB());

        return sb.toString();
    }

    private Style getStyle(boolean bold, boolean italic, boolean underline, Color color) {

        // Récupère le nom du style
        String styleName = getNameStyle(bold, italic, underline, color);

        // On récupère le style (Si il n'existe pas on récupère null)
        Style style = styledDocument.getStyle(styleName);

        // Si le style existe on le retourne
        if(style != null) return style;

            // Si le style n'existe pas on le créer
        else {

            // Création du nouveau style
            Style styleDefaut 	= styledDocument.getStyle(StyleContext.DEFAULT_STYLE);
            style 				= styledDocument.addStyle(styleName, styleDefaut);

            StyleConstants.setBold(style, bold);
            StyleConstants.setItalic(style, italic);
            StyleConstants.setUnderline(style, underline);
            StyleConstants.setForeground(style, color);


            return style;
        }
    }

    public synchronized void insertTextEnd(String texte, boolean bold, boolean italic, boolean underline, Color color) {
        try { styledDocument.insertString(styledDocument.getLength(), texte, getStyle(bold, italic, underline, color)); } catch(BadLocationException ignored) {}
    }

    public synchronized void insertLine() {
        try { styledDocument.insertString(styledDocument.getLength(), "\n", styledDocument.getStyle("default")); } catch(BadLocationException ignored) {}
    }

    public synchronized void insertIconSelect(Icon icon) {
        insertIcon(icon);
    }

    public synchronized void insertIconEnd(Icon icon) {
        select(styledDocument.getLength(), styledDocument.getLength());
        insertIconSelect(icon);
    }

    public synchronized void insertComponentSelect(JComponent c) {
        c.setAlignmentY(COMPONENT_ALIGNEMENT);
        insertComponent(c);
    }

    public synchronized void insertComponentEnd(JComponent c) {
        select(styledDocument.getLength(), styledDocument.getLength());
        insertComponentSelect(c);
    }
}