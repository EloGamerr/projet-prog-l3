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
		if(this.model.play(l, c))
			this.window.acutalize();
	}

	public void newGame() {
		System.out.println("New game");
		if(this.model.newGame())
			this.window.acutalize();
	}

	public void rollback() {
		System.out.println("Rollback");
		if(this.model.rollback())
			this.window.acutalize();
	}

	public void save() {
		System.out.println("Save");
		if(this.model.save())
			this.window.acutalize();
	}
	
	public void load() {
		System.out.println("Load");
		this.model.load();
	}
}
