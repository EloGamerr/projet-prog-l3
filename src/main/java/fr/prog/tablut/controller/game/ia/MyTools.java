package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.structures.Couple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is mostly used to keep methods that are used in the
 * the game state evaluation function used by Minimax
 */
public class MyTools {

    /**
     * The evaluation function for the board state. Although several
     * different functions were considered and tested, along with various weights
     * for the different variables, the evaluation function below turned out to
     * work best. An analysis of evaluation functions is included in the report.
     */
    public static double evaluation(Simulation boardState, PlayerEnum playerEnum) {
        PlayerEnum opponent = playerEnum.getOpponent();
        double oppPieceValue = (double) boardState.getPlayer(opponent).getOwnedCells().size();
        double yourPieceValue = (double) boardState.getPlayer(playerEnum).getOwnedCells().size();


        int turn = 0;
        /*
         * The evaluation function is different when the player is a Swede vs when the player is a Muscovite.
         *
         * The following differences in player priority were determined to be the most effective:
         * Swedes care less about the number of enemy pieces surrounding the king and more about the King's
         * proximity to a corner (in number of moves).
         *
         * The Muscovites care more about the piece count difference as the game progresses and less about
         * the king's proximity to a corner.
         */
        double evaluationValue = 0.0;
        if (playerEnum == PlayerEnum.DEFENDER) {
            /*
             * The Swedes evaluation function becomes more aggressive as the game progresses in terms of:
             * the number of moves for the king to reach a corner
             */
            if (turn < 40) {
                evaluationValue = yourPieceValue - oppPieceValue + kingMovesToCornerValue(boardState);
            } else if (turn < 70) {
                evaluationValue = yourPieceValue - oppPieceValue + 2.0 * kingMovesToCornerValue(boardState);
            } else {
                evaluationValue = yourPieceValue - oppPieceValue + 3.0 * kingMovesToCornerValue(boardState);
            }
        } else {
            /*
             * The Muscovites evaluation function becomes more aggressive as the game progresses in terms of:
             * the number of enemy pieces around the king and
             * the number of moves for the king to reach a corner
             */
            if (turn < 40) {
                evaluationValue = yourPieceValue - oppPieceValue + piecesAroundCorners(boardState) - kingMovesToCornerValue(boardState) + enemyPiecesAroundKing(boardState);
            } else if (turn < 70) {
                evaluationValue = 2 * (yourPieceValue - oppPieceValue) + piecesAroundCorners(boardState) - 1.5 * kingMovesToCornerValue(boardState) + 6 * enemyPiecesAroundKing(boardState);
            } else {
                evaluationValue = 3 * (yourPieceValue - oppPieceValue) + piecesAroundCorners(boardState) - 1.5 * kingMovesToCornerValue(boardState) + 12 * enemyPiecesAroundKing(boardState);
            }
        }

        return evaluationValue;
    }

    /**
     * Given a game state, this method returns a value used in board state evaluation
     * which represents the number of enemy pieces surrounding the Swede king.
     */
    public static double enemyPiecesAroundKing(Simulation boardState) {
        double numPieces = 0.0;
        
        // Top left corner
        if (!boardState.isValid(boardState.getKingL()+1, boardState.getKingC()) || boardState.getCellContent(boardState.getKingL()+1, boardState.getKingC()) == CellContent.ATTACK_TOWER) {
            numPieces += 0.25;
        }

        // Top right corner
        if (!boardState.isValid(boardState.getKingL()-1, boardState.getKingC()) || boardState.getCellContent(boardState.getKingL()-1, boardState.getKingC()) == CellContent.ATTACK_TOWER) {
            numPieces += 0.25;
        }

        // Bottom left corner
        if (!boardState.isValid(boardState.getKingL(), boardState.getKingC()+1) || boardState.getCellContent(boardState.getKingL(), boardState.getKingC()+1) == CellContent.ATTACK_TOWER) {
            numPieces += 0.25;
        }

        // Bottom right corner
        if (!boardState.isValid(boardState.getKingL(), boardState.getKingC()-1) || boardState.getCellContent(boardState.getKingL(), boardState.getKingC()-1) == CellContent.ATTACK_TOWER) {
            numPieces += 0.25;
        }

        return numPieces;
    }

