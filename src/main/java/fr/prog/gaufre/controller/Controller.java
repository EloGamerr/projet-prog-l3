package fr.prog.gaufre.controller;

import fr.prog.gaufre.model.Model;
import fr.prog.gaufre.view.GameWindow;

public class Controller {

	Model model;
	GameWindow window;
	
	public Controller(Model model, GameWindow window) {
		this.model = model;
		this.window = window;
	}
	
	public void click(int l, int c) {
		this.model.play(l, c);
	}
}
