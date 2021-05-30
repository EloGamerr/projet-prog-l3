package fr.prog.tablut.view.components;

import javax.swing.border.EmptyBorder;

import fr.prog.tablut.view.components.generic.GenericText;

public class Title extends GenericText {
    public Title(String text) {
        super(text, 100);
		setBorder(new EmptyBorder(0, 0, 20, 0));
    }

    public Title(String text, int fontSize) {
        super(text, fontSize);
		setBorder(new EmptyBorder(0, 0, 20, 0));
    }
}
