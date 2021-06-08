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
    public double heuristic(Simulation simulation, PlayerEnum playerEnum) {
        int ownedCellsAmountPlayer = simulation.getPlayer(playerEnum).getOwnedCells().size();
        PlayerEnum opponent = playerEnum.getOpponent();
        int ownedCellsAmountOpponent = simulation.getPlayer(opponent).getOwnedCells().size();

        /*
         * On doit gérer l'évaluation différement selon si le joueur actuel est un défenseur ou un attaquant
         *
         */
        double result;
        if (playerEnum == PlayerEnum.DEFENDER) {
            //Le défenseur cherche à garder le plus de tours possibles et d'éliminer le plus de tours adverse
            //On essaie de rapprocher au maximum le roi d'une des portes de sortie
            result = ownedCellsAmountPlayer - ownedCellsAmountOpponent + 5 * amountOfkingMovesToCorner(simulation);
        } else {
            //L'attaquant cherche à garder le plus de tours possibles et d'éliminer le plus de tours adverse
            //On cherche aussi à éviter que le roi s'approche trop des portes de sortie
            //On favorise aussi grandement les déplacements qui permettent la capture du roi
            //On va aussi favoriser les déplacements qui permettent d'atteindre une position stratégique du Tablut
            //pour les attaquants en se plaçant dans les coins
            result = 1.5 * (ownedCellsAmountPlayer - ownedCellsAmountOpponent) - 2 * amountOfkingMovesToCorner(simulation) + 3 * enemiesAroundKing(simulation) + attackersAroundCorners(simulation);
        }

        return result;
    }

    public double enemiesAroundKing(Simulation simulation) {
        double result = 0.0D;
        
        // En bas du roi
        if (isEnnemy(simulation, simulation.getKingC(), simulation.getKingL()+1)) {
            result += 0.25D;
        }

        // En haut du roi
        if (isEnnemy(simulation, simulation.getKingC(), simulation.getKingL()-1)) {
            result += 0.25D;
        }

        // A droite du roi
        if (isEnnemy(simulation, simulation.getKingC()+1, simulation.getKingL())) {
            result += 0.25D;
        }

        // A gauche du roi
        if (isEnnemy(simulation, simulation.getKingC()-1, simulation.getKingL())) {
            result += 0.25D;
        }

        return result;
    }

    private boolean isEnnemy(Simulation simulation, int col, int row) {
        return !simulation.isValid(col, row) || simulation.getCellContent(col, row) == CellContent.ATTACK_TOWER || simulation.getCellContent(col, row) == CellContent.GATE || simulation.isTheKingPlace(col, row);
    }

    /**
    * On regarde le nombre de tours attaquantes à des endroits stratégiques du plateau
     */
    public double attackersAroundCorners(Simulation simulation) {
        double result = 0.0;

        // Coin haut gauche
        if (simulation.getCellContent(1, 1) == CellContent.ATTACK_TOWER) {
            result += 0.25;
        }

        // Coin bas gauche
        if (simulation.getCellContent(1, 7) == CellContent.ATTACK_TOWER) {
            result += 0.25;
        }

        // Coin haut droit
        if (simulation.getCellContent(7, 1) == CellContent.ATTACK_TOWER) {
            result += 0.25;
        }

        // Coin bas droit
        if (simulation.getCellContent(7, 7) == CellContent.ATTACK_TOWER) {
            result += 0.25;
        }

        return result;
    }

     /**
      * Methode permétant de repérer si le roi est à portée d'une des 4 sorties du plateau
      */
    public double amountOfkingMovesToCorner(Simulation simulation) {
        List<Movement> kingMoves = simulation.getAllPossibleMovesForPosition(simulation.getKingC(), simulation.getKingL());

        double result = 0;
        if (!kingMoves.isEmpty()) { // Si le roi ne peut pas bouger pas besoin  de faire le reste
            // d contiendra les coups minimums pour aller sur chaqu'un des coins du plateau
            int[] d = new int[4];

            // Calcul du nombre de coup minimal pour chaques coins
            int i = 0;
            for (Point corner : Arrays.asList(new Point(0, 0), new Point(0, 8), new Point(8, 0), new Point(8, 8))) {
                d[i++] = movesToCorner(simulation, corner, 1, simulation.getKingC(), simulation.getKingL());
            }

            // Attribution de la valeur en fonction de la distance
            for (int j = 0; j < d.length; j++) {
                switch (d[j]) {
                    case 1:  result += 50; // Un roi à un mouvement de gagner est très profitable
                        break;
                    case 2:  result += 2; // Un roi à deux mouvement est bénéfique
                        break;
                    case 3:
                        result += 0.5; // Un roi à trois mouvements est bénéfique mais pas non plus incontournable
                        break;
                    default: result += 0; // Au dela, le bénéfice est trop faible
                        break;
                }
            }
        }

        return result;
    }

    public boolean isCorner(int i, int j) {
        return (i == j && (i == 0 || i == 8)) || (j - i) == 8 || (j - i) == -8;
    }

     /**
      * Calcule le nombre minimal de coup pour que le roi arrive a un coin
      * A noté qu'on ne prend pas en compte des éventuel mouvement ennemi, trop couteux
      */
    public int movesToCorner(Simulation simulation, Point corner, int c, int kingC, int kingL) {
        // Si on est déjà dans un coin ou on essaye de regarder à plus de deux coup on arrète
        if (c == 4 || isCorner(kingC, kingL)) {
            return c;
        }

        List<Movement> kingMoves = simulation.getAllPossibleMovesForPosition(kingC, kingL);

        // Création d'un tableau qui permettra de stocker les nombres de coup pour chaque déplacement
        int[] moves = new int[kingMoves.size()];

        //Parcours de tous les mouvements du roi pour remplir le tableau
        int i = 0;
        for (Movement move : kingMoves) {
            if (move.getFrom().distance(corner) > move.getTo().distance(corner)) {
                moves[i++] = movesToCorner(simulation, corner, c + 1, move.getToC(), move.getToL());
            }
        }

        // On va trouver le mouvement avec le moins de coups possibles pour atteindre l'un des coins
        int min = 100;
        for (int j = 0; j < moves.length; j++) {
            int value = moves[j];
            if (value != 0 && value < min) {
                min = value;
            }
        }

        //On retourne le minimum
        return min;
    }
}