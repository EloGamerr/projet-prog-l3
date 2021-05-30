package fr.prog.tablut;

import java.text.ParseException;

import javax.swing.SwingUtilities;

import fr.prog.tablut.view.GlobalWindow;

public class Tablut implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Tablut());
    }

    public void run() {
        try {
            // can be changed to any configuration file later - or by entry
            String configFolder = "res/config/";
            String configFile = "project.json";

            new GlobalWindow(configFolder + configFile);
        }

        catch(ParseException e) {
            e.printStackTrace();
        }
    }
}
