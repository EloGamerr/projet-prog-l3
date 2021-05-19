package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.structures.Couple;

public class Game {
	Model model;
	int rowAmount, colAmount;
	CellContent[][] grid;
	final int middle;
	int kingL, kingC;
	boolean isWon;
	List<Couple<Integer, Integer>> visited = new ArrayList<>();
	
	Game(Model model){
		this.model = model;
		this.middle = 4;
		this.isWon = false;
		init_game(model.rowAmount(),model.colAmount());
	}

	void init_game(int rowAmount, int colAmount) {
		grid = new CellContent[rowAmount][colAmount];
		this.rowAmount = rowAmount;
		this.colAmount = colAmount;
		init_king();
		init_gates();
		init_towers();
	}

	
	void init_king() {
		setKing(middle,middle);
	}

	void init_towers() {
		init_attackTowers();
		init_defenseTowers();
	}

	void init_defenseTowers() {
		setDefenseTower(middle-1,middle);
		setDefenseTower(middle-2,middle);
		setDefenseTower(middle+1,middle);
		setDefenseTower(middle+2,middle);
		
		setDefenseTower(middle,middle-1);
		setDefenseTower(middle,middle-2);
		setDefenseTower(middle,middle+1);
		setDefenseTower(middle,middle+2);
	}
	
	void init_attackTowers() {
		setAttackTower(middle,0);
		setAttackTower(middle,colAmount-1);
		setAttackTower(0,middle);
		setAttackTower(rowAmount-1,middle);
		
		setAttackTower(middle-1,0);
		setAttackTower(middle-1,colAmount-1);
		setAttackTower(0,middle-1);
		setAttackTower(rowAmount-1,middle-1);
		
		setAttackTower(middle+1,0);
		setAttackTower(middle+1,colAmount-1);
		setAttackTower(0,middle+1);
		setAttackTower(rowAmount-1,middle+1);
		
		setAttackTower(middle,1);
		setAttackTower(middle,colAmount-2);
		setAttackTower(1,middle);
		setAttackTower(rowAmount-2,middle);
	}
	
	void init_gates() {
		setGate(0,0);
		setGate(rowAmount-1,0);
		setGate(0,colAmount-1);
		setGate(rowAmount-1,colAmount-1);
	}

	boolean move(int l, int c, int dL, int dC) {
		int toL = l+dL;
		int toC = c+dC;
		if(toL < 0 || toC < 0 || toL >= rowAmount || toC >= colAmount) return false;
		
		if(toL != l && toC != c) return false; // Déplacements en diagonal non autorisés
		
		CellContent fromCellContent = grid[l][c];
		if(fromCellContent == CellContent.EMPTY || fromCellContent == CellContent.GATE) return false;
		
		List<Couple<Integer, Integer>> accessibleCells = accessibleCells(l, c);
		if(!accessibleCells.contains(new Couple<Integer, Integer>(toL, toC))) return false;
		
		Play result = new Play();
		
		clear(l, c);
		result.move(l, c, toL, toC);
		l = toL;
		c = toC;
		setContent(fromCellContent, l, c);
		
		if(isAttackTower(l, c)) {
			towerTaker_attack(l,c);
			if(!visited.isEmpty()) {
				visited.clear();
			}
		}
		if(isDefenseTower(l, c)) {
			towerTaker_defense(l,c);
			if(!visited.isEmpty()) {
				visited.clear();
			}
		}
		if(isLost()) {
			// faire des trucs
		}
		return true;
	}


