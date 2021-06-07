package fr.prog.tablut.controller.game.ia;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.structures.Possibility;

@Deprecated
public abstract class AIPlayer extends Player {	
	/**
	 * 
	 * @param grid : le tableau du jeu
	 * @param ownedCells : Les cases utilis�es par le joueur dont on simule le coup
	 * @return La liste des possibilit�es
	 */
	protected List<Possibility> getPossibilities(CellContent[][] grid, List<Point> ownedCells) {
		List<Possibility> listPossibilities = new ArrayList<>();
		
		for(Point cell : ownedCells) {
			//Remplacer ownedCell par les cases de player
			
			int L = cell.y;
			int C = cell.x;

			for(int toL = L-1 ; toL >= 0 ; toL--) {
				if(grid[C][toL] != CellContent.EMPTY)
					break;
				listPossibilities.add(new Possibility(cell, new Point(C, toL)));
			}
				
			
			for(int toL = L+1 ; toL < grid.length ; toL++) {
				if(grid[C][toL] != CellContent.EMPTY)
					break;
				listPossibilities.add(new Possibility(cell, new Point(C, toL)));
			}
			
			for(int toC = C-1 ; toC >= 0 ; toC--) {
				if(grid[toC][L] != CellContent.EMPTY)
					break;
				listPossibilities.add(new Possibility(cell, new Point(toC, L)));
			}
			
			for(int toC = C+1 ; toC < grid[0].length ; toC++) {
				if(grid[toC][L] != CellContent.EMPTY) 
					break;
				listPossibilities.add(new Possibility(cell, new Point(toC, L)));
			}
		}
		
		return listPossibilities;
	}
	
	/**
	 * 
	 * @param playingPlayer
	 * @param grid
	 * @param P1
	 * @param P2
	 * @return
	 */
	protected boolean isBetter(PlayerEnum playingPlayer,CellContent[][] grid, Possibility P1, Possibility P2) {
		// Peut etre supprim� pour mettre la note directement dans possibility
		
		//TODO Le truc chiant
		//Roi encadr� --> meilleur pour les noir
		//Le roi peut s'echapper --> meilleur pour les blancs
		//Manger une pi�ce --> 
		return false;
	}
	
	
}