    /**
     * Given a game state, this method returns a value used in board state evaluation for the
     * number of black pieces in key strategic locations near the corners.
     *
     * These locations were determined to be useful for the Muscovites in
     * maintaining an advantage during the game.
     */
    public static double piecesAroundCorners(Simulation boardState) {
        double value = 0.0;

        // Top left corner
        if (boardState.getCellContent(1, 1) == CellContent.ATTACK_TOWER) {
            value += 0.25;
        }

        // Top right corner
        if (boardState.getCellContent(1, 7) == CellContent.ATTACK_TOWER) {
            value += 0.25;
        }

        // Bottom left corner
        if (boardState.getCellContent(7, 1) == CellContent.ATTACK_TOWER) {
            value += 0.25;
        }

        // Bottom right corner
        if (boardState.getCellContent(7, 7) == CellContent.ATTACK_TOWER) {
            value += 0.25;
        }

        return value;
    }

    /**
     * This method returns a value representing the number of king moves to all the corners.
     * Given a state, the method checks the min number of moves to each corner, and returns
     * a positive value if we are within 1-2 moves to a certain corner, and an even higher
     * value if we are withing 1-2 moves to more than one corner.
     *
     */
    public static double kingMovesToCornerValue(Simulation boardState) {
        Couple<Integer, Integer> kingPosition = new Couple<>(boardState.getKingL(), boardState.getKingC());

        // Retrieves all legal moves for the king based on its current position
        List<Movement> kingMoves = getLegalKingMovesForPosition(kingPosition, boardState);

        double moveDistanceValue = 0.0;
        if (!kingMoves.isEmpty()) { // If the king actually has moves

            // Stores the min number of moves to reach each of the 4 corners
            int [] distances = new int [4];

            // Iterate through all corners, calculating the min number of moves to reach each one
            int cornerIdx = 0;
            for (Couple<Integer, Integer> corner : Arrays.asList(new Couple<>(0, 0), new Couple<>(0, 8), new Couple<>(8, 0), new Couple<>(8, 8))) {
                distances[cornerIdx] = calcMinMovesToCorner(boardState, corner, 1, kingPosition);
                cornerIdx++;
            }

            // Generate the move's value based on proximity to the corner
            for (int i = 0; i < distances.length; i++) {
                switch (distances[i]) {
                    case 1:  moveDistanceValue += 15; // Being 1 move away is much more valuable
                        break;
                    case 2:  moveDistanceValue += 1;
                        break;
                    default: moveDistanceValue += 0;
                        break;
                }
            }
        }

        return moveDistanceValue;
    }

    public static boolean isCorner(Couple<Integer, Integer> c) {
        return isCorner(c.getFirst(), c.getSecond());
    }

    public static boolean isCorner(int i, int j) { // Very efficient way to check if something is a corner.
        if (i * j != 0)
            return i == 8 && j == 8;
        return i + j == 8 || i + j == 0;
    }
    
