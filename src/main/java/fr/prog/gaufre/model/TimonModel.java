package fr.prog.gaufre.model;

public abstract class TimonModel implements Model {
	public short[][] grille;
	int x;
	int y;
	boolean fini = false;
	
	public TimonModel(int x, int y) {
		grille = new short[x][y];
		this.x = x;
		this.y = x;
		create_grid();
	}
	
	public void affiche() {
		for(int j = 0; j < y; j++) {
			for(int i = 0; i < x; i++) {
				System.out.print(grille[i][j]);
			}
			System.out.println();
		}
	}
	
	private void create_grid() {
		for(int i = 0; i < x ; i++) {
			for(int j = 0; j < y; j++) {
				grille[i][j] = 0;
			}
		}
	}
	
	public void reset() {
		create_grid();
	}

	@Override
	public boolean play(int c, int l) {
		if(fini) return false;
		if(grille[c][l] == 1) return false;

		for(int i = c; i < x; i++) for(int j = l; j < y; j++) {
			grille[i][j] = 1;
		}

		if(check_end()) {
			System.out.println("Le joueur qui vient de cliquer a gagné");
			fini = true;
		}

		return true;
	}

	private boolean check_end() {
		for(int i = 0; i < x; i++) for(int j = 0; j < y; j++) {
			if(i == 0 && j == 0) continue;

			if(grille[i][j] == 0) {
				return false;
			}
		}
		return true;
	}

	public short[][] get_grid() {
		return grille;
	}
	
	public int get_x() {
		return x;
	}
	
	public int get_y() {
		return y;
	}
}
