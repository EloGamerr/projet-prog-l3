package fr.prog.tablut.view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericLabel;
import fr.prog.tablut.view.components.generic.GenericObjectStyle;

public class NavPage extends JPanel {
    protected static Dimension maxDimension;

    public static void setDimension(Dimension d) {
        maxDimension = d;
    }

    public NavPage(String title, BottomButtonPannel bottomPannel) {
        this(title, null, bottomPannel);
    }

    public NavPage(BottomButtonPannel bottomPannel) {
        this(null, null, bottomPannel);
    }

    public NavPage(String title, String description, BottomButtonPannel bottomPannel) {
        setLayout(new BorderLayout());
        setOpaque(false);

        if(title != null) {
            Title Ttitle = new Title(title.toUpperCase(), 70);

            JPanel gb = new JPanel(new GridBagLayout());
            gb.setBorder(new EmptyBorder(50, 0, 0, 0));
            gb.setOpaque(false);

            Dimension gbSize = new Dimension((int)NavPage.maxDimension.getWidth(), 170);
            gb.setPreferredSize(gbSize);
            gb.setMaximumSize(gbSize);
            gb.setMinimumSize(gbSize);

            // title
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridy = 1;

            gb.add(Ttitle, gbc);

            // description
            if(description != null) {
                GenericLabel desc = new GenericLabel(description, 14);
                desc.setForeground(GenericObjectStyle.getProp("description", "color"));
                gbc.gridy = 2;
                gb.add(desc, gbc);
            }

            add(gb, BorderLayout.NORTH);
        }

        // bottom button pannel
		add(bottomPannel, BorderLayout.SOUTH);
    }

    public void setContent(JPanel content) {
        add(content, BorderLayout.CENTER);
    }
}
