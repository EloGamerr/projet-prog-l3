package fr.prog.tablut.view;

import javax.swing.JPanel;

import fr.prog.tablut.model.WindowName;

public abstract class Window extends JPanel{
	public WindowName name() {
		return WindowName.DefaultWindow;
	}
}