package fr.prog.tablut.controller.adaptators;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fr.prog.tablut.view.components.generic.GenericInput;

/**
* Adaptateur pour les inputs, utilisé pour saisir les noms des joueurs
*/
public class InputAdaptator extends ActionAdaptator<GenericInput> implements KeyListener {
    public InputAdaptator(GenericInput input) {
        super(input);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Le nom ne peut pas dépasser 16 caractère
        if(entity.getText().length() > 16) {
            entity.setText(entity.getText().substring(0, 16));
        }

        int k = e.getKeyCode();

        if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT) {
            entity.revalidate();
            entity.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
