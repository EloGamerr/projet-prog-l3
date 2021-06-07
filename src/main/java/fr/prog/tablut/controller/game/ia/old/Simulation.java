package fr.prog.tablut.controller.game.ia.old;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.structures.Couple;

public class Simulation {

	private CellContent[][] grid;
	private List<Point> whiteCells = new ArrayList<>();
	private List<Point> blackCells = new ArrayList<>();
	private Point king;

	public Simulation(CellContent[][] grid) {
		this.grid = grid;
		
		for (int l = 0; l < 9; l++) {
			for (int c = 0; c < 9; c++) {

				if (grid[l][c] == CellContent.ATTACK_TOWER) {
					blackCells.add(new Point(c, l));
				}
				else if (grid[l][c] == CellContent.DEFENSE_TOWER) {
					whiteCells.add(new Point(c, l));
				}
				else if (grid[l][c] == CellContent.KING) {
					king = new Point(c, l);
					whiteCells.add(king);
				}

			}
		}
	}

	private void deplacer(int fromC, int fromL, int toC, int toL) {
		grid[toC][toL] = grid[fromC][fromL];
		grid[fromC][fromL] = CellContent.EMPTY;
	}

	/**
	 * 
	 * @return La liste de tout les coup jouables en fonction des pions
	 */
	private List<Couple<Point, List<Point>>> coupJouable() {
		List<Couple<Point, List<Point>>> allPosibility = new ArrayList<Couple<Point, List<Point>>>();

		for (Point pion : whiteCells) {
			List<Point> listPossibility = new ArrayList<Point>();

			// Parcourir toutes les cases possibles sur 4 directions pour trouver les coups
			// possibles

			// Liste de toutes les possibilitées pour les pions blancs
			// Parcours a droite du pion
			for (int i = pion.y; i < 8; i++) {
				if (grid[pion.x][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			// Parcours en haut du pion
			for (int i = pion.x; i < 8; i++) {
				if (grid[i][pion.y] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			// Parcours a gauche du pion
			for (int i = pion.y; i > 8; i--) {
				if (grid[pion.x][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			// Parcours en bas du pion
			for (int i = pion.x; i > 8; i--) {
				if (grid[i][pion.y] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			Couple<Point, List<Point>> possibility = new Couple<Point, List<Point>>(pion, listPossibility);

			allPosibility.add(possibility);
		}

		// Liste de toutes les possibilitée pour les pions noirs
		for (Point pion : blackCells) {
			List<Point> listPossibility = new ArrayList<Point>();

			// Parcourir toutes les cases possibles sur 4 directions pour trouver les coups
			// possibles

			// Liste de toutes les possibilitées pour les pions blancs
			// Parcours a droite du pion
			for (int i = pion.y; i < 8; i++) {
				if (grid[pion.x][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			// Parcours en haut du pion
			for (int i = pion.x; i < 8; i++) {
				if (grid[i][pion.y] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			// Parcours a gauche du pion
			for (int i = pion.y; i > 8; i--) {
				if (grid[pion.x][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			// Parcours en bas du pion
			for (int i = pion.x; i > 8; i--) {
				if (grid[i][pion.y] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Point(pion.x, i));
			}

			Couple<Point, List<Point>> possibility = new Couple<Point, List<Point>>(pion, listPossibility);

			allPosibility.add(possibility);
		}

		return allPosibility;
	}
}
