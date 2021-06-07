package fr.prog.tablut;

import java.text.ParseException;

import javax.swing.SwingUtilities;

import fr.prog.tablut.view.GlobalWindow;

/**
 * --- MAIN ENTRY ---
 */
public class Tablut implements Runnable {
    public static String configPath = "config/light_theme.json";
    private String configFile = null;

    public static void main(String[] args) {
        String config = (args.length > 0)? args[0] : configPath;
        SwingUtilities.invokeLater(new Tablut(config));
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
            new GlobalWindow(configFile);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
    }
}
