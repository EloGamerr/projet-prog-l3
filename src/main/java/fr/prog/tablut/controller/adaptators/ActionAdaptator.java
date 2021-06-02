package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.prog.tablut.view.components.generic.GenericComponent;

public class ActionAdaptator<T extends GenericComponent> implements ActionListener {
    protected final T entity;

    public ActionAdaptator(T button) {
        this.entity = button;
    }

    protected void process(ActionEvent e) {
		
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        // doesn't process if the button is disabled
		if(entity.isDisabled())
            return;

        process(e);
    }
}
