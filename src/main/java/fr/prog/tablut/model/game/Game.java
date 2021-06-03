package fr.prog.tablut.model.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.saver.GameLoader;
import fr.prog.tablut.model.saver.GameSaver;
import fr.prog.tablut.structures.Couple;

public class Game {
	private static Game instance = null;

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

	private boolean paused = false;
	
	////////////////////////////////////////////////////
	// Constructor
	////////////////////////////////////////////////////



	public Game() {
		this.middle = 4;
		gameSaver = new GameSaver(this);
		loader = new GameLoader(this);
	}

	public static Game getInstance() {
		if(instance == null) {
			instance = new Game();
		}

		return instance;
	}

	/**
	 * Start a new game
	 */
	public void start(PlayerTypeEnum attacker, PlayerTypeEnum defender) {
		this.attacker = attacker.createPlayer();
		this.defender = defender.createPlayer();
		setPlayingPlayer(PlayerEnum.ATTACKER);
		this.move = new PawnTaker(this);
		this.plays = new Plays();
		init_game(9,9);
		setWinner(PlayerEnum.NONE);
		hasStarted = true;
	}

	/**
	 * Load a new game. All properties of the current game are lost if not saved.
	 * @param index_selected Save index (>= 0 and < amount of saves)
	 */
	public void load(int index_selected) {
		init_grid(9, 9);
		this.move = new PawnTaker(this);
		this.plays = new Plays();
		loader.loadData(index_selected);
		hasStarted = true;
	}

	/**
	 * Move a pawn with checks.
	 * @param l Row index on which we must remove the pawn
	 * @param c Column index on which we must remove the pawn
	 * @param toL Row index on which we must put the pawn
	 * @param toC Column index on which we must put the pawn
	 * @return True if the pawn was moved, false otherwise
	 */
	public boolean move(int l, int c, int toL, int toC) {
		System.out.println("Tour joué");
		if(isWon() || !isValid(toL, toC) || (toL != l && toC != c))
			return false;

		CellContent fromCellContent = getGrid()[l][c];

		if(fromCellContent == CellContent.EMPTY || fromCellContent == CellContent.GATE)
			return false;

		List<Couple<Integer, Integer>> accessibleCells = getAccessibleCells(l, c);

		if(!accessibleCells.contains(new Couple<>(toL, toC)))
			return false;

		Play play = plays.move(l, c, toL, toC);

		play.putModifiedOldCellContent(new Couple<>(l, c), getCellContent(l, c));
		setContent(CellContent.EMPTY, l, c);
		play.putModifiedNewCellContent(new Couple<>(l, c), CellContent.EMPTY);
		
		l = toL;
		c = toC;
		
		CellContent previousToCellContent = getCellContent(l, c);

		play.putModifiedOldCellContent(new Couple<>(l, c), previousToCellContent);
		setContent(fromCellContent, l, c);
		play.putModifiedNewCellContent(new Couple<>(l, c), fromCellContent);
		
		move.clearTakedPawns(l, c, play);
		
		setWinner(checkWin(previousToCellContent, fromCellContent));
		playingPlayerEnum = playingPlayerEnum.getOpponent();
		
		return true;
	}

	/**
	 * Undo the last move
	 * @return True if undo was done, false otherwise
	 */
	public boolean undo_move() {
		
		Play play = this.getPlays().undo_move();

		if(play != null) {
			for(Map.Entry<Couple<Integer, Integer>, CellContent> entry : play.getModifiedOldCellContents().entrySet()) {
				this.setContent(entry.getValue(), entry.getKey().getFirst(), entry.getKey().getSecond());
			}

			this.playingPlayerEnum = this.getPlayingPlayerEnum().getOpponent();
			setPaused(true);
			return true;
		}

		return false;
	}

	/**
	 * Redo the last move which was undone
	 * @return True if redo was done, false otherwise
	 */
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

	/**
	 * Initializing grid with pawns
	 */
	public void init_game(int rowAmount, int colAmount) {
		this.init_grid(rowAmount, colAmount);

		init_king();
		init_gates();
		init_towers();
	}

	/**
	 * Initializing grid with empty cells
	 */
	public void init_grid(int rowAmount, int colAmount) {
		setGrid(new CellContent[rowAmount][colAmount]);

		for(int i = 0 ; i < rowAmount; i++) {
			Arrays.fill(getGrid()[i], CellContent.EMPTY);
		}
		
		this.rowAmount = rowAmount;
		this.colAmount = colAmount;
	}
	
	private void init_king() {
		setKing(middle, middle);
	}

	private void init_towers() {
		init_attackTowers();
		init_defenseTowers();
	}

	private void init_defenseTowers() {
		setDefenseTower(middle-1, middle);
		setDefenseTower(middle-2, middle);
		setDefenseTower(middle+1, middle);
		setDefenseTower(middle+2, middle);
		
		setDefenseTower(middle, middle-1);
		setDefenseTower(middle, middle-2);
		setDefenseTower(middle, middle+1);
		setDefenseTower(middle, middle+2);
	}

	private void init_attackTowers() {
		setAttackTower(middle, 0);
		setAttackTower(middle, colAmount-1);
		setAttackTower(0, middle);
		setAttackTower(rowAmount-1, middle);
		
		setAttackTower(middle-1, 0);
		setAttackTower(middle-1, colAmount-1);
		setAttackTower(0, middle-1);
		setAttackTower(rowAmount-1, middle-1);
		
		setAttackTower(middle+1, 0);
		setAttackTower(middle+1, colAmount-1);
		setAttackTower(0, middle+1);
		setAttackTower(rowAmount-1, middle+1);
		
		setAttackTower(middle, 1);
		setAttackTower(middle, colAmount-2);
		setAttackTower(1, middle);
		setAttackTower(rowAmount-2, middle);
	}

