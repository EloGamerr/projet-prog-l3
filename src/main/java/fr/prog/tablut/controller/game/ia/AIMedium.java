package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
* IA de difficulté moyenne prenant en compte le nombre de pièces du joueur actuel, de son adversaire, l'évasion du roi et certains points stratégiques comme
/ le positonnement des attaquants à proximité des portes d'évasion
 */
public class AIMedium extends AIMinMax {
    public AIMedium(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    /**
     * Fonction calculant l'heuristique d'une simulation
     */
    public double evaluation(Simulation boardState, PlayerEnum playerEnum) {
        PlayerEnum opponent = playerEnum.getOpponent();
        double oppPieceValue = (double) boardState.getPlayer(opponent).getOwnedCells().size();
        double yourPieceValue = (double) boardState.getPlayer(playerEnum).getOwnedCells().size();


        int turn = 50;
        /*
         * On doit gérer l'évaluation différement selon si le joueur actuel est un défenseur ou un attaquant
         *
         * Le défenseur ne prend pas en compte le nombre de pièces ennemis autour du roi mais prend en compte
         * si le roi est proche d'une porte de sortie
         *
         * L'attaquant prend plus en compte la différence de pièces entre les 2 équipes
         */
        double evaluationValue = 0.0;
        if (playerEnum == PlayerEnum.DEFENDER) {
            if (turn < 40) {
                evaluationValue = yourPieceValue - oppPieceValue + kingMovesToCornerValue(boardState);
            } else if (turn < 70) {
                evaluationValue = yourPieceValue - oppPieceValue + 2.0 * kingMovesToCornerValue(boardState);
            } else {
                evaluationValue = yourPieceValue - oppPieceValue + 3.0 * kingMovesToCornerValue(boardState);
            }
        } else {
            if (turn < 40) {
                evaluationValue = yourPieceValue - oppPieceValue + attackersAroundCorners(boardState) - kingMovesToCornerValue(boardState) + enemiesAroundKing(boardState);
            } else if (turn < 70) {
                evaluationValue = 2 * (yourPieceValue - oppPieceValue) + attackersAroundCorners(boardState) - 1.5 * kingMovesToCornerValue(boardState) + 6 * enemiesAroundKing(boardState);
            } else {
                evaluationValue = 3 * (yourPieceValue - oppPieceValue) + attackersAroundCorners(boardState) - 1.5 * kingMovesToCornerValue(boardState) + 12 * enemiesAroundKing(boardState);
            }
        }

        return evaluationValue;
    }

    public double enemiesAroundKing(Simulation simulation) {
        double numPieces = 0.0;
        
        // En bas du roi
        if (isEnnemy(simulation, simulation.getKingL()+1, simulation.getKingC())) {
            numPieces += 0.25;
        }

        // En haut du roi
        if (isEnnemy(simulation, simulation.getKingL()-1, simulation.getKingC())) {
            numPieces += 0.25;
        }

        // A droite du roi
        if (isEnnemy(simulation, simulation.getKingL(), simulation.getKingC()+1)) {
            numPieces += 0.25;
        }

        // A gauche du roi
        if (isEnnemy(simulation, simulation.getKingL(), simulation.getKingC()-1)) {
            numPieces += 0.25;
        }

        return numPieces;
    }

    private boolean isEnnemy(Simulation simulation, int row, int col) {
        return !simulation.isValid(row, col) || simulation.getCellContent(row, col) == CellContent.ATTACK_TOWER || simulation.getCellContent(row, col) == CellContent.GATE || simulation.isTheKingPlace(row, col);
    }

    /**
     * Given a game state, this method returns a value used in board state evaluation for the
     * number of black pieces in key strategic locations near the corners.
     *
     * These locations were determined to be useful for the Muscovites in
     * maintaining an advantage during the game.
     */
    public double attackersAroundCorners(Simulation boardState) {
        double value = 0.0;

        // Coin haut gauche
        if (boardState.getCellContent(1, 1) == CellContent.ATTACK_TOWER) {
            value += 0.25;
        }

        // Coin haut droit
        if (boardState.getCellContent(1, 7) == CellContent.ATTACK_TOWER) {
            value += 0.25;
        }

        // Coin bas gauche
        if (boardState.getCellContent(7, 1) == CellContent.ATTACK_TOWER) {
            value += 0.25;
        }

        // Coin bas droit
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

     /**
      * Methode permétant de repérer si le roi est a portée d'une des 4 sorties du plateau
      * @param simulation
      * @return 
      */
    public double kingMovesToCornerValue(Simulation simulation) {
        Point kingPosition = new Point(simulation.getKingC(), simulation.getKingL());

        // Retrieves all legal moves for the king based on its current position
        List<Movement> kingMoves = simulation.getAllPossibleMovesForPosition(kingPosition.x, kingPosition.y);

        double moveDistanceValue = 0.0;
        if (!kingMoves.isEmpty()) { // Si le roi ne peut pas bouger pas besoin  de faire le reste

            // distances contiendra les coups minimums pour aller sur chaqu'un des coins du plateau
            int [] distances = new int [4];

            // Calcul du nombre de coup minimal pour chaques coins
            int cornerIdx = 0;
            for (Point corner : Arrays.asList(new Point(0, 0), new Point(0, 8), new Point(8, 0), new Point(8, 8))) {
                distances[cornerIdx] = calcMinMovesToCorner(simulation, corner, 1, kingPosition);
                cornerIdx++;
            }

            // Attribution de la valeur en fonction de la distance
            for (int i = 0; i < distances.length; i++) {
                switch (distances[i]) {
                    case 1:  moveDistanceValue += 15; // Un roi à un mouvement de gagner est très profitable
                        break;
                    case 2:  moveDistanceValue += 1; // Un roi a deux mouvement est bénéfique mais pas non plus incontournable
                        break;
                    default: moveDistanceValue += 0; // Au dela, le bénéfice est trop faible
                        break;
                }
            }
        }

        return moveDistanceValue;
    }
    /**
     * 
     * @param p la position de la case
     * @return si la case est un angl
     */
    public boolean isCorner(Point p) {
        return isCorner(p.x, p.y);
    }

    public boolean isCorner(int i, int j) {
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

     /**
      * Calcule le nombre minimal de coup pour que le roi arrive a un coin
      * A noté qu'on ne prend pas en compte des éventuel mouvement ennemi, trop couteux
      * @param simulation
      * @param corner
      * @param moveCt
      * @param kingPosition
      * @return
      */
    public int calcMinMovesToCorner(Simulation simulation, Point corner, int moveCt, Point kingPosition) {
        // Termination condition - either we're in a corner or it takes too many moves and thus becomes irrelevant
        // Si on est déjà dans un coin ou on essaye de regarder à plus de deux coup on arrète
        if (moveCt == 3 || isCorner(kingPosition)) {
            return moveCt;
        }

        List<Movement> kingMoves = simulation.getAllPossibleMovesForPosition(kingPosition.x, kingPosition.y);

        // We'll store the counts for each move here
        // Création d'un tableau qui permetra de stocker les nombres de coup pour chaques déplacement
        int [] moveCounts = new int[kingMoves.size()];

        // Iterate through current possible king moves and see how much closer we can get to a corner
        //Parcours de tout les mouvement du roi pour remplir le tableau
        int moveIdx = 0;
        for (Movement move : kingMoves) {
            // If move brings you closer to the corner, attempt it
            if (distance(new Point(move.getFromL(), move.getFromC()), corner) > distance(new Point(move.getToL(), move.getToC()), corner)) {
                moveCounts[moveIdx++] = calcMinMovesToCorner(simulation, corner, moveCt + 1, new Point(move.getToL(), move.getToC()));
            }
        }

        // Find the min number of moves to reach the corner, or return 50 if unreachable
        // On va trouver le mouvement avec le mois de coup possible pour atteindre l'un des coins
        int min = 50;
        for (int i = 0; i < moveCounts.length; i++) {
            int current = moveCounts[i];
            if (current != 0 && current < min) {
                min = current;
            }
        }

        //On retourne le minimum
        return min;
    }

    /**
     * 
     * @param p1 Emplacement de la première case
     * @param p2 Emplacement de la deuxième case
     * @return la distance entre les deux cases sous forme d'entier
     */
    private int distance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}