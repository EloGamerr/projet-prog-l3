package fr.prog.gaufre.controller;

import java.util.List;

import fr.prog.gaufre.model.Couple;
import fr.prog.gaufre.model.Model;

public class IARandom extends IA {

	public IARandom(Model model) {
		super(model);
	}

	@Override
	public Couple<Integer, Integer> play() {
		List<Couple<Integer, Integer>> playableCells = this.model.playableCells();
		
		if(playableCells.isEmpty()) return null;
		
		return playableCells.get(this.random.nextInt(playableCells.size()));
	}
	
}
