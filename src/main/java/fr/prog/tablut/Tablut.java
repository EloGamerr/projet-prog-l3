package fr.prog.tablut;

import fr.prog.tablut.view.GlobalWindow;

import javax.swing.*;

public class Tablut implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Tablut());
    }

    public void run() {
        JFrame jFrame = new JFrame("Tablut");

        jFrame.setContentPane(new GlobalWindow());

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(800, 500);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }
}
