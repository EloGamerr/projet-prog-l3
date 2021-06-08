package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.CellContent;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 * IA de difficulté difficile prenant en compte le nombre de pièces du joueur actuel, de son adversaire,
 * l'évasion du roi, les attaquants à proximité du roi et certains points stratégiques comme le positonnement
 * des attaquants à proximité des portes d'évasion
 */
public class AIHard extends AIMinMax {
    public AIHard(PlayerEnum playerEnum) {
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
            //On essaie d'éviter que le roi soit entouré d'ennemis
            //Dans le cas où il y a la possibilité d'atteindre une porte de sortie avec le roi, on regarde
            //aussi le nombre d'ennemis sur la ligne du roi car la sortie est beaucoup plus facilement atteignable si
            //aucun ennemi ne la protège
            result = 1.5 * (ownedCellsAmountPlayer - ownedCellsAmountOpponent) + amountOfkingMovesToCorner(simulation, 100, 100, 1) - 2*enemiesAroundKing(simulation) - (amountOfkingMovesToCorner(simulation, 100, 100, 1) == 0 ? 0 : enemiesOnKingLine(simulation));
        } else {
            //L'attaquant cherche à garder le plus de tours possibles et d'éliminer le plus de tours adverse
            //On cherche aussi à éviter que le roi s'approche trop des portes de sortie
            //On favorise aussi grandement les déplacements qui permettent la capture du roi
            //On va aussi favoriser les déplacements qui permettent d'atteindre une position stratégique du Tablut
            //pour les attaquants en se plaçant dans les coins
            result = 1.5 * (ownedCellsAmountPlayer - ownedCellsAmountOpponent) - 2 * amountOfkingMovesToCorner(simulation, 100, 100, 1) + 3 * enemiesAroundKing(simulation) + attackersAroundCorners(simulation);
        }

        return result;
    }

    public double enemiesOnKingLine(Simulation simulation) {
        int amount = 0;

        // En bas du roi
        for(int c = simulation.getKingC()-1 ; c >= 0 ; c--) {
            if (isEnnemy(simulation, simulation.getKingC()+c, simulation.getKingL())) {
                amount++;
                break;
            }
        }

        for(int c = simulation.getKingC()+1 ; c <= 8 ; c++) {
            if (isEnnemy(simulation, simulation.getKingC()+c, simulation.getKingL())) {
                amount++;
                break;
            }
        }

        for(int l = simulation.getKingL()+1 ; l <= 8 ; l++) {
            if (isEnnemy(simulation, simulation.getKingC(), simulation.getKingL()+l)) {
                amount++;
                break;
            }
        }

        for(int l = simulation.getKingL()-1 ; l >= 0 ; l--) {
            if (isEnnemy(simulation, simulation.getKingC(), simulation.getKingL()+l)) {
                amount++;
                break;
            }
        }

        switch(amount) {
            case 4:
                return 100;
            case 3:
                return 10;
            case 2:
                return 5;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    public double enemiesAroundKing(Simulation simulation) {
        int amount = 0;

        // En bas du roi
        if (isEnnemy(simulation, simulation.getKingC(), simulation.getKingL()+1)) {
            amount++;
        }

        // En haut du roi
        if (isEnnemy(simulation, simulation.getKingC(), simulation.getKingL()-1)) {
            amount++;
        }

        // A droite du roi
        if (isEnnemy(simulation, simulation.getKingC()+1, simulation.getKingL())) {
            amount++;
        }

        // A gauche du roi
        if (isEnnemy(simulation, simulation.getKingC()-1, simulation.getKingL())) {
            amount++;
        }

        switch(amount) {
            case 4:
                return 200; // mort du roi
            case 3:
                return 10; // très dangereux
            case 2:
                return 2; // dangereux mais facilement contrable
            case 1:
                return 1; // pas de danger
            default:
                return 0;
        }
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
    public double amountOfkingMovesToCorner(Simulation simulation, double firstResult, double secondResult, double thirdResult) {
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
            for (int k : d) {
                switch (k) {
                    case 1:
                        result += firstResult; // Le roi est à un mouvement de gagner
                        break;
                    case 2:
                        result += secondResult; // Le roi est à deux mouvements de gagner
                        break;
                    case 3:
                        result += thirdResult; // Le roi est à trois mouvements de gagner
                        break;
                    default:
                        result += 0; // Le roi est à plus de trois mouvements de gagner
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
        for (int value : moves) {
            if (value != 0 && value < min) {
                min = value;
            }
        }

        //On retourne le minimum
        return min;
    }
}