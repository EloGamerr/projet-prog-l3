package fr.prog.tablut.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.prog.tablut.model.saver.GameLoader;
import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.structures.Couple;

public class Game {
	private int rowAmount, colAmount;
	private CellContent[][] grid;
	private final int middle;
	private int kingL, kingC;
	private PlayerEnum winner;
	private PlayerEnum playingPlayerEnum;
	private PawnTaker move;
	private Player attacker;
	private Player defender;
	private Plays plays;
	private boolean hasStarted;
	private GameSaver gameSaver;
	private String currentSavePath = "";
	public GameLoader loader;
	


	public Game(){
		this.middle = 4;
		
	}
	
	public void start(Player attacker, Player defender) {
		this.attacker = attacker;
		this.defender = defender;
		setPlayingPlayer(PlayerEnum.ATTACKER);
		this.move = new PawnTaker(this);
		init_game(9,9);
		this.plays = new Plays(this);
		setWinner(PlayerEnum.NONE);
		hasStarted = true;
		loader = new GameLoader(this);
		this.gameSaver = new GameSaver(this);
	}
	
	public void load(int index_selected) {
		init_grid(9,9);
		this.move = new PawnTaker(this);
		hasStarted = true;
		this.plays = new Plays(this);
		loader = new GameLoader(this);
		gameSaver = new GameSaver(this);
		loader.loadData(index_selected);
	}
	
	public boolean hasStarted() {
		return hasStarted;
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

	public void init_grid(int rowAmount, int colAmount) {

		setGrid(new CellContent[rowAmount][colAmount]);
		for(int i = 0 ; i < rowAmount ; i++) {
			Arrays.fill(getGrid()[i], CellContent.EMPTY);
		}
		
		this.rowAmount = rowAmount;
		this.colAmount = colAmount;
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
		if(isWon()) return false;
		
		if(!isValid(toL, toC)) return false;

		if(toL != l && toC != c) return false; // Déplacements en diagonal non autorisés

		CellContent fromCellContent = getGrid()[l][c];
		if(fromCellContent == CellContent.EMPTY || fromCellContent == CellContent.GATE) return false;

		List<Couple<Integer, Integer>> accessibleCells = getAccessibleCells(l, c);
		if(!accessibleCells.contains(new Couple<>(toL, toC))) return false;

		Play play = plays.move(l, c, toL, toC);
		play.putModifiedOldCellContent(new Couple<>(l, c), getCellContent(l, c));
		clear(l, c);
		play.putModifiedNewCellContent(new Couple<>(l, c), CellContent.EMPTY);
		l = toL;
		c = toC;
		CellContent previousToCellContent = getCellContent(l, c);
		play.putModifiedOldCellContent(new Couple<>(l, c), previousToCellContent);
		setContent(fromCellContent, l, c);
		play.putModifiedNewCellContent(new Couple<>(l, c), fromCellContent);
		
		if(isAttackTower(l, c)) {
			move.towerTaker_attack(l,c, play);
		}
		if(isDefenseTower(l, c)) {
			move.towerTaker_defense(l,c, play);
		}
		setWinner(checkWin(previousToCellContent, fromCellContent));
		if(isWon()) {
			System.out.println(winner + " a gagné !");
		}
		else {
			playingPlayerEnum = playingPlayerEnum.getOpponent();
		}
		return true;
	}


	public List<Couple<Integer, Integer>> getAccessibleCells(int fromL, int fromC) {
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
		if(getGrid()[l][c] == CellContent.ATTACK_TOWER) {
			attacker.getOwnedCells().remove(new Couple<>(l, c));
		}
		else if(getGrid()[l][c] == CellContent.DEFENSE_TOWER || getGrid()[l][c] == CellContent.KING) {
			defender.getOwnedCells().remove(new Couple<>(l, c));
		}
		
		if(cellContent == CellContent.ATTACK_TOWER) {
			attacker.getOwnedCells().add(new Couple<>(l, c));
		}
		else if(cellContent == CellContent.DEFENSE_TOWER || cellContent == CellContent.KING) {
			defender.getOwnedCells().add(new Couple<>(l, c));
		}
	
		getGrid()[l][c] = cellContent;
	}

	public PlayerEnum getWinner() {
		return winner;
	}
	
	private PlayerEnum checkWin(CellContent previousToCellContent, CellContent toCellContent) {
		if(isWon()) return winner;
		
		if(!isTheKing(kingL,kingC)) // Si le roi a été tué
			return playingPlayerEnum;
		else if(previousToCellContent == CellContent.GATE && toCellContent == CellContent.KING) // Si le roi s'est déplacé sur une porte
			return playingPlayerEnum;
		
		return PlayerEnum.NONE;
	}
	
	
	private void clear(int l, int c) {
		setContent(CellContent.EMPTY, l, c);
		
	}
	
	public void setWinner(PlayerEnum winner) {
		this.winner = winner;
	}
	
	public void setPlayingPlayer(PlayerEnum playingPlayer) {
		this.playingPlayerEnum = playingPlayer;
	}
	
	public void setDefenseTower(int l, int c) {
		setContent(CellContent.DEFENSE_TOWER, l, c);
	}
	
	public void setAttackTower(int l, int c) {
		setContent(CellContent.ATTACK_TOWER, l, c);
	}
	
	public void setDefender(Player defender) {
		this.defender = defender;
	}
	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}
	
