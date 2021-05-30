package fr.prog.tablut.view.components.generic;

import javax.swing.JPanel;

public class GenericText extends JPanel {
    protected GenericLabel label;

    public GenericText(String text) {
        label = new GenericLabel(text, 15);
        init();
    }
    
    public GenericText(String text, int fontSize) {
        label = new GenericLabel(text, fontSize);
        init();
    }

    private void init() {
        add(label);
		setOpaque(false);
    }

    public void setText(String text) {
        label.setText(text);
    }
}
