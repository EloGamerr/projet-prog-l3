package fr.prog.gaufre.controller;

import java.util.Iterator;
import java.util.List;

import fr.prog.gaufre.model.Couple;
import fr.prog.gaufre.model.Model;

public class IALooseWin extends IA{

	public IALooseWin(Model model) {
		super(model);
	}

	@Override
	public Couple<Integer, Integer> play() {
		short[][] grid = this.model.get_grid();
		
		if(grid[0][1] == 0) {
			boolean isEmpty = true;
			
			for(int i = 1 ; i < grid.length ; i++) {
				if(grid[i][0] == 0)
					isEmpty = false;
			}
			if(isEmpty)
				return new Couple<Integer, Integer>(0, 1);
		}
	
		if(grid[1][0] == 0) {
			boolean isEmpty = true;
			
			for(int i = 1 ; i < grid[0].length ; i++) {
				if(grid[0][i] == 0)
					isEmpty = false;
			}
			if(isEmpty)
				return new Couple<Integer, Integer>(1, 0);
		}
		
		List<Couple<Integer, Integer>> playableCells = this.model.playableCells();
		
		if(playableCells.isEmpty()) return null;
		
		Iterator<Couple<Integer, Integer>> it = playableCells.iterator();
		while(it.hasNext()) {
			Couple<Integer, Integer> coord = it.next();
			if(coord.getFirst() == 1 && coord.getSecond() == 0 || coord.getFirst() == 0 && coord.getSecond() == 1)
				it.remove();
		}
		
		if(playableCells.isEmpty()) {
			if(grid[0][1] == 0)
				return new Couple<Integer, Integer>(0, 1);
			if(grid[1][0] == 0)
				return new Couple<Integer, Integer>(1, 0);
		}
		
		return playableCells.get(this.random.nextInt(playableCells.size()));
	}
}
