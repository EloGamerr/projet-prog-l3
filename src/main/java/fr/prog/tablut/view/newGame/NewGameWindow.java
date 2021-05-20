package fr.prog.tablut.view.newGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.prog.tablut.model.WindowName;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.Window;
import fr.prog.tablut.view.generic.GenericButton;
import fr.prog.tablut.view.generic.GenericLabel;

public class NewGameWindow extends Window{
	JTextField pseudoAttaquant;
	JTextField pseudoDeffenseur;
	public NewGameWindow(GlobalWindow globalWindow) {
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		
		this.add(new TopLabel(),c);
		
		c.gridy = 1;
		this.add(new SelectionPlayer(),c);
		
		c.gridy = 2;
		c.insets = new Insets(100, 0, 0, 0);
		this.add(new BottomButton(),c);
		
		
	}
	
	protected void paintComponent(Graphics graphics) {
        Graphics2D drawable = (Graphics2D) graphics;

        int width = getSize().width;
        int height = getSize().height;

        drawable.clearRect(0, 0, width, height);
        
        super.paintComponent(graphics);
    }

	@Override
	public WindowName name() {
		return WindowName.NewGameWindow;
	}
}


class TopLabel extends JPanel {
	public TopLabel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(new GenericLabel("NOUVELLE PARTIE", 30),c);
		
		c.insets = new Insets(20, 0, 0, 0);
		c.gridy = 1;
		this.add(new GenericLabel("Choisissez les paramètres de partie qui vous conviennent puis cliquez sur confirmer", 20),c);
	}
}

class BottomButton extends JPanel {
	public BottomButton() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(new GenericButton("Retour",GenericButton.GO(WindowName.HomeWindow), new Dimension(150,30)),c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 20, 0, 0);
		this.add(new GenericButton("Jouer !",GenericButton.GO(WindowName.GameWindow), new Dimension(150,30)),c);
	}
}