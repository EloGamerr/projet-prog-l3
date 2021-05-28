package fr.prog.tablut.view;

import java.text.ParseException;
import javax.swing.JPanel;

import org.json.JSONObject;

import fr.prog.tablut.model.window.WindowConfig;
import fr.prog.tablut.model.window.WindowName;

public abstract class Window extends JPanel {
	protected WindowConfig config;

	/**
	 * Creates a basic window extending JPanel
	 */
	public Window() {
		config = new WindowConfig();
	}

	/**
	 * Returns the name of the window (sub-window, aka tab)
	 * @return The name of the window
	 */
	public WindowName name() {
		return WindowName.DefaultWindow;
	}

	/**
	 * Returns the configuration object of the window
	 * @return The window's configuration
	 */
	public WindowConfig getConfig() {
		return config;
	}

	/**
	 * Set the window's configuration from the file at given path
	 * @param configPath file's path
	 * @throws ParseException
	 */
	protected void setConfig(String configPath) throws ParseException {
		config.setConfig(configPath);
	}

	/**
	 * Set the window's configuration from given JSON
	 * @param configObject The json object
	 */
	protected void setConfig(JSONObject configObject) {
		config.setConfig(configObject);
	}

	/**
	 * Copies the window's configuration from another window's config
	 * @param config The configuration to copy
	 */
	protected void setConfig(WindowConfig config) {
		this.config.setConfig(config);
	}
}