package fr.prog.tablut.view.components.generic;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class GenericScrollPane extends JScrollPane {
    public GenericScrollPane(Component content) {
        super(content);
        init();
    }

    public GenericScrollPane(Component content, int vsbPolicy, int hsbPolicy) {
        super(content, vsbPolicy, hsbPolicy);
        init();
    }

    private void init() {
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }
}
