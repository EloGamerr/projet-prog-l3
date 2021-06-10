package fr.prog.tablut;

import java.text.ParseException;

import javax.swing.SwingUtilities;

import fr.prog.tablut.view.window.GlobalWindow;

/**
 * --- MAIN ENTRY ---
 */
public class Tablut implements Runnable {
    // default loaded config if no argument given
    public static final String configPath = "config/dark_theme.json";
    private final String configFile;

    public static void main(String[] args) {
        // args[0] is the config file to load (app theme)
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
