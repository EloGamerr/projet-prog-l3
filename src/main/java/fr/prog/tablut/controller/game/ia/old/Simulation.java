package fr.prog.tablut.controller.game.ia.old;

import java.util.ArrayList;
import java.util.List;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.structures.Couple;

public class Simulation {

	private CellContent[][] grid;
	private List<Couple<Integer, Integer>> whiteCells = new ArrayList<>();
	private List<Couple<Integer, Integer>> blackCells = new ArrayList<>();
	private Couple<Integer, Integer> king;

	public Simulation(CellContent[][] grid) {
		this.grid = grid;
		
		for (int l = 0; l < 9; l++) {
			for (int c = 0; c < 9; c++) {

				if (grid[l][c] == CellContent.ATTACK_TOWER) {
					blackCells.add(new Couple<Integer, Integer>(l, c));
				}
				else if (grid[l][c] == CellContent.DEFENSE_TOWER) {
					whiteCells.add(new Couple<Integer, Integer>(l, c));
				}
				else if (grid[l][c] == CellContent.KING) {
					king = new Couple<Integer, Integer>(l, c);
					whiteCells.add(king);
				}

			}
		}
	}

	private void deplacer(int fromL, int fromC, int toL, int toC) {
		grid[toL][toC] = grid[fromL][fromC];
		grid[fromL][fromC] = CellContent.EMPTY;
	}

	/**
	 * 
	 * @return La liste de tout les coup jouables en fonction des pions
	 */
	private List<Couple<Couple<Integer, Integer>, List<Couple<Integer, Integer>>>> coupJouable() {
		List<Couple<Couple<Integer, Integer>, List<Couple<Integer, Integer>>>> allPosibility = new ArrayList<Couple<Couple<Integer, Integer>, List<Couple<Integer, Integer>>>>();

		for (Couple<Integer, Integer> pion : whiteCells) {
			List<Couple<Integer, Integer>> listPossibility = new ArrayList<Couple<Integer, Integer>>();

			// Parcourir toutes les cases possibles sur 4 directions pour trouver les coups
			// possibles

			// Liste de toutes les possibilitées pour les pions blancs
			// Parcours a droite du pion
			for (int i = pion.getFirst(); i < 8; i++) {
				if (grid[i][pion.getSecond()] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			// Parcours en haut du pion
			for (int i = pion.getSecond(); i < 8; i++) {
				if (grid[pion.getFirst()][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			// Parcours a gauche du pion
			for (int i = pion.getFirst(); i > 8; i--) {
				if (grid[i][pion.getSecond()] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			// Parcours en bas du pion
			for (int i = pion.getSecond(); i > 8; i--) {
				if (grid[pion.getFirst()][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			Couple<Couple<Integer, Integer>, List<Couple<Integer, Integer>>> possibility = new Couple<Couple<Integer, Integer>, List<Couple<Integer, Integer>>>(pion, listPossibility);

			allPosibility.add(possibility);
		}

		// Liste de toutes les possibilitée pour les pions noirs
		for (Couple<Integer, Integer> pion : blackCells) {
			List<Couple<Integer, Integer>> listPossibility = new ArrayList<Couple<Integer, Integer>>();

			// Parcourir toutes les cases possibles sur 4 directions pour trouver les coups
			// possibles

			// Liste de toutes les possibilitées pour les pions blancs
			// Parcours a droite du pion
			for (int i = pion.getFirst(); i < 8; i++) {
				if (grid[i][pion.getSecond()] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			// Parcours en haut du pion
			for (int i = pion.getSecond(); i < 8; i++) {
				if (grid[pion.getFirst()][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			// Parcours a gauche du pion
			for (int i = pion.getFirst(); i > 8; i--) {
				if (grid[i][pion.getSecond()] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			// Parcours en bas du pion
			for (int i = pion.getSecond(); i > 8; i--) {
				if (grid[pion.getFirst()][i] != CellContent.EMPTY) {
					break;
				}

				listPossibility.add(new Couple<Integer, Integer>(i, pion.getSecond()));
			}

			Couple<Couple<Integer, Integer>, List<Couple<Integer, Integer>>> possibility = new Couple<Couple<Integer, Integer>, List<Couple<Integer, Integer>>>(pion, listPossibility);

			allPosibility.add(possibility);
		}

		return allPosibility;
	}
}
