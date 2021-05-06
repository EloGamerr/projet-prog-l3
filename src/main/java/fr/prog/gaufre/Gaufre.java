package fr.prog.gaufre;

import fr.prog.gaufre.controller.AdaptateurSouris;
import fr.prog.gaufre.controller.Controller;
import fr.prog.gaufre.model.TimonModel;
import fr.prog.gaufre.view.GameWindow;

import javax.swing.*;

public class Gaufre implements Runnable {
	int lg,lr;
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Gaufre());
    }


    public void run() {
        JFrame jFrame = new JFrame("Gaufre");

        TimonModel model = new TimonModel(6, 8) {
		};
        GameWindow gameWindow = new GameWindow(model);
        Controller controller = new Controller(model, gameWindow);

        gameWindow.addMouseListener(new AdaptateurSouris(controller, gameWindow));

        jFrame.add(gameWindow);


        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }
}
