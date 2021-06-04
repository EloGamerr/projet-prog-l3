package fr.prog.tablut.view.components.generic;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * A component extending a JPanel, to create a rounded JPanel
 * @see JPanel
 */
public class GenericPanel extends JPanel {
    public GenericPanel() {
        this(new FlowLayout(), null);
    }

    public GenericPanel(LayoutManager layout) {
        this(layout, null);
    }

    public GenericPanel(LayoutManager layout, Dimension d) {
        super();
        setOpaque(false);

        if(layout != null)
            setLayout(layout);

        if(d != null) {
            setSize(d);
            setPreferredSize(d);
            setMaximumSize(d);
            setMinimumSize(d);
        }
    }
}
