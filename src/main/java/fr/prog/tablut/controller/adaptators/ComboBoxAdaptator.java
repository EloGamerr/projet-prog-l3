package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import fr.prog.tablut.view.pages.newGame.SelectionPlayerForm;

public class ComboBoxAdaptator implements ActionListener {
	private final SelectionPlayerForm selectionPlayer;
	private final String side;
	
	public ComboBoxAdaptator(String side, SelectionPlayerForm selectionPlayer) {
		this.selectionPlayer = selectionPlayer;
		this.side = side;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<?> comboBox = (JComboBox<?>)e.getSource();

        if(comboBox.getSelectedItem().toString() == "Humain")
			selectionPlayer.showInput(side);
		else
			selectionPlayer.hideInput(side);
	}
}
