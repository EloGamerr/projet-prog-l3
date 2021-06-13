package fr.prog.tablut.controller.adaptators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JComboBox;

import fr.prog.tablut.view.pages.newGame.SelectionPlayerForm;

/**
* Adaptateur pour les comboBox, utilisé pour séléctioner les types de joueurs pour les attaquants et les défenseur
*/
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

		//Si l'utilisateur séléctionne l'humain, on affiche l'input pour séléctionner le nom
        if(Objects.requireNonNull(comboBox.getSelectedItem()).toString().equals("Humain"))
			selectionPlayer.showInput(side);
		else
		//Sinon on le cache
			selectionPlayer.hideInput(side);
	}
}
