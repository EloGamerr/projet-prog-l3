package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.prog.tablut.structures.Couple;

public class PawnTaker {
	private List<Couple<Integer, Integer>> visited = new ArrayList<>();
	private Game game;
	
	
	PawnTaker(Game game){
		this.game = game;
	}
	
	// On check les voisins du pion attaquant qui a joué
	public void towerTaker_attack(int l, int c) {
		towerTaker_attack_core(l-1,c,-1,0);
		towerTaker_attack_core(l+1,c,1,0);
		towerTaker_attack_core(l,c-1,0,-1);
		towerTaker_attack_core(l,c+1,0,1);
	}
	// On check les voisins du pion attaquant qui a joué
	public void towerTaker_defense(int l, int c) {
		towerTaker_defense_core(l-1,c,-1,0);
		towerTaker_defense_core(l+1,c,1,0);
		towerTaker_defense_core(l,c-1,0,-1);
		towerTaker_defense_core(l,c+1,0,1);
	}
	
	
	public void towerTaker_defense_core(int l, int c, int dl, int dc){
		if(game.isAttackTower(l, c)){
			if(isDefTowerHelper(l+dl, c+dc)) // Si deux cases plus loin nous avons un allié de circonstance pour la défense
				game.setContent(CellContent.EMPTY,l,c); // on enleve le pion ennemi
			else  
				testSurround(l, c); // Sinon on vérifie que le coup joué  bloque totalement le pion ennemi
				
		}
	}
	
	public void towerTaker_attack_core(int l, int c, int dl, int dc){
		if(game.isDefenseTower(l, c)){ // Si la case contient une tour défensive
			if(isAttTowerHelper(l+dl, c+dc)) // Si deux cases plus loin nous avons un allié de circonstance pour l'attaque
				game.setContent(CellContent.EMPTY,l,c); // on enleve le pion ennemi
			else 
				testSurround(l, c);  // Sinon on vérifie que le coup joué  bloque totalement le pion ennemi
		}
		else if(game.isTheKing(l,c)) // Si la case contient le roi
				testSurround(l, c);  // On vérifie que le coup joué  bloque totalement le roi ennemi
	}
	
	
	public void testSurround(int l, int c) {
		if(isSurrounded(new Couple<Integer, Integer>(l, c),l,c)) { // Si le pion est encerclé
			game.setContent(CellContent.EMPTY,l,c); // On enlève le pion
			if(!visited.isEmpty()) {
				for(Couple<Integer, Integer> cell : visited) { 
					game.setContent(CellContent.EMPTY,cell.getFirst(),cell.getSecond()); // // Et on enlève les pions encerclés avec le précédent
				}
			}
		}
		visited.clear();
	}
	
	public boolean isSurrounded(Couple<Integer, Integer> c, int i, int j) {
		List<Couple<Integer, Integer>> allyCells = new ArrayList<>();
		boolean surrounded = true;
		int counter_obstacle = 0;
		Couple<Integer , List<Couple<Integer, Integer>>> var = new Couple<Integer, List<Couple<Integer, Integer>>>(counter_obstacle, allyCells);
		
		switch(game.getGrid()[i][j]) {
			case DEFENSE_TOWER:
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i-1, j);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i+1, j);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i, j-1);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i, j+1);
				break;

			case KING:
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i-1, j);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i+1, j);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i, j-1);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), i, j+1);
				break;

			case ATTACK_TOWER:
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), i-1, j);
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), i+1, j);
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), i, j-1);
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), i, j+1);
				break;
				
			default: break;	
		}
		
		counter_obstacle = var.getFirst();
		if(counter_obstacle == 4) { // Le pion ne peut plus se déplacer
				if(var.getSecond().size()!=0) { // Si il a des alliés autour de lui, on vérifie si eux mêmes sont bloqués 
					if(!visited.contains(c)) {
						visited.add(c);
					}
					for(Couple<Integer, Integer> cell : visited) {
						allyCells.remove(cell);
					}
					for(Couple<Integer, Integer> cell : allyCells) {
						if(!isSurrounded(new Couple<Integer, Integer>(cell.getFirst(), cell.getSecond()),cell.getFirst(),cell.getSecond())) {
							surrounded = false;
						}
					}
					return surrounded;	
				}
				return true; 
		}
		return false;		
	}

	// vérifie de quelle nature est la case voisine 
	public Couple<Integer, List<Couple<Integer, Integer>>> checkneighbour_attack(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int i, int j){
			if(!game.isValid(i, j)) 
				counter_obstacle++;
			
			else if(isDefTowerHelper(i,j)) 
				counter_obstacle++;
			
			else if(game.isAttackTower(i,j)){
				allyCells.add(new Couple<Integer, Integer>(i, j));
				counter_obstacle++;
			}
			
			return new Couple<Integer, List<Couple<Integer, Integer>>>(counter_obstacle, allyCells);
	}
	
	// vérifie de quelle nature est la case voisine 
	public Couple<Integer, List<Couple<Integer, Integer>>> checkneighbour_defense(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int i, int j){
			if(!game.isValid(i, j)) 
				counter_obstacle++;
			
			else if(isAttTowerHelper(i,j)) 
				counter_obstacle++;	
			
			else if(isDefenseAlly(i, j)) {
				allyCells.add(new Couple<Integer, Integer>(i, j));
				counter_obstacle++;
			}
			
			return new Couple<Integer, List<Couple<Integer, Integer>>>(counter_obstacle, allyCells);
	}
	
	
	
	
	
	public boolean isDefTowerHelper(int i, int j) {
		if((game.isTheKingPlace(i,j) && !game.isOccupied(i,j)) || game.isDefenseTower(i,j) || game.isGate(i,j)) 
			return true;
		else 
			return false;
	}
	
	
	
	public boolean isAttTowerHelper(int i, int j) {
		if((game.isTheKingPlace(i,j) && !game.isOccupied(i,j)) || game.isAttackTower(i,j) || game.isGate(i,j)) 
			return true;
		else 
			return false;
	}
	
	public boolean isDefenseAlly(int i ,int j) {
		if(game.isTheKing(i, j) || game.isDefenseTower(i, j))
			return true;
		else
			return false;
	}
}
