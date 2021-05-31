package fr.prog.tablut;

import java.text.ParseException;

import javax.swing.SwingUtilities;

import fr.prog.tablut.view.GlobalWindow;

/**
 * --- MAIN ENTRY ---
 */
public class Tablut implements Runnable {
    String configFolder = "res/config/";
    String configFile = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Tablut("project.json"));
    }

    /**
     * Loads the game without any configuration - keeps the default one
     */
    public Tablut() {
        this(null);
    }

    /**
     * Loads the game with given configuration
     * @param configFilePath The configuration file path
     */
    public Tablut(String configFilePath) {
        configFile = configFilePath;
    }

    public void run() {
        try {
            String cfg = (configFile==null)? null : configFolder + configFile;
            new GlobalWindow(cfg);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
    }
}
