package fr.prog.tablut.model.game;

import java.util.ArrayList;
import java.util.List;
import fr.prog.tablut.structures.Couple;

public class PawnTaker {
	private List<Couple<Integer, Integer>> visited = new ArrayList<>();
	private Game game;
	
	
	////////////////////////////////////////////////////
	// Constructor
	////////////////////////////////////////////////////	
	
	PawnTaker(Game game) {
		this.game = game;
	}
	
	
	////////////////////////////////////////////////////
	// Main function
	////////////////////////////////////////////////////	
	
	public void clearTakedPawns(int l, int c, Play play) {
		if(game.isAttackTower(l, c))
			attack(l,c, play );

		if(game.isDefenseTower(l, c))
			defense(l,c, play);
	}
	
	////////////////////////////////////////////////////
	// Attack 
	////////////////////////////////////////////////////
	
	public void attack(int l, int c, Play play) {
		attack_core(l-1,c,-1,0, play);
		attack_core(l+1,c,1,0, play);
		attack_core(l,c-1,0,-1, play);
		attack_core(l,c+1,0,1, play);
	}
	
	public void attack_core(int l, int c, int dl, int dc, Play play){
		if(game.isDefenseTower(l, c)){ // Si la case contient une tour d�fensive
			if(isAttTowerHelper(l+dl, c+dc)) { // Si deux cases plus loin nous avons un alli� de circonstance pour l'attaque
				play.putModifiedOldCellContent(new Couple<>(l, c), game.getCellContent(l, c));
				game.setContent(CellContent.EMPTY,l,c); // on enleve le pion ennemi
				play.putModifiedNewCellContent(new Couple<>(l, c), CellContent.EMPTY);
			}
			else 
				testSurround(l, c, play);  // Sinon on v�rifie que le coup jou�  bloque totalement le pion ennemi
		}
		else if(game.isTheKing(l,c)) // Si la case contient le roi
				testSurround(l, c, play);  // On v�rifie que le coup jou�  bloque totalement le roi ennemi
	}
	
	////////////////////////////////////////////////////
	// Defense 
	////////////////////////////////////////////////////
	
	public void defense(int l, int c, Play play) {
		defense_core(l-1,c,-1,0, play);
		defense_core(l+1,c,1,0, play);
		defense_core(l,c-1,0,-1, play);
		defense_core(l,c+1,0,1, play);
	}
	
	public void defense_core(int l, int c, int dl, int dc, Play play){
		if(game.isAttackTower(l, c)){
			if(isDefTowerHelper(l+dl, c+dc)) { // Si deux cases plus loin nous avons un alli� de circonstance pour la d�fense
				play.putModifiedOldCellContent(new Couple<>(l, c), game.getCellContent(l, c));
				game.setContent(CellContent.EMPTY,l,c); // on enleve le pion ennemi
				play.putModifiedNewCellContent(new Couple<>(l, c), CellContent.EMPTY);
			}
			else  testSurround(l, c, play); // Sinon on v�rifie que le coup jou�  bloque totalement le pion ennemi		
		}
	}
	
	////////////////////////////////////////////////////
	// Surround test 
	////////////////////////////////////////////////////
	
	
	public void testSurround(int l, int c, Play play) {
		if(isSurrounded(new Couple<Integer, Integer>(l, c),l,c)) { // Si le pion est encercl�
			play.putModifiedOldCellContent(new Couple<>(l, c), game.getCellContent(l, c));
			game.setContent(CellContent.EMPTY,l,c); // On enl�ve le pion
			play.putModifiedNewCellContent(new Couple<>(l, c), CellContent.EMPTY);
			
			if(!visited.isEmpty()) {
				for(Couple<Integer, Integer> cell : visited) {
					play.putModifiedOldCellContent(cell, game.getCellContent(cell.getFirst(), cell.getSecond()));
					game.setContent(CellContent.EMPTY,cell.getFirst(),cell.getSecond()); // // Et on enl�ve les pions encercl�s avec le pr�c�dent
					play.putModifiedOldCellContent(cell, CellContent.EMPTY);
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
		
		if(counter_obstacle == 4) { // Le pion ne peut plus se d�placer
			if(var.getSecond().size()!=0) { // Si il a des alli�s autour de lui, on v�rifie si eux m�mes sont bloqu�s 
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

	
	////////////////////////////////////////////////////
	// Check content functions
	////////////////////////////////////////////////////
	
	public Couple<Integer, List<Couple<Integer, Integer>>> checkneighbour_attack(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int i, int j) {
		if(!game.isValid(i, j))
			counter_obstacle++;

		else if(isDefTowerHelper(i,j))
			counter_obstacle++;

		else if(game.isAttackTower(i,j)) {
			allyCells.add(new Couple<Integer, Integer>(i, j));
			counter_obstacle++;
		}

		return new Couple<Integer, List<Couple<Integer, Integer>>>(counter_obstacle, allyCells);
	}

	public Couple<Integer, List<Couple<Integer, Integer>>> checkneighbour_defense(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int i, int j) {
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
		return ((game.isTheKingPlace(i,j) && !game.isOccupied(i,j)) || game.isDefenseTower(i,j) || game.isGate(i,j));
	}
	public boolean isAttTowerHelper(int i, int j) {
		return ((game.isTheKingPlace(i,j) && !game.isOccupied(i,j)) || game.isAttackTower(i,j) || game.isGate(i,j));
	}
	
	public boolean isDefenseAlly(int i ,int j) {
		return (game.isTheKing(i, j) || game.isDefenseTower(i, j));
	}
}
