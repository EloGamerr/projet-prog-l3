package fr.prog.gaufre.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import fr.prog.gaufre.controller.IA;

public class Model implements Cloneable {
	public short[][] grille;
	int x;
	int y;
	boolean finished = false;
	private short playing_player;
	private Deque<Couple<Integer, Integer>> players_actions;
	private Stack<Play> last_plays;
	private IA ia;

	public Model(int x, int y) {
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

	public boolean play(int c, int l, int playerNumber) {
		if(c < 0 || c >= x || y < 0 || l >= y) return false;
		if(finished) return false;
		if(c == 0 && l == 0) return false;
		if(grille[c][l] == 1) return false;
		if(playerNumber != this.playing_player) return false;

		Play play = new Play();

		for(int i = c; i < x; i++) for(int j = l; j < y; j++) {
			if(grille[i][j] == 0)
				play.addCouple(i, j);

			grille[i][j] = 1;
		}
		try {
			this.players_actions.add(new Couple<Integer,Integer>(c,l));
		}catch (Exception e) {
			System.err.println(e);
		}
		this.last_plays.add(play);

		if(check_end()) {
			if(this.ia != null) {
				if(this.ia.getPlayerNumber() == this.playing_player) {
					System.out.println("L'IA a gagné");
				}
				else {
					System.out.println("Le joueur a gagné");
				}
			}
			else {
				System.out.println("Le joueur " + this.playing_player + " a gagné");
			}
			
			finished = true;
			return true;
		}

		this.playing_player = this.getWaitingPlayer();

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
		this.finished = false;
		
		return true;
	}

	public boolean rollback() {
		if(this.finished || last_plays.isEmpty()) return false;
		int decider = 0;
		if(this.ia == null) {
			decider = 1;
			this.playing_player = getWaitingPlayer();
		}
		else {
			decider = 2;
		}
		for(int i = 0; i < decider; i++) {
			Play last_play = last_plays.pop();
			players_actions.pollLast();
			for(Couple<Integer,Integer> cp : last_play.getCouples()) {
				this.grille[cp.getFirst()][cp.getSecond()] = 0;
			}
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
			return false;
		}
		return true;
	}
	
	public boolean load() {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/saves"));
		int result = filechooser.showOpenDialog(new JFrame());
		if(result != JFileChooser.APPROVE_OPTION) {
			return false;
		}
		this.newGame();
		File selectedfile = filechooser.getSelectedFile();
		try {
			Scanner reader = new Scanner(selectedfile);
			String current_line;
			while (reader.hasNextLine()) {
				current_line = reader.nextLine();
				int c_saved = 0,l_saved = 0;
				int counter = 0;
				if(current_line != "\n") {
					for(String split_character : current_line.split(" ")) {
						if(counter == 0) {
							c_saved = Integer.parseInt(split_character);
						}
						else {
							l_saved = Integer.parseInt(split_character);
						}
						counter++;
					}
					this.play(c_saved, l_saved, this.playing_player);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
		return true;
	}
	
	public List<Couple<Integer, Integer>> playableCells() {
		return this.playableCells(new Couple<Integer, Integer>(0, 0));
	}
	
	public List<Couple<Integer, Integer>> playableCells(Couple<Integer, Integer> leftTopCorner) {
		List<Couple<Integer, Integer>> coords = new ArrayList<>();
		
		for(int i = leftTopCorner.getFirst(); i < x; i++) for(int j = leftTopCorner.getSecond(); j < y; j++) {
			if(this.grille[i][j] == 0 && (i != 0 || j != 0))
				coords.add(new Couple<Integer, Integer>(i, j));
		}
		
		return coords;
	}
	
	public IA getIA() {
		return this.ia;
	}
	
	public void setIA(IA ia) {
		this.ia = ia;
	}
	
	public short getPlayingPlayer() {
		return this.playing_player;
	}
	
	public short getWaitingPlayer() {
		return (short) (this.playing_player % 2 + 1); // If playing_player == 1 then return 2 else return 1
	}
	
	public Model clone() {
		Model model = new Model(x, y);
		model.grille = new short[x][y];
		
		for(int i = 0 ; i < x ; i++) {
			model.grille[i] = grille[i].clone();
		}
		
		model.finished = finished;
		model.playing_player = playing_player;
		model.players_actions = new ArrayDeque<>(players_actions);
		model.last_plays = (Stack<Play>) last_plays.clone();
		//dont set IA
		return model;
	}
}