	public void setKing(int l, int c) {
		setContent(CellContent.KING, l, c);
	}
	
	public void setGate(int l, int c) {
		setContent(CellContent.GATE, l, c);
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
	
	public PlayerEnum getPlayingPlayerEnum() {
		return this.playingPlayerEnum;
	}

	public boolean canPlay(int l, int c) {
		if(this.playingPlayerEnum == PlayerEnum.ATTACKER) {
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

	/**
	 * @param cellContent CellContent to search
	 * @return All cells of the grid that are equals to the param
	 */
	public List<Couple<Integer, Integer>> getCellContentWhereEquals(CellContent cellContent) {
		List<Couple<Integer, Integer>> cells = new ArrayList<>();
		for(int i = 0 ; i < grid.length ; i++) {
			for(int j = 0 ; j < grid[i].length ; j++) {
				if(grid[i][j] == cellContent) {
					cells.add(new Couple<>(i, j));
				}
			}
		}

		return cells;
	}

	public int getKingL() {
		return kingL;
	}

	public int getKingC() {
		return kingC;
	}
	
	public Player getAttacker() {
		return attacker;
	}

	public Player getDefender() {
		return defender;
	}

	public Player getPlayingPlayer() {
		return this.getPlayingPlayerEnum() == PlayerEnum.ATTACKER ? attacker : defender;
	}
	
	public Plays getPlays() {
		return plays;
	}


	public boolean isWon() {
		return this.getWinner() != PlayerEnum.NONE;
	}
	
	public String getCurrentSavePath() {
		return currentSavePath;
	}

	public void setCurrentSavePath(String currentSavePath) {
		this.currentSavePath = currentSavePath;
	}
	public boolean undo_move() {
		Play play = this.getPlays().undo_move();
		if(play != null) {
			for(Map.Entry<Couple<Integer, Integer>, CellContent> entry : play.getModifiedOldCellContents().entrySet()) {
				this.setContent(entry.getValue(), entry.getKey().getFirst(), entry.getKey().getSecond());
			}

			this.playingPlayerEnum = this.getPlayingPlayerEnum().getOpponent();
			return true;
		}

		return false;
	}

	public boolean redo_move() {
		Play play = this.getPlays().redo_move();
		if(play != null) {
			for(Map.Entry<Couple<Integer, Integer>, CellContent> entry : play.getModifiedNewCellContents().entrySet()) {
				this.setContent(entry.getValue(), entry.getKey().getFirst(), entry.getKey().getSecond());
			}

			this.playingPlayerEnum = this.getPlayingPlayerEnum().getOpponent();
			return true;
		}

		return false;

	}
}
