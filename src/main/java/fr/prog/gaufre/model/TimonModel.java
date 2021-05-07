package fr.prog.gaufre.model;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class TimonModel implements Model {
	public short[][] grille;
	int x;
	int y;
	boolean fini = false;
	private short playing_player;
	private Queue<Play> last_plays;

	public TimonModel(int x, int y) {
		this.x = x;
		this.y = x;
		this.newGame();
	}
	
	public void affiche() {
		for(int j = 0; j < y; j++) {
			for(int i = 0; i < x; i++) {
				System.out.print(grille[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public boolean play(int c, int l) {
		if(fini) return false;
		if(grille[c][l] == 1) return false;
		if(c < 0 || c >= x || y < 0 || l >= y) return false;

		Play play = new Play();

		for(int i = c; i < x; i++) for(int j = l; j < y; j++) {
			if(grille[i][j] == 0)
				play.addCouple(i, j);

			grille[i][j] = 1;
		}

		this.last_plays.add(play);

		if(check_end()) {
			System.out.println("Le joueur " + this.playing_player + " a gagné");
			fini = true;
			return true;
		}

		this.playing_player = (short) (this.playing_player % 2 + 1); // If playing_player == 1 then playing_player = 2 else playing_player = 1

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

	public boolean newGame() {
		this.last_plays = new ArrayDeque();
		this.grille = new short[x][y];
		this.playing_player = 1;

		return true;
	}

	public boolean rollback() {
		if(this.fini) return false;

		return true;
	}

	public boolean save() {
		return true;
	}
}
