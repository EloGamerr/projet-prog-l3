package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.prog.tablut.structures.Couple;

public class Game {
	private int rowAmount, colAmount;
	private CellContent[][] grid;
	private final int middle;
	private int kingL, kingC;
	private PlayerEnum winner;
	private final List<Couple<Integer, Integer>> visited = new ArrayList<>();
	private PlayerEnum player;
	
	public Game(){
		this.middle = 4;
		this.player = PlayerEnum.ATTACKER;
		
		init_game(9,9);
	}

	void init_game(int rowAmount, int colAmount) {
		grid = new CellContent[rowAmount][colAmount];
		for(int i = 0 ; i < rowAmount ; i++) {
			Arrays.fill(grid[i], CellContent.EMPTY);
		}
		
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

	public boolean move(int l, int c, int toL, int toC) {
		if(getWinner() != null) return false;
		
		if(!isValid(toL, toC)) return false;

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
		CellContent previousToCellContent = grid[l][c];
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
		if((winner = checkWin(previousToCellContent, fromCellContent)) != null) {
			System.out.println(winner + " a gagné !");
		}
		else {
			player = player.getOpponent();
		}
		return true;
	}


	public List<Couple<Integer, Integer>> accessibleCells(int fromL, int fromC) {
		List<Couple<Integer, Integer>> accessibleCells = new ArrayList<>();
		
		for(int toL = fromL-1 ; toL >= 0 ; toL--) {

			if(cantAccess(fromL, fromC, fromC, toL, accessibleCells))
				break;
		}
		
		for(int toL = fromL+1 ; toL < rowAmount ; toL++) {

			if(cantAccess(fromL, fromC, fromC, toL, accessibleCells))
				break;
		}
		
		for(int toC = fromC-1 ; toC >= 0 ; toC--) {

			if(cantAccess(fromL, fromC, toC, fromL, accessibleCells))
				break;
		}
		
		for(int toC = fromC+1 ; toC < colAmount ; toC++) {

			if(cantAccess(fromL, fromC, toC, fromL, accessibleCells))
				break;
		}
		
		return accessibleCells;
	}
	
	private boolean cantAccess(int fromL, int fromC, int toC, int toL, List<Couple<Integer, Integer>> accessibleCells) {
		CellContent fromCellContent = grid[fromL][fromC];
		CellContent toCellContent = grid[toL][toC];
		
		if(toCellContent == CellContent.GATE || (isTheKingPlace(toC, toL) && toCellContent == CellContent.EMPTY)) {
			if(fromCellContent == CellContent.KING) {
				accessibleCells.add(new Couple<Integer, Integer>(toL, toC));
			}
			return false;
		}
		
		if(toCellContent != CellContent.EMPTY) return true;
		
		accessibleCells.add(new Couple<Integer, Integer>(toL, toC));
		
		return false;
	}
	
	public void towerTaker_attack(int l, int c) {
		towerTaker_attack_core(l-1,c,-1,0);
		towerTaker_attack_core(l+1,c,1,0);
		towerTaker_attack_core(l,c-1,0,-1);
		towerTaker_attack_core(l,c+1,0,1);
	}
	
	public void towerTaker_defense(int l, int c) {
		towerTaker_defense_core(l-1,c,-1,0);
		towerTaker_defense_core(l+1,c,1,0);
		towerTaker_defense_core(l,c-1,0,-1);
		towerTaker_defense_core(l,c+1,0,1);
	}
	
	// Prendre en compte les murs et portes
	public void towerTaker_defense_core(int l, int c, int dl, int dc){
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

	// Faire l'attaque du roi + prendre en compte les murs et portes
	public void towerTaker_attack_core(int l, int c, int dl, int dc){
		if((isDefenseTower(l,c) || isTheKing(l,c)) && isADefenseAllied(l+dl, c+dc)) {
			System.out.println("Entrée dans isSurrounded");
			if(isSurrounded(null,l,c) || isSurrounded(new Couple<Integer, Integer>(l, c),l+dl,c+dc)) {
				setContent(CellContent.EMPTY,l,c);
				setContent(CellContent.EMPTY,l+dl,c+dc);
			}
		}
		else if(isDefenseTower(l, c)){
			if(isAttackTower(l+dl, c+dc)){
				setContent(CellContent.EMPTY,l,c);
			}
		}
		else if(isTheKing(l,c)){
			if(isSurrounded(null,l,c)) {
				setContent(CellContent.EMPTY,l,c);
			}
		}
			 

		}

	public Couple<Integer, Integer> checkneighbour_attack(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int counter_enemy, int i, int j){
		if(isADefenseAllied(i,j)) {
			counter_obstacle++;
			counter_enemy++;
		}
		else if(isOccupied(i, j)){
			allyCells.add(new Couple<Integer, Integer>(i, j));
			counter_obstacle++;
		}
		return new Couple<Integer, Integer>(counter_obstacle, counter_enemy);
	}
	
	public Couple<Integer, Integer> checkneighbour_defense(List<Couple<Integer, Integer>> allyCells, int counter_obstacle, int counter_enemy, int i, int j){
		if(isAnAttackAllied(i,j)) {
			counter_obstacle++;
			counter_enemy++;
		}
		else if(isOccupied(i, j)){
			allyCells.add(new Couple<Integer, Integer>(i, j));
			counter_obstacle++;
		}
		return new Couple<Integer, Integer>(counter_obstacle, counter_enemy);
	}
	
	public boolean isSurrounded(Couple<Integer, Integer> c, int i, int j) {
		List<Couple<Integer, Integer>> allyCells = new ArrayList<>();
		boolean surrounded = true;
		int counter_obstacle = 0;
		int counter_enemy = 0;
		Couple<Integer, Integer> nb = new Couple<Integer, Integer>(counter_obstacle, counter_enemy);
		switch(grid[i][j]) {
			case DEFENSE_TOWER:
			case KING:

				nb = checkneighbour_defense(allyCells, nb.getFirst(), nb.getSecond(), i-1, j);
				nb = checkneighbour_defense(allyCells, nb.getFirst(), nb.getSecond(), i+1, j);
				nb = checkneighbour_defense(allyCells, nb.getFirst(), nb.getSecond(), i, j-1);
				nb = checkneighbour_defense(allyCells, nb.getFirst(), nb.getSecond(), i, j+1);
				
				break;

			case ATTACK_TOWER:
				
				nb = checkneighbour_attack(allyCells, nb.getFirst(), nb.getSecond(), i-1, j);
				nb = checkneighbour_attack(allyCells, nb.getFirst(), nb.getSecond(), i+1, j);
				nb = checkneighbour_attack(allyCells, nb.getFirst(), nb.getSecond(), i, j-1);
				nb = checkneighbour_attack(allyCells, nb.getFirst(), nb.getSecond(), i, j+1);
				
				break;
				
			default:
				
				break;	
		}
		
		if(nb.getFirst() == 4) {
			System.out.println("nb obstacles : "+nb.getFirst());
			System.out.println("nb enemy : "+nb.getSecond());
			if(nb.getSecond() == 4) {
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
	

	

	
	public void setContent(CellContent cellContent, int l, int c) {
		if (cellContent == CellContent.KING) {
			kingC = c;
			kingL = l;
		}
		grid[l][c] = cellContent;
	}

	public PlayerEnum getWinner() {
		return winner;
	}
	
	private PlayerEnum checkWin(CellContent previousToCellContent, CellContent toCellContent) {
		if(winner != null) return winner;
		
		if(!isTheKing(kingL,kingC)) // Si le roi a été tué
			return player;
		else if(previousToCellContent == CellContent.GATE && toCellContent == CellContent.KING) // Si le roi s'est déplacé sur une porte
			return player;
		
		return null;
	}
	
	
	private void clear(int l, int c) {
		grid[l][c] = CellContent.EMPTY;
		
	}

	public void setDefenseTower(int l, int c) {
		grid[l][c] = CellContent.DEFENSE_TOWER;
	}
	
	public void setAttackTower(int l, int c) {
		grid[l][c] = CellContent.ATTACK_TOWER;
	}
	
	public void setKing(int l, int c) {
		grid[l][c] = CellContent.KING;
		kingC = c;
		kingL = l;
	}
	
	public void setGate(int l, int c) {
		grid[l][c] = CellContent.GATE;
	}
	
	public boolean isAWall(int i, int j) {
		return i == rowAmount - 1 || j == colAmount - 1;
	}
	
	public boolean isTheKingPlace(int i, int j) {
		return i == middle && j == middle;
	}
	
	
	
	public boolean isADefenseAllied(int i, int j) {
		if(isAWall(i,j)|| isTheKingPlace(i,j) || isAttackTower(i,j) || isGate(i,j))
			return false;
		else return isDefenseTower(i, j) || isTheKing(i, j);
	}
	
	public boolean isAnAttackAllied(int i, int j) {
		if(isAWall(i,j) ||isDefenseTower(i,j) || isTheKing(i,j)|| isTheKingPlace(i,j) ||  isGate(i,j)) {
			return false;
		}
		else return isAttackTower(i, j);
	}
	
	public boolean isOccupied(int l, int c) {
		if(!isValid(l, c)) return true;
		
		return grid[l][c] != CellContent.EMPTY;
	}

	public boolean isTheKing(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return grid[l][c] == CellContent.KING;
	}
	
	public boolean isAttackTower(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return grid[l][c] == CellContent.ATTACK_TOWER;
	}
	
	public boolean  isDefenseTower(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return grid[l][c] == CellContent.DEFENSE_TOWER;
	}
	
	public boolean isGate(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return grid[l][c] == CellContent.GATE;
	}
	
	
	public int getRowAmout() {
		return this.rowAmount;
	}
	
	public int getColAmout() {
		return this.colAmount;
	}
	
	public CellContent getCellContent(int l, int c) {
		return this.grid[l][c];
	}
	
	public boolean isValid(int l, int c) {
		return !(l < 0 || c < 0 || l >= rowAmount || c >= colAmount);
	}
	
	public PlayerEnum getPlayer() {
		return this.player;
	}
	
	public boolean canPlay(int l, int c) {
		if(this.player == PlayerEnum.ATTACKER) {
			return this.getCellContent(l, c) == CellContent.ATTACK_TOWER;
		}
		
		return this.getCellContent(l, c) == CellContent.DEFENSE_TOWER || this.getCellContent(l, c) == CellContent.KING;
	}
}
