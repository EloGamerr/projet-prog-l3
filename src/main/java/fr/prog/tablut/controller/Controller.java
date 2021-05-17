package fr.prog.tablut.controller;

import fr.prog.tablut.model.Model;
import fr.prog.tablut.view.center.GridWindow;

public class Controller {

	private Model model;
	private GridWindow gridWindow;
	
	public Controller(Model model, GridWindow gridWindow) {
		this.model = model;
		this.gridWindow = gridWindow;
	}
	
	public void click(int row, int col) {

	}
}
