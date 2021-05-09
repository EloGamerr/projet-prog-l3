package fr.prog.gaufre.controller;

import fr.prog.gaufre.model.Couple;
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
		if(this.model.play(l, c, this.getPlayerNumber(false))) {
			this.window.actualize();
			IA ia = this.model.getIA();
			if(ia != null) {
				this.playIA(ia);
			}
		}
	}

	public void newGame() {
		System.out.println("New game");
		if(this.model.newGame()) {
			this.window.actualize();
			IA ia = this.model.getIA();
			if(ia != null) {
				if(ia.getPlayerNumber() == 1) {
					System.out.println("L'IA commence à jouer");
					this.playIA(ia);
				}
				else {
					System.out.println("Le joueur commence à jouer");
				}
			}
			
		}
	}

	private void playIA(IA ia) {
		Couple<Integer, Integer> coord = ia.play();
		if(coord != null) {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Controller.this.model.play(coord.getFirst(), coord.getSecond(), Controller.this.getPlayerNumber(true));
					Controller.this.window.actualize();
				}
			}.start();
		}
	}
	
	public void rollback() {
		System.out.println("Rollback");
		if(this.model.rollback())
			this.window.actualize();
	}

	public void save() {
		System.out.println("Save");
		if(this.model.save())
			this.window.actualize();
	}
	
	public void load() {
		System.out.println("Load");
		if(this.model.load()) {
			this.window.actualize();
		}
	}
	
	private short getPlayerNumber(boolean isIA) {
		IA ia = this.model.getIA();
		
		if(ia == null) {
			if(isIA) 
				return 0;
			
			return this.model.getPlayingPlayer();
		}
		
		if(isIA)
			return ia.getPlayerNumber();
		
		return (short) (ia.getPlayerNumber() % 2 + 1);
	}
}
