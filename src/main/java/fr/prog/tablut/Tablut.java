package fr.prog.tablut;


import javax.swing.*;

import fr.prog.tablut.view.GlobalWindow;

public class Tablut implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Tablut());
    }

    public void run() {
        JFrame jFrame = new JFrame("Tablut");

        new GlobalWindow(jFrame);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
        jFrame.setSize(1200, 800);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
