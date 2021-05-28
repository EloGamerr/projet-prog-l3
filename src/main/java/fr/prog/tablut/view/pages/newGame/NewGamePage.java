package fr.prog.tablut.view.pages.newGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.prog.tablut.model.window.WindowName;
import fr.prog.tablut.view.Page;
import fr.prog.tablut.view.GlobalWindow;
import fr.prog.tablut.view.components.generic.GenericButton;
import fr.prog.tablut.view.components.generic.GenericLabel;

public class NewGamePage extends Page {
	JTextField pseudoAttaquant;
	JTextField pseudoDefenseur;

	public NewGamePage(GlobalWindow globalWindow) {
		super(globalWindow);

		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		
		add(new TopLabel(), c);
		
		c.gridy = 1;

		add(new SelectionPlayer(), c);
		
		c.gridy = 2;
		c.insets = new Insets(100, 0, 0, 0);

		add(new BottomButton(), c);
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
		setOpaque(false);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		add(new GenericLabel("NOUVELLE PARTIE", 30), c);
		
		c.insets = new Insets(20, 0, 0, 0);
		c.gridy = 1;
		add(new GenericLabel("Choisissez les paramètres de partie qui vous conviennent puis cliquez sur confirmer", 20), c);
	}
}

class BottomButton extends JPanel {
	public BottomButton() {
		setOpaque(false);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		add(new GenericButton("Retour", GenericButton.GO(WindowName.HomeWindow), new Dimension(150, 30)), c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 20, 0, 0);
		add(new GenericButton("Jouer !", GenericButton.GO(WindowName.GameWindow), new Dimension(150, 30)), c);
	}
}