package fr.prog.tablut.model;


public class Game {

	final int attackTower = 1;
	final int defenseTower = 2;
	final int king = 4;
	final int gate = 8;
	Model model;
	int lines, columns;
	int [][] grid;
	int middle;
	int kingL, kingC;
	boolean isWon = false;
	
	Game(Model model){
		this.model = model;
		init_game(model.rowAmount(),model.colAmount());
	}

	void init_game(int rowAmount, int colAmount) {
		grid = new int[rowAmount+1][colAmount+1];
		lines = rowAmount;
		columns = colAmount;
		middle = (lines+1)/2;
		init_king();
		init_gates();
		init_towers();
	}

	void init_king() {
		addKing(middle,middle);
	}

	void init_towers() {
		init_attackTowers();
		init_defenseTowers();
	}

	void init_defenseTowers() {
		addDefenseTower(middle,middle);

		addDefenseTower(middle-1,middle);
		addDefenseTower(middle-2,middle);
		addDefenseTower(middle+1,middle);
		addDefenseTower(middle+2,middle);
		
		addDefenseTower(middle,middle-1);
		addDefenseTower(middle,middle-2);
		addDefenseTower(middle,middle+1);
		addDefenseTower(middle,middle+2);
	}
	
	void init_attackTowers() {
		addAttackTower(middle,0);
		addAttackTower(middle,columns);
		addAttackTower(0,middle);
		addAttackTower(columns,middle);
		
		addAttackTower(middle-1,0);
		addAttackTower(middle-1,columns);
		addAttackTower(0,middle-1);
		addAttackTower(columns,middle-1);
		
		addAttackTower(middle+1,0);
		addAttackTower(middle+1,columns);
		addAttackTower(0,middle+1);
		addAttackTower(columns,middle+1);
		
		addAttackTower(middle,1);
		addAttackTower(middle,columns-1);
		addAttackTower(1,middle);
		addAttackTower(columns-1,middle);
	}
	
	void init_gates() {
		addGate(0,0);
		addGate(lines,0);
		addGate(0,columns);
		addGate(lines,columns);
	}

	Play move(int l, int c, int dL, int dC) {
		Play result = new Play();
		int toL = l+dL;
		int toC = c+dC;
		int id = grid[l][c];
		
		suppr(id, l, c);
		result.move(l, c, toL, toC);
		l = toL;
		c = toC;
		add(id, l, c);
		return result;
	}

	
	void add(int id, int l, int c) {
		if (id == king) {
			if(isGate(l,c)) {
				suppr(gate,l,c);
				isWon = true;
			}
			kingC = c;
			kingL = l;
		}
		grid[l][c] |=  id;
	}

	public boolean isLost() {
		if(!isTheKing(kingL,kingC)) 
			return true;
		else
			return false;
	}
	
	
	
	private void suppr(int id, int l, int c) {
		grid[l][c] &= ~id;
		
	}

	void addDefenseTower(int l, int c) {
		grid[l][c] = defenseTower;
	}
	
	void addAttackTower(int l, int c) {
		grid[l][c] = attackTower;
	}
	
	void addKing(int l, int c) {
		grid[l][c] = king;
		kingC = c;
		kingL = l;
	}
	
	void addGate(int l, int c) {
		grid[l][c] = gate;
	}
	
	void makeEmpty(int i, int j) {
		grid[i][j] = 0;
	}
	
	boolean isFree(int l, int c) {
		return grid[l][c] == 0;
	}

	boolean isTheKing(int l, int c) {
		return grid[l][c] == king;
	}
	
	boolean isAttackTower(int l, int c) {
		return grid[l][c] == attackTower;
	}
	
	boolean  isDefenseTower(int l, int c) {
		return grid[l][c] == defenseTower;
	}
	
	boolean isGate(int l, int c) {
		return grid[l][c] == gate;
	}
	
	
}
