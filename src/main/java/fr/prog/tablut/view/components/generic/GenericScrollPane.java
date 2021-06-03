package fr.prog.tablut.view.components.generic;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class GenericScrollPane extends JScrollPane {
    public GenericScrollPane(Component content) {
        super(content);

        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }
}