	public List<Couple<Integer, Integer>> accessibleCells(int fromL, int fromC) {
		List<Couple<Integer, Integer>> accessibleCells = new ArrayList<>();
		
		for(int toL = fromL-1 ; toL >= 0 ; toL--) {
			int toC = fromC;
			
			if(!checkAccess(fromL, fromC, toC, toL, accessibleCells))
				break;
		}
		
		for(int toL = fromL+1 ; toL < rowAmount ; toL++) {
			int toC = fromC;
			
			if(!checkAccess(fromL, fromC, toC, toL, accessibleCells))
				break;
		}
		
		for(int toC = fromC-1 ; toC >= 0 ; toC--) {
			int toL = fromL;
			
			if(!checkAccess(fromL, fromC, toC, toL, accessibleCells))
				break;
		}
		
		for(int toC = fromC+1 ; toC < colAmount ; toC++) {
			int toL = fromL;
			
			if(!checkAccess(fromL, fromC, toC, toL, accessibleCells))
				break;
		}
		
		return accessibleCells;
	}
	
	private boolean checkAccess(int fromL, int fromC, int toC, int toL, List<Couple<Integer, Integer>> accessibleCells) {
		CellContent fromCellContent = grid[fromL][fromC];
		CellContent toCellContent = grid[toL][toC];
		if(fromCellContent != CellContent.KING && (toCellContent == CellContent.GATE || (toL == middle && toC == middle))) return true;
		
		if(toCellContent != CellContent.EMPTY) return false;
		
		accessibleCells.add(new Couple<Integer, Integer>(toL, toC));
		
		return true;
	}
	
	void towerTaker_attack(int l, int c) {
		towerTaker_attack_core(l-1,c,-1,0);
		towerTaker_attack_core(l+1,c,1,0);
		towerTaker_attack_core(l,c-1,0,-1);
		towerTaker_attack_core(l,c+1,0,1);
	}
	
	void towerTaker_defense(int l, int c) {
		towerTaker_defense_core(l-1,c,-1,0);
		towerTaker_defense_core(l+1,c,1,0);
		towerTaker_defense_core(l,c-1,0,-1);
		towerTaker_defense_core(l,c+1,0,1);
	}

	void towerTaker_defense_core(int l, int c, int dl, int dc){
		if(isAttackTower(l, c)){
			if(isDefenseTower(l+dl, c+dc)){
				setContent(CellContent.EMPTY,l,c);
			}
			
			else if(isAttackTower(l+dl, c+dc)) {
				if(isSurrounded(null,l,c) && isSurrounded(new Couple<Integer, Integer>(l, c),l+dl,c+dc)) {
					setContent(CellContent.EMPTY,l,c);
					setContent(CellContent.EMPTY,l+dl,c+dc);
				}
			}
		}
	}

	void towerTaker_attack_core(int l, int c, int dl, int dc){
		if(isDefenseTower(l, c)){
			if(isAttackTower(l+dl, c+dc)){
				setContent(CellContent.EMPTY,l,c);
			}
			 
			if(isDefenseTower(l+dl, c+dc)) {
				if(isSurrounded(null,l,c) && isSurrounded(new Couple<Integer, Integer>(l, c),l+dl,c+dc)) {
					setContent(CellContent.EMPTY,l,c);
					setContent(CellContent.EMPTY,l+dl,c+dc);
				}
			}
		}
	}

	void checkneighbour_attack(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int counter_enemy, int i, int j){
		if(isAnAttackAllied(i,j)) {
			counter_obstacle++;
			counter_enemy++;
		}
		else {
			allyCells.add(new Couple<Integer, Integer>(i, j));
			counter_obstacle++;
		}
	}
	
	void checkneighbour_defense(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int counter_enemy, int i, int j){
		if(isADefenseAllied(i,j)) {
			counter_obstacle++;
			counter_enemy++;
		}
		else {
			allyCells.add(new Couple<Integer, Integer>(i, j));
			counter_obstacle++;
		}
	}
	
