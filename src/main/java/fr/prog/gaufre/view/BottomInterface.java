package fr.prog.gaufre.view;

import fr.prog.gaufre.controller.Controller;
import fr.prog.gaufre.controller.adaptators.IaAdaptator;
import fr.prog.gaufre.controller.adaptators.LoadAdaptator;
import fr.prog.gaufre.controller.adaptators.NewGameAdaptator;
import fr.prog.gaufre.controller.adaptators.RollbackAdaptator;
import fr.prog.gaufre.controller.adaptators.SaveAdaptator;


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
        
        JButton loadButton = new JButton("Charger une partie");
        loadButton.addMouseListener(new LoadAdaptator(controller));
        this.add(loadButton);
        
        JButton IAButton1 = new JButton("Joueur 1");
        IAButton1.addMouseListener(new IaAdaptator(controller,1,IAButton1));
        this.add(IAButton1);
        
        JButton IAButton2 = new JButton("Joueur 2");
        IAButton2.addMouseListener(new IaAdaptator(controller,2,IAButton2));
        this.add(IAButton2);
        
        
    }
}
