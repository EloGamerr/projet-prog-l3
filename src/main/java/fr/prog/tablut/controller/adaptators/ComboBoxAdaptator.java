package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import fr.prog.tablut.view.pages.newGame.SelectionPlayer;

public class ComboBoxAdaptator implements ActionListener {
	private final SelectionPlayer selectionPlayer;
	private final String side;
	
	public ComboBoxAdaptator(String side, SelectionPlayer selectionPlayer) {
		this.selectionPlayer = selectionPlayer;
		this.side = side;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<?> comboBox = (JComboBox<?>)e.getSource();

		if(comboBox.getSelectedItem() == "Humain")
			selectionPlayer.showInput(side);
		else
			selectionPlayer.hideInput(side);
	}
}
