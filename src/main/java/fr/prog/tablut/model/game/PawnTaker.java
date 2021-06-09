package fr.prog.tablut.model.game;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.structures.Couple;

public class PawnTaker {
	private final List<Point> visited = new ArrayList<>(); // Variable globale qui sert à connaître les pions alliés et
													 // connexes qui ont déjà été traité par les fonctions de la classe
	private final Game game;
	
	
	////////////////////////////////////////////////////
	// Constructor
	////////////////////////////////////////////////////	
	
	PawnTaker(Game game) {
		this.game = game;
	}
	
	
	////////////////////////////////////////////////////
	// Main function
	////////////////////////////////////////////////////

	/**
	* Méthode principale
	* Vérifie si des pions ont étés pris en fonction de la nature de la case déplacée 
 	*/
	public void clearTakedPawns(int c, int l, Play play) {
		if(game.isAttackTower(c, l))
			attack(c, l, play);

		if(game.isDefenseTower(c, l))
			defense(c, l, play);
		
	}
	
	////////////////////////////////////////////////////
	// Attack 
	////////////////////////////////////////////////////
	
	/**
	* Regarde aux extremités de la case en question
 	*/
	public void attack(int c, int l, Play play) {
		attack_core(c,   l-1,  0,-1, play);
		attack_core(c,   l+1,  0, 1, play);
		attack_core(c-1, l,-1, 0,    play);
		attack_core(c+1, l, 1, 0,    play);
	}
	
	public void attack_core(int c, int l, int dc, int dl, Play play){
		if(game.isDefenseTower(c, l)) { // Si la case contient une tour défensive
			if(isAttTowerHelper(c+dc, l+dl)) { // Si deux cases plus loin nous avons un allié de circonstance pour l'attaque
				play.putModifiedOldCellContent(new Point(c, l), game.getCellContent(c, l));
				game.setContent(CellContent.EMPTY, c, l); // on enleve le pion ennemi
				play.putModifiedNewCellContent(new Point(c, l), CellContent.EMPTY);
			}
			else 
				testSurround(c, l, play);  // Sinon on vérifie que le coup joué  bloque totalement le pion ennemi
		}
		else if(game.isTheKing(c, l)) // Si la case contient le roi
				testSurround(c, l,  play);  // On vérifie que le coup joué  bloque totalement le roi ennemi
	}
	
	////////////////////////////////////////////////////
	// Defense 
	////////////////////////////////////////////////////
	/**
	* Regarde aux extremités de la case en question
 	*/
	public void defense(int c, int l, Play play) {
		defense_core(c,   l-1,  0,-1, play);
		defense_core(c,   l+1,  0, 1, play);
		defense_core(c-1, l,-1, 0,    play);
		defense_core(c+1, l, 1, 0,    play);
	}
	
	public void defense_core(int c, int l, int dc, int dl, Play play) {
		if(game.isAttackTower(c, l)) {
			if(isDefTowerHelper(c+dc, l+dl)) { // Si deux cases plus loin nous avons un allié de circonstance pour la défense
				play.putModifiedOldCellContent(new Point(c, l), game.getCellContent(c, l));
				game.setContent(CellContent.EMPTY, c, l); // on enleve le pion ennemi
				play.putModifiedNewCellContent(new Point(c, l), CellContent.EMPTY);
			}
			else  testSurround(c, l, play); // Sinon on vérifie que le coup joué  bloque totalement le pion ennemi		
		}
	}
	
