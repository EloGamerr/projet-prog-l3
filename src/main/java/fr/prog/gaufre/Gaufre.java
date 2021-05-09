package fr.prog.gaufre;


import javax.swing.*;

import fr.prog.gaufre.controller.Controller;
import fr.prog.gaufre.controller.adaptators.MouseAdaptator;
import fr.prog.gaufre.model.Model;
import fr.prog.gaufre.view.BottomInterface;
import fr.prog.gaufre.view.GameWindow;

import java.awt.*;

public class Gaufre implements Runnable {
	int lg, lr;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Gaufre());
	}

	public void run() {
		JFrame jFrame = new JFrame("Gaufre");

		Model model = new Model(10, 10);
		//model.setIA(new IAminmax(model));
		
		GameWindow gameWindow = new GameWindow(model);
		Controller controller = new Controller(model, gameWindow);
		controller.newGame();
		
		gameWindow.addMouseListener(new MouseAdaptator(controller, gameWindow));

		jFrame.add(gameWindow);

		jFrame.add(new BottomInterface(controller), BorderLayout.SOUTH);

		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setSize(1000, 1000);
		jFrame.setVisible(true);
	
	}
}