	private void init_gates() {
		setGate(0, 0);
		setGate(rowAmount-1, 0);
		setGate(0, colAmount-1);
		setGate(rowAmount-1, colAmount-1);
	}


	/**
	 * Check if a pawn can't access to a cell, the following rules must be followed :
	 * <p>
	 * - Towers can't go on a gate
	 * <p>
	 * - Towers can't go on the king throne
	 * <p>
	 * - Pawns can't go on a cell which is already occupied
	 * @return True if the pawn can't access to the cell, otherwise, a couple of the cell coord is added to the param accessibleCells and false is returned
	 */
	private boolean cantAccess(int fromL, int fromC, int toL, int toC, List<Couple<Integer, Integer>> accessibleCells) {
		CellContent fromCellContent = getGrid()[fromL][fromC];
		CellContent toCellContent = getGrid()[toL][toC];
		
		if(toCellContent == CellContent.GATE || (isTheKingPlace(toC, toL) && toCellContent == CellContent.EMPTY)) {
			if(fromCellContent == CellContent.KING) {
				accessibleCells.add(new Couple<Integer, Integer>(toL, toC));
			}

			return false;
		}
		
		if(toCellContent != CellContent.EMPTY)
			return true;
		
		accessibleCells.add(new Couple<Integer, Integer>(toL, toC));
		
		return false;
	}

	public boolean canPlay(int l, int c) {
		if(this.playingPlayerEnum == PlayerEnum.ATTACKER) {
			return this.getCellContent(l, c) == CellContent.ATTACK_TOWER;
		}
		
		return this.getCellContent(l, c) == CellContent.DEFENSE_TOWER || this.getCellContent(l, c) == CellContent.KING;
	}
	
	/**
	 * We first check if someone already won, if not, then we check the following rules :
	 * <p>
	 * - If the king was killed, the attacker won
	 * - If the king went on a gate cell, the defender won
	 * @param previousToCellContent previous cell content of the cell in which the pawn was moved in the last play
	 * @param toCellContent new cell content of the cell in which the pawn was moved in the last play
	 * @return the PlayerEnum winner (PlayerEnum.NONE if no one won)
	 */
	private PlayerEnum checkWin(CellContent previousToCellContent, CellContent toCellContent) {
		if(this.getWinner() != PlayerEnum.NONE) return winner;
		
		// If the king has been killed
		if(!isTheKing(kingL,kingC))
			return playingPlayerEnum;

		// If the king is on a gate
		else if(previousToCellContent == CellContent.GATE && toCellContent == CellContent.KING)
			return playingPlayerEnum;
		
		return PlayerEnum.NONE;
	}
	
	public boolean isTheKingPlace(int i, int j) {
		return i == middle && j == middle;
	}

	/**
	 * @return True if the cell content is not empty, false otherwise
	 */
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
	
	public boolean isValid(int l, int c) {
		return !(l < 0 || c < 0 || l >= rowAmount || c >= colAmount);
	}
	
	public boolean isWon() {
		return winner != PlayerEnum.NONE;
	}
	
	public boolean hasStarted() {
		return hasStarted;
	}
	
	public boolean isPaused() {
		return paused ;
	}
	
	////////////////////////////////////////////////////
	// Getters & Setters
	////////////////////////////////////////////////////

	/**
	 * @return The winner, equals PlayerEnum.NONE if the game is not won
	 */
	public PlayerEnum getWinner() {
		return winner;
	}
	
	public int getRowAmout() {
		return this.rowAmount;
	}
	
	public int getColAmout() {
		return this.colAmount;
	}
	
	public GameSaver getGameSaver() {
		return gameSaver;
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
	
	public CellContent[][] getGrid() {
		return grid;
	}
	
	public String getCurrentSavePath() {
		return currentSavePath;
	}

	public PlayerEnum getPlayingPlayerEnum() {
		return this.playingPlayerEnum;
	}
	
	public CellContent getCellContent(int l, int c) {
		return this.getGrid()[l][c];
	}
	
	public List<Couple<Integer, Integer>> getAccessibleCells(int fromL, int fromC) {
		List<Couple<Integer, Integer>> accessibleCells = new ArrayList<>();

		for(int toL = fromL-1 ; toL >= 0 ; toL--) {
            if(cantAccess(fromL, fromC, toL, fromC , accessibleCells)) break;
        }

        for(int toL = fromL+1 ; toL < rowAmount ; toL++) {
            if(cantAccess(fromL, fromC, toL, fromC, accessibleCells)) break;
        }

        for(int toC = fromC-1 ; toC >= 0 ; toC--) {
            if(cantAccess(fromL, fromC, fromL, toC, accessibleCells)) break;
        }
		
        for(int toC = fromC+1 ; toC < colAmount ; toC++) {
            if(cantAccess(fromL, fromC, fromL, toC, accessibleCells)) break;
        }

		return accessibleCells;
	}

	/**
	 * @param cellContent CellContent to search
	 * @return All cells of the grid that are equals to the param cellContent
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
	
	/**
	 * @param cellContent CellContent to be set in the target cell
	 * @param l Row of the target cell
	 * @param c Column of the target cell
	 */
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
	
	public void setGrid(CellContent[][] grid) {
		this.grid = grid;
	}
	
	public void setCurrentSavePath(String currentSavePath) {
		this.currentSavePath = currentSavePath;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	
}
