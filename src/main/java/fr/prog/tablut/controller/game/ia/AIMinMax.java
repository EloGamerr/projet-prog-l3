package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.pages.game.GamePage;
import fr.prog.tablut.view.pages.game.sides.center.board.BoardDrawer;
import fr.prog.tablut.view.pages.game.sides.center.board.GameColors;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class AIMinMax extends AIPlayer {
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public AIMinMax(PlayerEnum playerEnum) {
        super(playerEnum);
    }

    @Override
    public boolean play(Game game, GamePage gamePage) {
        if(super.play(game, gamePage)) {
            Simulation simulation = new Simulation(game);
            Movement movement = null;
            try {
                movement = getBestMovement(simulation);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Joue !");
            BoardDrawer g =  gamePage.getBoardInterface().getIndicatorsDesigner().getBoardDrawer();
            g.setColor(GameColors.GATE_FRAME_COLOR);
            g.strokeWidth(2);
            g.strokeSquare(1, 1);
            g.strokeWidth(1);
            g.setColor(GameColors.CIRCLE);
            updateAnim(new Point(movement.fromC, movement.fromL), new Point(movement.toC, movement.toL), gamePage);
        }

        return true;
    }

    /**
     * Préparation de l'algorithme minimax, la méthode initialise l'algorithme pour permettre
     * de créer et parcourir l'arbre des simulations jusqu'à une profondeur maximum
     * pour finalement déterminer quel coup est le plus rentable
     */
    public Movement getBestMovement(Simulation simulation) throws ExecutionException, InterruptedException {

        //On peut obtenir la liste de tout les mouvements possibles pour la game
        List<Movement> moves = simulation.getAllPossibleMoves();

        // Stores the value of every possible move for the current board state.
        // Regroupe l'heuristique de tout les coups possibles
        Future[] moveValue = new Future [moves.size()];
        // This was found to be the max depth value which causes minimal timeouts
        // L'IA a pour l'instant une profondeur de 3, aller au dessus prend beaucoup plus de temps
        int maxDepth = 3;

        /**
         * On va itérer sur chaques possibilitées pour lui assigner une heuristique
         */
        int curMoveIdx = 0;
        for (Movement curMove: moves) {
            //La classe simulation permet d'appliquer le mouvement et d'en déterminer l'heuristique par l'algorithme min/max
            Simulation newSimulation = (Simulation) simulation.clone();
            newSimulation.move(curMove.fromC, curMove.fromL, curMove.toC, curMove.toL);

            // Si la simulation renvoie un état gagnant, pas besoin de calculer les suivantes
            if (newSimulation.getWinner() == this.getPlayerEnum()) {
                return curMove;
            }

            //On commence avec un alpha maximum et un beta au minimum et une profondeur de 1
            moveValue[curMoveIdx++] = executor.submit(() -> minimax(newSimulation, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, maxDepth));
        }
        //On retourne le mouvement avec l'heuristique la plus haute dans la liste
        List<Integer> highestValueMoves = getHighestValueMoves(moveValue);
        return moves.get(highestValueMoves.get(random.nextInt(highestValueMoves.size())));
    }

    /**
     * Renvoie tous les indices dont la valeur correspondante est celle maximum
     * parmi tous les éléments du tableau
     */
    public List<Integer> getHighestValueMoves(Future [] moveValue) throws ExecutionException, InterruptedException {
        double maxValue = (double) moveValue[0].get();
        List<Integer> maxIdx = new ArrayList<>();
        maxIdx.add(0);
        //On cherche la valeur max
        for(int i = 1; i < moveValue.length; i++) {
            //On vide la liste si on trouve une nouvelle valeur max
            double value = (double) moveValue[i].get();
            if (value > maxValue) {
                maxIdx.clear();
                maxValue = value;
                maxIdx.add(i);
            }
            //On ajoute l'indice si sa valeur est la valeur max actuelle
            else if(value == maxValue) {
                maxIdx.add(i);
            }
        }
        return maxIdx;
    }

    /**
     * Utilisation de l'algo minimax, on crée et parcourt un arbre des simulations en utilisant un élagage alpha-bêta 
     * et on renvoie la valeur du meilleur coup selon l'heuristique de la classe fils
     */
    public double minimax(Simulation simulation, double alpha, double beta, int depth, int maxDepth) throws ExecutionException, InterruptedException {
        if (simulation.isWon()) {
            PlayerEnum winner = simulation.getWinner();
            if (winner == this.getPlayerEnum()) { // Si on arrive à une situation gagnante pour l'IA, on renvoie la valeur maximale
                return Double.MAX_VALUE;
            } else if (winner == this.getPlayerEnum().getOpponent()) { // Si on arrive à une situation perdante pour l'IA, on renvoie la valeur minimale
                return Double.MIN_VALUE;
            }
            return 0D; // On ne devrait jamais arriver ici mais au cas où
        } else if (depth == maxDepth) { // Si on est arrivé a la profondeur maximale on renvoie la valeur selon l'heuristique définit dans la classe fils
            return this.evaluation(simulation, this.getPlayerEnum());
        } else { // On continue de créer et d'explorer l'arbre
            if (this.getPlayerEnum() == simulation.getPlayingPlayerEnum()) { //On est ici dans le cas du joueur
                for (Movement movement : simulation.getAllPossibleMoves()) {
                    alpha = Math.max(alpha, minimax(getNextSimulation(simulation, movement), alpha, beta, depth + 1, maxDepth));
                    if (alpha >= beta) {
                        return beta; //On retourne la valeur beta si elle est maximale
                    }
                }

                return alpha; // On retourne la valeurs alpha si elle est maximale
            }
            else { // On est ici dans le cas de l'adversaire
                for (Movement movement: simulation.getAllPossibleMoves()) {
                    beta = Math.min(beta, minimax(getNextSimulation(simulation, movement), alpha, beta, depth + 1, maxDepth));
                    if (alpha >= beta) {
                        return alpha;
                    }
                }

                return beta;
            }
        }
    }

    private Simulation getNextSimulation(Simulation simulation, Movement movement) {
        Simulation newSimulation = (Simulation) simulation.clone(); // On clone la simulation pour ne pas perdre celle passée en paramètre
        newSimulation.move(movement.fromC, movement.fromL, movement.toC, movement.toL); // Obtention du nouvel état de la simulation copiée
        return newSimulation;
    }

    public abstract double evaluation(Simulation simulation, PlayerEnum playerEnum);
}
