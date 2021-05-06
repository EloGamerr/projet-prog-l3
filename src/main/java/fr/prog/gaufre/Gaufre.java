package fr.prog.gaufre;

import fr.prog.gaufre.controller.AdaptateurSouris;
import fr.prog.gaufre.model.Model;
import fr.prog.gaufre.view.GameWindow;

import javax.swing.*;

public class Gaufre implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Gaufre());
    }


    public void run() {
        JFrame jFrame = new JFrame("Gaufre");

        //Model model;
        GameWindow gameWindow = new GameWindow(/*model*/);

        gameWindow.addMouseListener(new AdaptateurSouris(/*controller*/));

        jFrame.add(gameWindow);


        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }
}
