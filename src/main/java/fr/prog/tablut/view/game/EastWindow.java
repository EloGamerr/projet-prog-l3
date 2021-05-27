package fr.prog.tablut.view.game;

import fr.prog.tablut.controller.adaptators.ButtonSaveAdaptator;
import fr.prog.tablut.model.Game;
import fr.prog.tablut.view.generic.GenericButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EastWindow extends JPanel {
    public EastWindow(Game game) {
        this.setBackground(new Color(0, 0, 0));
        GenericButton save = new GenericButton("Nouvelle Sauvegarde");
        this.add(save);
        save.addMouseListener(new ButtonSaveAdaptator(game,this));
        
        GenericButton saveToFile = new GenericButton("Sauvegarder");
        this.add(saveToFile);
        saveToFile.addMouseListener(new ButtonSaveAdaptator(game,this));
    
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
    
    
}
