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
    /**
     * Creates a generic panel with FlowLayout's manager and no dimension
     * @see FlowLayout
     */
    public GenericPanel() {
        this(new FlowLayout(), null);
    }

    /**
     * Creates a generic panel with given layout manager
     * @param layout The layout manager of the panel
     */
    public GenericPanel(LayoutManager layout) {
        this(layout, null);
    }

    /**
     * Creates a generic panel with given dimension, and FlowLayout manager
     * @see FlowLayout
     * @param d The dimension of the panel
     */
    public GenericPanel(Dimension d) {
        this(new FlowLayout(), d);
    }

    /**
     * Creates a generic panel with given layout manager and given dimension
     * @see FlowLayout
     * @param layout The layout manager of the panel
     * @param d The dimension of the panel
     */
    public GenericPanel(LayoutManager layout, Dimension d) {
        super();
        setOpaque(false);
        setLayout(layout);

        if(d != null) {
            setSize(d);
            setPreferredSize(d);
            setMaximumSize(d);
            setMinimumSize(d);
        }
    }
}
