package fr.prog.tablut.view;

import javax.swing.JPanel;

import fr.prog.tablut.model.window.WindowName;

public abstract class Window extends JPanel {
	protected WindowName windowName = WindowName.DefaultWindow;

	/**
	 * Creates a basic window extending JPanel
	 */
	public Window() {
		
	}

	/**
	 * Returns the name of the window (sub-window, aka tab)
	 * @return The name of the window
	 */
	public WindowName name() {
		return windowName;
	}
}