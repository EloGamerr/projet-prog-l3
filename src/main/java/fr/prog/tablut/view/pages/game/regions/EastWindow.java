package fr.prog.tablut.view.pages.game.regions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.view.components.generic.GenericButton;

public class EastWindow extends JPanel {
    public EastWindow(Game game) {
        setBackground(new Color(0, 0, 0));
        GenericButton save = new GenericButton("Sauvegarde");
        add(save);
        save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				
			}});
        
        
        
        
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D drawable = (Graphics2D) graphics;
    }
    
    
}
