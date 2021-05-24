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
	private PlayerEnum player;
	private Move move;
	
	public Game(){
		this.middle = 4;
		this.player = PlayerEnum.ATTACKER;
		this.move = new Move(this);
		init_game(9,9);
	}

	void init_game(int rowAmount, int colAmount) {
		setGrid(new CellContent[rowAmount][colAmount]);
		for(int i = 0 ; i < rowAmount ; i++) {
			Arrays.fill(getGrid()[i], CellContent.EMPTY);
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

		CellContent fromCellContent = getGrid()[l][c];
		if(fromCellContent == CellContent.EMPTY || fromCellContent == CellContent.GATE) return false;

		List<Couple<Integer, Integer>> accessibleCells = accessibleCells(l, c);
		if(!accessibleCells.contains(new Couple<Integer, Integer>(toL, toC))) return false;

		Play result = new Play();
		
		clear(l, c);
		result.move(l, c, toL, toC);
		l = toL;
		c = toC;
		CellContent previousToCellContent = getGrid()[l][c];
		setContent(fromCellContent, l, c);
		
		if(isAttackTower(l, c)) {
			move.towerTaker_attack(l,c);

		}
		if(isDefenseTower(l, c)) {
			move.towerTaker_defense(l,c);
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
		CellContent fromCellContent = getGrid()[fromL][fromC];
		CellContent toCellContent = getGrid()[toL][toC];
		
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
	
	
	

	

	
	public void setContent(CellContent cellContent, int l, int c) {
		if (cellContent == CellContent.KING) {
			kingC = c;
			kingL = l;
		}
		getGrid()[l][c] = cellContent;
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
		getGrid()[l][c] = CellContent.EMPTY;
		
	}

	public void setDefenseTower(int l, int c) {
		getGrid()[l][c] = CellContent.DEFENSE_TOWER;
	}
	
	public void setAttackTower(int l, int c) {
		getGrid()[l][c] = CellContent.ATTACK_TOWER;
	}
	
	public void setKing(int l, int c) {
		getGrid()[l][c] = CellContent.KING;
		kingC = c;
		kingL = l;
	}
	
	public void setGate(int l, int c) {
		getGrid()[l][c] = CellContent.GATE;
	}
	
	
	public boolean isTheKingPlace(int i, int j) {
		return i == middle && j == middle;
	}
	
	

	
	public boolean isOccupied(int l, int c) {
		if(!isValid(l, c)) return true;
		
		return getGrid()[l][c] != CellContent.EMPTY;
	}

	public boolean isTheKing(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return getGrid()[l][c] == CellContent.KING;
	}
	
	public boolean isAttackTower(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return getGrid()[l][c] == CellContent.ATTACK_TOWER;
	}
	
	public boolean  isDefenseTower(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return getGrid()[l][c] == CellContent.DEFENSE_TOWER;
	}
	
	public boolean isGate(int l, int c) {
		if(!isValid(l, c)) return false;
		
		return getGrid()[l][c] == CellContent.GATE;
	}
	
	
	public int getRowAmout() {
		return this.rowAmount;
	}
	
	public int getColAmout() {
		return this.colAmount;
	}
	
	public CellContent getCellContent(int l, int c) {
		return this.getGrid()[l][c];
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

	public CellContent[][] getGrid() {
		return grid;
	}

	public void setGrid(CellContent[][] grid) {
		this.grid = grid;
	}
}
