package fr.prog.gaufre.view;

import fr.prog.gaufre.controller.Controller;
import fr.prog.gaufre.controller.NewGameAdaptator;
import fr.prog.gaufre.controller.RollbackAdaptator;
import fr.prog.gaufre.controller.SaveAdaptator;

import javax.swing.*;

public class BottomInterface extends JPanel {
    public BottomInterface(Controller controller) {
        JButton newGameButton = new JButton("Nouvelle partie");
        newGameButton.addMouseListener(new NewGameAdaptator(controller));
        this.add(newGameButton);

        JButton rollbackButton = new JButton("Annuler le dernier coup");
        rollbackButton.addMouseListener(new RollbackAdaptator(controller));
        this.add(rollbackButton);

        JButton saveButton = new JButton("Sauvegarder la partie");
        saveButton.addMouseListener(new SaveAdaptator(controller));
        this.add(saveButton);
    }
}