    /**
     * This method calculates the min number of moves for the king to reach a given corner.
     *
     * This is done by ignoring opponent moves. We simply care about how many consecutive moves it would
     * take the king to reach a specific corner. This is because it becomes very difficult (and costly) to
     * predict opponent moves as well.
     *
     * This method projects a move onto the board state and recursively goes to the following move, but does
     * not actually process the move in order to be more efficient (time and memory-wise).
     */
    public static int calcMinMovesToCorner(Simulation boardState, Couple<Integer, Integer> corner, int moveCt, Couple<Integer, Integer>kingPosition) {
        // Termination condition - either we're in a corner or it takes too many moves and thus becomes irrelevant
        if (moveCt == 3 || isCorner(kingPosition)) {
            return moveCt;
        }

        List<Movement> kingMoves = getLegalKingMovesForPosition(kingPosition, boardState);

        // We'll store the counts for each move here
        int [] moveCounts = new int[kingMoves.size()];

        // Iterate through current possible king moves and see how much closer we can get to a corner
        int moveIdx = 0;
        for (Movement move : kingMoves) {
            // If move brings you closer to the corner, attempt it
            if (distance(new Couple<>(move.getFromL(), move.getFromC()), corner) > distance(new Couple<>(move.getToL(), move.getToC()), corner)) {
                moveCounts[moveIdx] = calcMinMovesToCorner(boardState, corner, moveCt + 1, new Couple<>(move.getToL(), move.getToC()));
                moveIdx++;
            }
        }

        // Find the min number of moves to reach the corner, or return 50 if unreachable
        int min = 50;
        for (int i = 0; i < moveCounts.length; i++) {
            int current = moveCounts[i];
            if (current != 0 && current < min) {
                min = current;
            }
        }
        return min;
    }

    private static int distance(Couple<Integer, Integer> c1, Couple<Integer, Integer> c2) {
        return Math.abs(c1.getFirst() - c2.getFirst()) + Math.abs(c1.getSecond() - c2.getSecond());
    }

    /**
     * Modified the getLegalMovesForPosition() method from Simulation.java,
     * adjusted because the original was not permitting me to retrieve king moves.
     */
    public static ArrayList<Movement> getLegalKingMovesForPosition(Couple<Integer, Integer>start, Simulation boardState) {
        ArrayList<Movement> legalMoves = new ArrayList<>();

        // Iterate along 4 directions.
        List<Couple<Integer, Integer>> goodCoords = new ArrayList<>();
        goodCoords.addAll(getLegalCoordsInDirection(start, -1, 0, boardState)); // move in -x direction
        goodCoords.addAll(getLegalCoordsInDirection(start, 0, -1, boardState)); // move in -y direction
        goodCoords.addAll(getLegalCoordsInDirection(start, 1, 0, boardState)); // move in +x direction
        goodCoords.addAll(getLegalCoordsInDirection(start, 0, 1, boardState)); // move in +y direction

        /*
         * Add the real moves now. We do not call isLegal here; this is because we
         * efficiently enforce legality by only adding those that are legal. This makes
         * for a more efficient method so people aren't slowed down by just figuring out
         * what they can do.
         */
        for (Couple<Integer, Integer>end : goodCoords) {
            legalMoves.add(new Movement(start.getFirst(), start.getSecond(), end.getFirst(), end.getSecond()));
        }
        return legalMoves;
    }

    /**
     * Added the getLegalCoordsInDirection() method from Simulation.java,
     * because it is required by getLegalKingMovesForPosition() (above) but the original is private.
     */
    private static List<Couple<Integer, Integer>> getLegalCoordsInDirection(Couple<Integer, Integer>start, int x, int y, Simulation boardState) {
        ArrayList<Couple<Integer, Integer>> coords = new ArrayList<>();
        assert (!(x != 0 && y != 0));
        int startPos = (x != 0) ? start.getFirst() : start.getSecond(); // starting at x or y
        int incr = (x != 0) ? x : y; // incrementing the x or y value
        int endIdx = (incr == 1) ? 9 - 1 : 0; // moving in the 0 or 8 direction

        for (int i = startPos + incr; incr * i <= endIdx; i += incr) { // increasing/decreasing functionality
            // new Couple<Integer, Integer>is an x Couple<Integer, Integer>change or a y Couple<Integer, Integer>change
            Couple<Integer, Integer> coord = (x != 0) ? new Couple<>(i, start.getSecond()) : new Couple<>(start.getFirst(), i);
            if (!boardState.isOccupied(coord.getFirst(), coord.getSecond())) {
                coords.add(coord);
            } else {
                break;

            }
        }
        return coords;
    }
}