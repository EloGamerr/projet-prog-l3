package fr.prog.tablut.model.game;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.prog.tablut.model.game.player.Player;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.model.game.player.PlayerTypeEnum;
import fr.prog.tablut.model.saver.GameLoader;

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
	private String currentSavePath = "";
	public GameLoader loader;
	
	private String attackerName = "";


	private String defenderName = "";


	private boolean paused = false;
	private long startTime;
	
	////////////////////////////////////////////////////
	// Constructor
	////////////////////////////////////////////////////



	public Game() {
		this.middle = 4;
		loader = new GameLoader(this);
	}

	public static Game getInstance() {
		if(instance == null) {
			instance = new Game();
		}

		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}

	/**
	 * Start a new game
	 */
	public void start(PlayerTypeEnum attacker, PlayerTypeEnum defender, String attackerName, String defenderName) {
		this.attacker = attacker.createPlayer(PlayerEnum.ATTACKER);
		this.defender = defender.createPlayer(PlayerEnum.DEFENDER);
		setNames(attacker, defender, attackerName, defenderName);
		setPlayingPlayer(PlayerEnum.ATTACKER);
		this.move = new PawnTaker(this);
		this.plays = new Plays();
		init_game(9,9);
		setWinner(PlayerEnum.NONE);
		hasStarted = true;
		paused = false;
		this.startTime = System.currentTimeMillis();
	}
	
	public void restart() {
		this.attacker.getOwnedCells().clear();
		this.defender.getOwnedCells().clear();
		setPlayingPlayer(PlayerEnum.ATTACKER);
		this.plays = new Plays();
		init_game(9,9);
		setWinner(PlayerEnum.NONE);
		hasStarted = true;
		paused = false;
		this.startTime = System.currentTimeMillis();
	}


	/**
	 * Load a new game. All properties of the current game are lost if not saved.
	 * @param index_selected Save index (&gt;= 0 and &lt; amount of saves)
	 */
	public void load(int index_selected) {
		this.move = new PawnTaker(this);
		this.plays = new Plays();
		loader.loadData(index_selected);
		hasStarted = true;
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * Move a pawn with checks.
	 * @param l Row index on which we must remove the pawn
	 * @param c Column index on which we must remove the pawn
	 * @param toL Row index on which we must put the pawn
	 * @param toC Column index on which we must put the pawn
	 * @return True if the pawn was moved, false otherwise
	 */
	public boolean move(int c, int l, int toC, int toL) {
		if(!canMove(c, l, toC, toL)) return false;

		CellContent fromCellContent = getGrid()[l][c];

		Play play = plays.move(c, l, toC, toL);

		play.putModifiedOldCellContent(new Point(c, l), getCellContent(c, l));
		setContent(CellContent.EMPTY, c, l);
		play.putModifiedNewCellContent(new Point(c, l), CellContent.EMPTY);
		
		l = toL;
		c = toC;
		
		CellContent previousToCellContent = getCellContent(c, l);

		play.putModifiedOldCellContent(new Point(c, l), previousToCellContent);
		setContent(fromCellContent, c, l);
		play.putModifiedNewCellContent(new Point(c, l), fromCellContent);
		
		move.clearTakedPawns(c, l, play);
		
		setWinner(checkWin(previousToCellContent, fromCellContent));
		playingPlayerEnum = playingPlayerEnum.getOpponent();
		
		return true;
	}

	public boolean canMove(int c, int l, int toC, int toL) {
		if(isWon() || !isValid(toC, toL) || (toL != l && toC != c))
			return false;

		CellContent fromCellContent = getGrid()[l][c];

		if(fromCellContent == CellContent.EMPTY || fromCellContent == CellContent.GATE)
			return false;

		if(!isPlayingPlayerOwningCell(c, l))
			return false;

		List<Movement> accessibleCells = getAllPossibleMovesForPosition(c, l);

		if(!accessibleCells.contains(new Movement(c, l, toC, toL)))
			return false;

		return true;
	}

	/**
	 * Undo the last move
	 * @return True if undo was done, false otherwise
	 */
	public boolean undo_move() {
		Play play = this.getPlays().undo_move();

		if(play != null) {
			for(Map.Entry<Point, CellContent> entry : play.getModifiedOldCellContents().entrySet()) {
				this.setContent(entry.getValue(), entry.getKey().x, entry.getKey().y);
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
			for(Map.Entry<Point, CellContent> entry : play.getModifiedNewCellContents().entrySet()) {
				this.setContent(entry.getValue(), entry.getKey().x, entry.getKey().y);
			}

			this.playingPlayerEnum = this.getPlayingPlayerEnum().getOpponent();
			if(PlayerTypeEnum.getFromPlayer(this.getAttacker()).isAI() && PlayerTypeEnum.getFromPlayer(this.getDefender()).isAI())
				setPaused(true);
			return true;
		}

		return false;
	}

    public boolean hasPreviousMove() {
        return !getPlays().getPreviousMovements().isEmpty();
    }

    public boolean hasNextMove() {
        return !getPlays().getNextMovements().isEmpty();
    }

	/**
	 * Initializing grid with pawns
	 */
	public void init_game(int colAmount, int rowAmount) {
		this.init_grid(colAmount, rowAmount);

		init_king();
		init_gates();
		init_towers();
	}

	/**
	 * Initializing grid with empty cells
	 */
	public void init_grid(int colAmount, int rowAmount) {
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
		setAttackTower(middle, rowAmount-1);
		setAttackTower(0, middle);
		setAttackTower(colAmount-1, middle);
		
		setAttackTower(middle-1, 0);
		setAttackTower(middle-1, rowAmount-1);
		setAttackTower(0, middle-1);
		setAttackTower(colAmount-1, middle-1);
		
		setAttackTower(middle+1, 0);
		setAttackTower(middle+1, rowAmount-1);
		setAttackTower(0, middle+1);
		setAttackTower(colAmount-1, middle+1);
		
		setAttackTower(middle, 1);
		setAttackTower(middle, rowAmount-2);
		setAttackTower(1, middle);
		setAttackTower(colAmount-2, middle);
	}

	private void init_gates() {
		setGate(0, 0);
		setGate(colAmount-1, 0);
		setGate(0, rowAmount-1);
		setGate(colAmount-1, rowAmount-1);
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
	private boolean cantAccess(int fromC, int fromL, int toC, int toL, List<Movement> moves) {
		CellContent fromCellContent = getGrid()[fromL][fromC];
		CellContent toCellContent = getGrid()[toL][toC];

		if(toCellContent == CellContent.GATE || (isTheKingPlace(toC, toL) && toCellContent == CellContent.EMPTY)) {
			if(fromCellContent == CellContent.KING) {
				moves.add(new Movement(fromC, fromL, toC, toL));
			}

			return false;
		}

		if(toCellContent != CellContent.EMPTY)
			return true;

		moves.add(new Movement(fromC, fromL, toC, toL));

		return false;
	}

	/**
	 * This method calculate if a pawn can move from a cell,
	 * don't use this method if you know the end cell of the move,
	 * use canMove(int l, int c, int toL, int toC) instead
	 * @return True if the cell has an empty cell on her row or on her column
	 * and if the cell content is owned by the playingPlayerEnum
	 */
	public boolean canMove(int c, int l) {
		if(isWon())
			return false;

		if(!isPlayingPlayerOwningCell(c, l)) return false;

		return this.isNotBlocked(c, l);
	}

	/**
	 *
	 * @return True if the cell content is owned by the playingPlayerEnum
	 */
	public boolean isPlayingPlayerOwningCell(int c, int l) {
		if(this.playingPlayerEnum == PlayerEnum.ATTACKER && this.getCellContent(c, l) != CellContent.ATTACK_TOWER) return false;

		if(this.playingPlayerEnum == PlayerEnum.DEFENDER && this.getCellContent(c, l) != CellContent.DEFENSE_TOWER && this.getCellContent(c, l) != CellContent.KING) return false;

		return true;
	}

	/**
	 * This method is a variant of the getAccessibleCells() method, but it has
	 * better performances (in the case where the pawn on the cell is not blocked)
	 * and the return is only a boolean. Use this method only
	 * if you don't need the list of the accessible cells
	 * @return True if the cell has an empty cell on her row or on her column
	 */
	public boolean isNotBlocked(int fromC, int fromL) {
		List<Movement> accessibleCells = new ArrayList<>();

		for(int toL = fromL-1 ; toL >= 0 ; toL--) {
			if(cantAccess(fromC, fromL, fromC, toL, accessibleCells)) break;

			if(!accessibleCells.isEmpty()) return true;
		}

		for(int toL = fromL+1 ; toL < rowAmount ; toL++) {
			if(cantAccess(fromC, fromL, fromC, toL, accessibleCells)) break;

			if(!accessibleCells.isEmpty()) return true;
		}

		for(int toC = fromC-1 ; toC >= 0 ; toC--) {
			if(cantAccess(fromC, fromL, toC, fromL,  accessibleCells)) break;

			if(!accessibleCells.isEmpty()) return true;
		}

		for(int toC = fromC+1 ; toC < colAmount ; toC++) {
			if(cantAccess(fromC, fromL, toC, fromL, accessibleCells)) break;

			if(!accessibleCells.isEmpty()) return true;
		}

		return !accessibleCells.isEmpty();
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
		if(!isTheKing(kingC, kingL))
			return playingPlayerEnum;

		// If the king is on a gate
		else if(previousToCellContent == CellContent.GATE && toCellContent == CellContent.KING)
			return playingPlayerEnum;
		
		return PlayerEnum.NONE;
	}
	
	public boolean isTheKingPlace(int x, int y) {
		return x == middle && y == middle;
	}

	/**
	 * @return True if the cell content is not empty, false otherwise
	 */
	public boolean isOccupied(int c, int l) {
		if(!isValid(c, l)) return true;
		return getGrid()[l][c] != CellContent.EMPTY;
	}

	public boolean isTheKing(int c, int l) {
		if(!isValid(c, l)) return false;
		return getGrid()[l][c] == CellContent.KING;
	}
	
	public boolean isAttackTower(int c, int l) {
		if(!isValid(c, l)) return false;
		return getGrid()[l][c] == CellContent.ATTACK_TOWER;
	}
	
	public boolean  isDefenseTower(int c, int l) {
		if(!isValid(c, l)) return false;
		return getGrid()[l][c] == CellContent.DEFENSE_TOWER;
	}
	
	public boolean isGate(int c, int l) {
		if(!isValid(c, l)) return false;
		return getGrid()[l][c] == CellContent.GATE;
	}
	
	public boolean isValid(int c, int l) {
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
	
	public int getRowAmount() {
		return this.rowAmount;
	}
	
	public int getColAmount() {
		return this.colAmount;
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

	public Player getPlayer(PlayerEnum playerEnum) {
		return playerEnum == PlayerEnum.ATTACKER ? attacker : defender;
	}

	public Plays getPlays() {
		return plays;
	}

	public Movement getPlayAt(int index) {
		if(index > -1 && plays.getPlays().size() > index)
		    return plays.getPlays().get(index).getMovement();
        return null;
	}
	
	public Movement getLastPlay() {
        return getPlayAt(plays.getPlays().size() - 1);
	}

	public Movement getCurrentLastPlay() {
		return getPlayAt(plays.getCurrentMovement());
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
	
	public String getDefenderName() {
		return defenderName;
	}
	
	public String getAttackerName() {
		return attackerName;
	}

	public CellContent getCellContent(int c, int l) {
		return this.getGrid()[l][c];
	}

	public List<Movement> getAllPossibleMoves() {
		return getAllPossibleMoves(getPlayingPlayer());
	}

	public List<Movement> getAllPossibleMoves(Player player) {
		List<Movement> moves = new ArrayList<>();

		for(Point cell : player.getOwnedCells()) {
			moves.addAll(this.getAllPossibleMovesForPosition(cell.x, cell.y));
		}

		return moves;
	}

	public List<Movement> getAllPossibleMovesForPosition(int fromC, int fromL) {
		List<Movement> moves = new ArrayList<>();

		for(int toL = fromL-1; toL >= 0; toL--) {
			if(cantAccess(fromC, fromL, fromC, toL, moves)) break;
		}

		for(int toL = fromL+1; toL < rowAmount; toL++) {
			if(cantAccess(fromC, fromL, fromC, toL, moves)) break;
		}

		for(int toC = fromC-1; toC >= 0; toC--) {
			if(cantAccess(fromC, fromL, toC, fromL, moves)) break;
		}

		for(int toC = fromC+1; toC < colAmount; toC++) {
			if(cantAccess(fromC, fromL, toC, fromL, moves)) break;
		}

		return moves;
	}

	/**
	 * @param cellContent CellContent to search
	 * @return All cells of the grid that are equals to the param cellContent
	 */
	public List<Point> getCellContentWhereEquals(CellContent cellContent) {
		List<Point> cells = new ArrayList<>();

		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] == cellContent) {
					cells.add(new Point(j, i));
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
	public void setContent(CellContent cellContent, int c, int l) {
		if (cellContent == CellContent.KING) {
			kingC = c;
			kingL = l;
		}

		if(getGrid()[l][c] == CellContent.ATTACK_TOWER) {
			attacker.getOwnedCells().remove(new Point(c, l));
		}

		else if(getGrid()[l][c] == CellContent.DEFENSE_TOWER || getGrid()[l][c] == CellContent.KING) {
			defender.getOwnedCells().remove(new Point(c, l));
		}
		
		if(cellContent == CellContent.ATTACK_TOWER) {
			attacker.getOwnedCells().add(new Point(c, l));
		}
		
		else if(cellContent == CellContent.DEFENSE_TOWER || cellContent == CellContent.KING) {
			defender.getOwnedCells().add(new Point(c, l));
		}
	
		getGrid()[l][c] = cellContent;
	}
	
	public void setWinner(PlayerEnum winner) {
		this.winner = winner;
	}
	
	public void setPlayingPlayer(PlayerEnum playingPlayer) {
		this.playingPlayerEnum = playingPlayer;
	}
	
	public void setDefenseTower(int c, int l) {
		setContent(CellContent.DEFENSE_TOWER, c, l);
	}
	
	public void setAttackTower(int c, int l) {
		setContent(CellContent.ATTACK_TOWER, c, l);
	}
	
	public void setDefender(Player defender) {
		this.defender = defender;
	}
	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}
	
	public void setKing(int c, int l) {
		setContent(CellContent.KING, c, l);
	}
	
	public void setGate(int c, int l) {
		setContent(CellContent.GATE, c, l);
	}
	
	public void setGrid(CellContent[][] grid) {
		this.grid = grid;
	}

	public void setCurrentSavePath(String currentSavePath) {
		this.currentSavePath = currentSavePath;
	}
	
	public void setPaused(boolean paused) {
		boolean pause = PlayerTypeEnum.getFromPlayer(Game.getInstance().getAttacker()).isAI() && PlayerTypeEnum.getFromPlayer(Game.getInstance().getDefender()).isAI();

		if(pause)
			pause = paused;

		this.paused = pause;
	}
	
	public void setNames(PlayerTypeEnum attacker, PlayerTypeEnum defender, String attackerName, String defenderName) {
		if(attacker.isAI())
			setAttackerName(attacker.toString());
		else
			setAttackerName(attackerName);

		if(defender.isAI())
			setDefenderName(defender.toString());
		else
			setDefenderName(defenderName);
	}
	
	public void setDefenderName(String defenderName) {
		this.defenderName = defenderName;	
	}

	public void setAttackerName(String attackerName) {
		this.attackerName = attackerName;	
    }

	public long getStartTime() {
		return startTime;
	}

	public int getDuration() {
		return (int) (System.currentTimeMillis()-getStartTime());
	}

	public void setKingL(int kingL) {
		this.kingL = kingL;
	}

	public void setKingC(int kingC) {
		this.kingC = kingC;
	}

    public int getMovementsNumber() {
        return getPlays().getCurrentMovement() + 1;
    }
}
