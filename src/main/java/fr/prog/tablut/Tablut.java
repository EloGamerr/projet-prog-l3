package fr.prog.tablut;

import fr.prog.tablut.controller.AdaptateurSouris;
import fr.prog.tablut.controller.Controller;
import fr.prog.tablut.model.Model;
import fr.prog.tablut.view.GridWindow;

import javax.swing.*;

public class Tablut implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Tablut());
    }


    public void run() {
        JFrame jFrame = new JFrame("Tablut");

        Model model = new Model();
        GridWindow gridWindow = new GridWindow(model);
        Controller controller = new Controller(model, gridWindow);

        gridWindow.addMouseListener(new AdaptateurSouris(controller, gridWindow));

        jFrame.add(gridWindow);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }
}