	////////////////////////////////////////////////////
	// Surround test 
	////////////////////////////////////////////////////
	/**
	* Vérifie si la case passée en paramètre est entourée d'obstacle inamicaux
 	*/
	public void testSurround(int c, int l,  Play play) {
		if(isSurrounded(new Point(c, l), c, l)) { // Si le pion est encerclé
			play.putModifiedOldCellContent(new Point(c, l), game.getCellContent(c, l));
			game.setContent(CellContent.EMPTY,c, l); // On enléve le pion
			play.putModifiedNewCellContent(new Point(c, l), CellContent.EMPTY);
			
			if(!visited.isEmpty()) { // Si des pions alliés ont étés traités par la classe
				for(Point cell : visited) {
					play.putModifiedOldCellContent(cell, game.getCellContent(cell.x, cell.y));
					game.setContent(CellContent.EMPTY,cell.x, cell.y); // // On les enleves également
					play.putModifiedOldCellContent(cell, CellContent.EMPTY);
				}
			}
		}

		visited.clear();
	}
	/**
	* Va chercher le nombre d'obstacle et le nombre d'alliés connexes à la case x,y
	* va vérifier si la case est encerclée et fera de même avec ses alliés connexes si c'est le cas
	* retourne vrai si encerclée et faux sinon
 	*/
	public boolean isSurrounded(Point c, int x, int y) {
		List<Point> allyCells = new ArrayList<>();
		boolean surrounded = true;
		int counter_obstacle = 0;
		Couple<Integer , List<Point>> var = new Couple<>(counter_obstacle, allyCells);
		
		switch(game.getGrid()[y][x]) {
		
			case KING:
				var = checkneighbour_king(var.getSecond(), var.getFirst(), x-1, y);
				var = checkneighbour_king(var.getSecond(), var.getFirst(), x+1, y);
				var = checkneighbour_king(var.getSecond(), var.getFirst(), x,   y-1);
				var = checkneighbour_king(var.getSecond(), var.getFirst(), x,   y+1);
				break;
			case DEFENSE_TOWER:
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), x-1, y);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), x+1, y);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), x,   y-1);
				var = checkneighbour_defense(var.getSecond(), var.getFirst(), x,   y+1);
				break;
			

			case ATTACK_TOWER:
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), x-1, y);
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), x+1, y);
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), x,   y-1);
				var = checkneighbour_attack(var.getSecond(), var.getFirst(), x,   y+1);
				break;
				
			default: break;	
		}
		
		counter_obstacle = var.getFirst();
		
		if(counter_obstacle == 4) { // Le pion ne peut plus se d�placer
			if(var.getSecond().size() != 0) { // Si il a des alli�s autour de lui, on v�rifie si eux m�mes sont bloqu�s 
				if(!visited.contains(c)) {
					visited.add(c);
				}
				
				for(Point cell : visited) {
					allyCells.remove(cell);
				}
				
				for(Point cell : allyCells) {
					if(!isSurrounded(new Point(cell.x, cell.y), cell.x, cell.y)) {
						surrounded = false;
					}
				}
				
				return surrounded;	
			}
			
			return true; 
		}
		
		return false;		
	}

	
	////////////////////////////////////////////////////
	// Check content functions
	////////////////////////////////////////////////////

	/**
	* regarde si la case  connexe  x,y est un obstacle ou un allié et complète les compteurs passées en paramètre
 	*/

	public Couple<Integer, List<Point>> checkneighbour_attack(List<Point> allyCells, int counter_obstacle, int x, int y) {
		if(!game.isValid(x, y))
			counter_obstacle++;

		else if(isDefTowerHelper(x, y))
			counter_obstacle++;

		else if(game.isAttackTower(x, y)) {
			allyCells.add(new Point(x, y));
			counter_obstacle++;
		}

		return new Couple<>(counter_obstacle, allyCells);
	}
	
	public Couple<Integer, List<Point>> checkneighbour_king(List<Point> allyCells, int counter_obstacle, int x, int y) {
		if(!game.isValid(x, y))
			counter_obstacle++;

		else if(isAttTowerHelper_king(x, y))
			counter_obstacle++;	

		else if(isDefenseAlly(x, y)) {
			allyCells.add(new Point(x, y));
			counter_obstacle++;
		}

		return new Couple<>(counter_obstacle, allyCells);
	}

	public Couple<Integer, List<Point>> checkneighbour_defense(List<Point> allyCells, int counter_obstacle, int x, int y) {
		if(!game.isValid(x, y))
			counter_obstacle++;

		else if(isAttTowerHelper(x, y))
			counter_obstacle++;	

		else if(isDefenseAlly(x, y)) {
			allyCells.add(new Point(x, y));
			counter_obstacle++;
		}

		return new Couple<>(counter_obstacle, allyCells);
	}
	


	public boolean isDefTowerHelper(int x, int y) {
		return (game.isOccupied(x, y) && (game.isDefenseTower(x, y) || game.isGate(x, y)));
	}
	public boolean isAttTowerHelper(int x, int y) {
		return (game.isOccupied(x, y) && (game.isAttackTower(x, y) || game.isGate(x, y)));
	}
	public boolean isAttTowerHelper_king(int x, int y) {
		return (!game.isValid(x, y) || game.isTheKingPlace(x, y) || game.isAttackTower(x, y) || game.isGate(x, y));
	}
	
	public boolean isDefenseAlly(int x ,int y) {
		return (game.isTheKing(x, y) || game.isDefenseTower(x, y));
	}
}
