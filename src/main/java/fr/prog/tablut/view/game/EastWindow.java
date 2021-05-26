package fr.prog.tablut.view.game;

import fr.prog.tablut.model.Game;
import fr.prog.tablut.view.generic.GenericButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EastWindow extends JPanel {
    public EastWindow(Game game) {
        this.setBackground(new Color(0, 0, 0));
        GenericButton save = new GenericButton("Sauvegarde");
        this.add(save);
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
