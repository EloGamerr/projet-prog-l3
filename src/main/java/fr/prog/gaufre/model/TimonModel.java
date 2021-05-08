package fr.prog.gaufre.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;
import java.util.Stack;

public abstract class TimonModel implements Model {
	public short[][] grille;
	int x;
	int y;
	boolean fini = false;
	private short playing_player;
	private Queue<Couple<Integer, Integer>> players_actions;
	private Stack<Play> last_plays;

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
		try {
			this.players_actions.add(new Couple<Integer,Integer>(c,l));
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
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
		this.last_plays = new Stack<Play>();
		this.players_actions = new ArrayDeque<Couple<Integer, Integer>>();
		this.grille = new short[x][y];
		this.playing_player = 1;

		return true;
	}

	public boolean rollback() {
		if(this.fini || last_plays.isEmpty()) return false;
		Play last_play = last_plays.pop();
		players_actions.poll();
		for(Couple<Integer,Integer> cp : last_play.getCouples()) {
			this.grille[cp.getFirst()][cp.getSecond()] = 0;
		}
		
		return true;
	}

	public boolean save() {
		String file_name = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SSS").format(new Date());
		try {
			File f = new File("saves/" + file_name + ".save");
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			for(Couple<Integer,Integer> cp : players_actions) {
				fw.write(String.format("%d %d\n", cp.getFirst(),cp.getSecond()));
			}
			fw.close();
		}
		catch(IOException e) {
			System.err.println("Failed to create the file...");
		}
		return true;
	}
	
	public boolean load() {
		return true;
	}
}