	boolean isSurrounded(Couple<Integer, Integer> c, int i, int j) {
		List<Couple<Integer, Integer>> allyCells = new ArrayList<>();
		boolean surrounded = true;
		int counter_obstacle = 0;
		int counter_enemy = 0;
		switch(grid[i][j]) {
			case DEFENSE_TOWER:
				
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i-1, j);
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i+1, j);
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i, j-1);
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i, j+1);
				
				break;
					
			case KING:
				
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i-1, j);
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i+1, j);
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i, j-1);
				checkneighbour_defense(allyCells, counter_obstacle, counter_enemy, i, j+1);
				
				break;
				
			case ATTACK_TOWER:
				
				checkneighbour_attack(allyCells, counter_obstacle, counter_enemy, i-1, j);
				checkneighbour_attack(allyCells, counter_obstacle, counter_enemy, i+1, j);
				checkneighbour_attack(allyCells, counter_obstacle, counter_enemy, i, j-1);
				checkneighbour_attack(allyCells, counter_obstacle, counter_enemy, i, j+1);
				
				break;
				
			default:
				
				break;	
		}
		if(counter_obstacle == 4) {
			if(counter_enemy == 3) {
				return true;
			}
			else {
				if(!visited.contains(c)) {
					visited.add(c);
				}
				for(Couple<Integer, Integer> cell : visited) {
					allyCells.remove(cell);
				}
				for(Couple<Integer, Integer> cell : allyCells) {
					if(!isSurrounded(new Couple<Integer, Integer>(i, j),cell.getFirst(),cell.getSecond())) {
						surrounded = false;
					}
				}
			}
		}
		else 
			return false;
		
		return surrounded;			
	}
	

	

	
	void setContent(CellContent cellContent, int l, int c) {
		if (cellContent == CellContent.KING) {
			if(isGate(l,c)) {
				clear(l,c);
				isWon = true;
			}
			kingC = c;
			kingL = l;
		}
		grid[l][c] = cellContent;
	}

	public boolean isLost() {
		if(!isTheKing(kingL,kingC)) 
			return true;
		else
			return false;
	}
	
	private void clear(int l, int c) {
		grid[l][c] = CellContent.EMPTY;
		
	}

	void setDefenseTower(int l, int c) {
		grid[l][c] = CellContent.DEFENSE_TOWER;
	}
	
	void setAttackTower(int l, int c) {
		grid[l][c] = CellContent.ATTACK_TOWER;
	}
	
	void setKing(int l, int c) {
		grid[l][c] = CellContent.KING;
		kingC = c;
		kingL = l;
	}
	
	void setGate(int l, int c) {
		grid[l][c] = CellContent.GATE;
	}
	
	boolean isAWall(int i, int j) {
		if(i == rowAmount-1 || j == colAmount-1) 
			return true;
		else
			return false;
	}
	
	boolean isTheKingPlace(int i, int j) {
		if(i == middle && j-1 == middle)
			return true;
		else
			return false;
	}
	
	
	
	boolean isADefenseAllied(int i, int j) {
		if(isAWall(i,j)|| isTheKingPlace(i,j) || isAttackTower(i,j) || isGate(i,j))
			return false;
		else if(isDefenseTower(i,j) || isTheKing(i,j)) 
			return true;
		return false;
	}
	
	boolean isAnAttackAllied(int i, int j) {
		if(isAWall(i,j) ||isDefenseTower(i,j) || isTheKing(i,j)|| isTheKingPlace(i,j) ||  isGate(i,j)) {
			return false;
		}
		else if(isAttackTower(i,j)) 
			return true;
		
		return false;
	}
	
	boolean isFree(int l, int c) {
		return grid[l][c] == CellContent.EMPTY;
	}

	boolean isTheKing(int l, int c) {
		return grid[l][c] == CellContent.KING;
	}
	
	boolean isAttackTower(int l, int c) {
		return grid[l][c] == CellContent.ATTACK_TOWER;
	}
	
	boolean  isDefenseTower(int l, int c) {
		return grid[l][c] == CellContent.DEFENSE_TOWER;
	}
	
	boolean isGate(int l, int c) {
		return grid[l][c] == CellContent.GATE;
	}
	
	
}
