package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAMedium extends AIPlayer {
	Random random;
	public IAMedium(PlayerEnum playerEnum) {
		super(playerEnum);

		random = new Random();
	}

	@Override
	public String toString() {
		return "AIMedium";
	}

	@Override
	public boolean play(Game game) {
		Simulation simulation = new Simulation(game);
		Movement movement = minimaxDecision(simulation.getAllPossibleMoves(), simulation);
		game.move(movement.getFromL(), movement.getFromC(), movement.getToL(), movement.getToC());

		return true;
	}

	/**
	 * This is the core method for selecting which move to play.
	 * It follows the pseudo-code for minimaxDecision shown in the class slides.
	 *
	 * It implements represents the initial branching of the current node, processing
	 * each of the available moves and then calling minimaxValue() to evaluate the value
	 * of the move by traversing the game state tree using minimax with alpha-beta pruning.
	 */
	public Movement minimaxDecision(List<Movement> moves, Simulation boardState) {
		// Keeps track of when the move was started in order to avoid timeouts
		long moveStartTime = System.currentTimeMillis();

		// Stores the value of every possible move for the current board state.
		double[] moveValue = new double [moves.size()];

		// This was found to be the max depth value which causes minimal timeouts
		int maxDepth = 3;

		/*
		 * Iterate through all possible moves and assign a value to each of them using mimimax
		 * (with ? - ? pruning) and the evaluation function.
		 */
		int curMoveIdx = 0;
		for (Movement curMove: moves) {

			// Evaluating a move to depth 3 takes up to 60ms in most cases, thus if we reach 1940ms, return the best move found thus far
			/*if (System.currentTimeMillis() - moveStartTime > 1940) {
				return moves.get(getHighestValueMove(moveValue));
			}*/

			// Clone the board state and apply the move to obtain the new game state and evaluate it using minimax
			Simulation clonedBoardState = (Simulation) boardState.clone();
			clonedBoardState.move(curMove.fromL, curMove.fromC, curMove.toL, curMove.toC); // apply the operator o and obtain the new game state s.

			// Shortcut which exits if we find a winning move.
			if (clonedBoardState.getWinner() == this.getPlayerEnum()) {
				return curMove;
			}

			// start with alpha & beta = -10K/10K and initial depth 1
			moveValue[curMoveIdx] = minimaxValue(clonedBoardState, -10000, 10000, 1, maxDepth); // Value[o] = MinimaxValue(s)
			curMoveIdx++;
		}

		//return the operator with the highest value Value[o] by finding its index in the moves list
		List<Integer> highestValueMoves = getHighestValueMoves(moveValue);
		return moves.get(highestValueMoves.get(random.nextInt(highestValueMoves.size())));
	}

	/**
	 * Helper method to iterate through the moveValue array and return
	 * the index of the highest valued move.
	 */
	public List<Integer> getHighestValueMoves(double [] moveValue) {
		double maxValue = moveValue[0];
		List<Integer> maxIdx = new ArrayList<>();
		maxIdx.add(0);
		for(int i = 1; i < moveValue.length; i++) {
			if (moveValue[i] > maxValue) {
				maxIdx.clear();
				maxValue = moveValue[i];
				maxIdx.add(i);
			}
			else if(moveValue[i] == maxValue) {
				maxIdx.add(i);
			}
		}
		return maxIdx;
	}

	/**
	 * This implementation of the MiniMaxValue() method shown in class
	 * is a hybrid between the pseudocode of minimax and the code for alpha beta pruning,
	 * as this implementation contains alpha beta pruning.
	 *
	 * It traverses the game state tree by generating successor states recursively and evaluating
	 * "leaf" nodes when the maximum intended depth is reached.
	 */
	public double minimaxValue(Simulation boardState, double alpha, double beta, int depth, int maxDepth) {
		// if isTerminal(s), return Utility(s) based on board state
		if (boardState.isWon()) {
			PlayerEnum winner = boardState.getWinner();
			if (winner == this.getPlayerEnum()) {
				return 50000;
			} else if (winner == this.getPlayerEnum().getOpponent()) {
				return -50000;
			} else {
				// In the case of a draw we return 0 because we'll have reached 100 moves and all other leaves will either be win, loss, or draw
				return 0;
			}
		} else if (depth == maxDepth) { // If we've reached the maximum decided depth, evaluate and return this value

			//System.out.println("test3");
			return MyTools.evaluation(boardState, this.getPlayerEnum());
		} else { // Otherwise, continue to generate and explore the search tree
			List<Simulation> successors = getSuccessors(boardState);

			if (this.getPlayerEnum() == boardState.getPlayingPlayerEnum()) { // if Max player is to move in s, return maxs’ Value(s’).
				for (Simulation sucState: successors) { // for each state s’ in Successors(s)
					// let ? = max { ?, MinValues(s’,?,?) }.
					alpha = Math.max(alpha, minimaxValue(sucState, alpha, beta, depth + 1, maxDepth)); // let Value(s’) = MinimaxValue(s’)
					if (alpha >= beta) { // if ? ? ?, return ?.
						//System.out.println("test");
						return beta;
					}
				}

				return alpha; // return ?.
			} else { // if Min player is to move in s, return mins’ Value(s’).

				for (Simulation sucState: successors) { // for each state s’ in Successors(s)
					// let ? = min { ?, MinValues(s’,?,?) }.
					beta = Math.min(beta, minimaxValue(sucState, alpha, beta, depth + 1, maxDepth)); // let Value(s’) = MinimaxValue(s’)
					if (alpha >= beta) { // if ? ? ?, return ?.
						//System.out.println("test2 " + alpha + " " + beta);
						return alpha;
					}
				}

				return beta; //return ?.
			}
		}
	}

	private void printBoard(CellContent[][] grid) {
		for (CellContent[] linCellContents : grid) {
			System.out.print("|");
			for (CellContent cellContent : linCellContents) {
				switch (cellContent) {
					case EMPTY:
						System.out.print("   |");
						break;
					case ATTACK_TOWER:
						System.out.print(" N |");
						break;
					case DEFENSE_TOWER:
						System.out.print(" B |");
						break;
					case KING:
						System.out.print(" K |");
						break;
					case GATE:
						System.out.print(" X |");
						break;
					case KINGPLACE:
						System.out.print(" P |");
						break;
					default:
						break;
				}
			}
			System.out.println("");
		}
		System.out.flush();
	}

	/**
	 * Retrieves all successor states for a given board state by applying
	 * all legal moves to clones of the current board state.
	 */
	public List<Simulation> getSuccessors(Simulation boardState) {
		List<Simulation> successors = new ArrayList<>();

		// Iterate through all legal moves and apply them to a clone of the board state
		for (Movement curMove: boardState.getAllPossibleMoves()) {
			Simulation clonedBoardState = (Simulation) boardState.clone();
			clonedBoardState.move(curMove.fromL, curMove.fromC, curMove.toL, curMove.toC); // apply the operator o and obtain the new game state s.
			successors.add(clonedBoardState);
		}

		return successors;
	}

	/**
	 * The basic evaluation function used initially (piece difference).
	 * Kept for testing purposes.
	 */
	public double basicEvaluation(Simulation boardState) {
		// Calculate the difference between the player's pieces and the opponent's pieces
		double pieceDifference = (double) (boardState.getPlayer(this.getPlayerEnum()).getOwnedCells().size() - boardState.getPlayer(this.getPlayerEnum().getOpponent()).getOwnedCells().size());

		return pieceDifference;
	}
}
