package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.components.generic.GenericComponent;

public class ActionAdaptator<T extends GenericComponent> implements ActionListener {
    protected final T entity;

    /**
     * Création d'un adaptateur pour un composant générique
     * @param button Le composant générique a écouter
     */
    public ActionAdaptator(T button) {
        this.entity = button;
    }

    protected void process(ActionEvent e) {
		
	}

    @Override
    /**
    * L'action du bouton sera lancé si il n'est pas désactivé
    */
    public void actionPerformed(ActionEvent e) {
        // doesn't process if the button is disabled
		if(entity.isDisabled())
            return;

        process(e);
    }
}
