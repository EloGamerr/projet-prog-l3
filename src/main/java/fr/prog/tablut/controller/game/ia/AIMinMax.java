package fr.prog.tablut.controller.game.ia;

import fr.prog.tablut.model.game.Game;
import fr.prog.tablut.model.game.Movement;
import fr.prog.tablut.model.game.player.PlayerEnum;
import fr.prog.tablut.view.pages.game.GamePage;

import javax.swing.SwingUtilities;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SuppressWarnings("rawtypes")
public abstract class AIMinMax extends AIPlayer {
    // Après plusieurs tests, on a conclu que 2 ou 3 threads permettaient d'avoir de meilleures performances
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final Deque<Movement> previousMovements;
    private boolean lockPlay;

    public AIMinMax(PlayerEnum playerEnum) {
        super(playerEnum);

        previousMovements = new ArrayDeque<>();
        lockPlay = false;
    }

    @Override
    public boolean play(Game game, GamePage gamePage) {
        if(!lockPlay && super.play(game, gamePage)) {
            // On doit lock la méthode play car elle va être rappelé
            // tant que l'IA cherche meilleur coup
            lockPlay = true;

            // On cherche le meilleur mouvement dans un nouveau thread pour ne pas bloquer l'interface
            new Thread(() -> {
                Simulation simulation = new Simulation(game);
                try {
                    final Movement movement = getBestMovement(simulation);

                    //On évite que l'IA fasse les mêmes mouvements en boucle (voir plus loin dans le code)
                    while(previousMovements.size() >= 3)
                        previousMovements.removeFirst();

                    previousMovements.addLast(movement);

                    // On revient sur le thread principal
                    SwingUtilities.invokeLater(() -> {
                        //On doit mettre lockPlay à false seulement dès qu'on repasse
                        //dans le thread principal
                        lockPlay = false;
                        updateAnim(Objects.requireNonNull(movement).getFrom(), movement.getTo(), gamePage);
                    });
                } catch (ExecutionException |InterruptedException e) {
                    //On remet lockPlay à false car une erreur a eu lieu : l'IA n'est plus en train de chercher un meilleur coup
                    lockPlay = false;
                    e.printStackTrace();
                }
            }).start();
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

        // Regroupe l'heuristique de tout les coups possibles
        Future[] values = new Future[moves.size()];

        // L'IA a pour l'instant une profondeur de 3, aller au dessus prend beaucoup plus de temps
        int maxDepth = 3;

        // On va itérer sur chaques possibilité pour lui assigner une heuristique
        int i = 0;
        for (Movement move: moves) {
            //La classe simulation permet d'appliquer le mouvement et d'en déterminer l'heuristique par l'algorithme min/max
            Simulation newSimulation = new Simulation(simulation);
            newSimulation.move(move.getFromC(), move.getFromL(), move.getToC(), move.getToL());

            // Si la simulation renvoie un état gagnant, pas besoin de calculer les suivantes
            if (newSimulation.getWinner() == this.getPlayerEnum()) {
                return move;
            }

            //On évite que l'IA fasse les mêmes mouvements en boucle
            if(previousMovements.contains(move)) {
                values[i++] = executor.submit(() -> -1000D);
                continue;
            }

            //On commence avec un alpha maximum et un beta au minimum et une profondeur de 1
            //On va diviser les calculs dans plusieurs threads pour améliorer légèrement les performances
            values[i++] = executor.submit(() -> minimax(newSimulation, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1, maxDepth));
        }
        //On retourne le mouvement avec l'heuristique la plus haute dans la liste
        List<Integer> highestValueMoves = getHighestValues(values);
        return moves.get(highestValueMoves.get(random.nextInt(highestValueMoves.size())));
    }

    /**
     * Renvoie tous les indices dont la valeur correspondante est celle maximum
     * parmi tous les éléments du tableau
     */
    public List<Integer> getHighestValues(Future[] values) throws ExecutionException, InterruptedException {
        double maxValue = (double) values[0].get();
        List<Integer> maxI = new ArrayList<>();
        maxI.add(0);
        //On cherche la valeur max
        for(int i = 1; i < values.length; i++) {
            //On vide la liste si on trouve une nouvelle valeur max
            double value = (double) values[i].get();
            if (value > maxValue) {
                maxI.clear();
                maxValue = value;
                maxI.add(i);
            }
            //On ajoute l'indice si sa valeur est la valeur max actuelle
            else if(value == maxValue) {
                maxI.add(i);
            }
        }
        return maxI;
    }

    /**
     * Utilisation de l'algo minimax, on crée et parcourt un arbre des simulations en utilisant un élagage alpha-bêta 
     * et on renvoie la valeur du meilleur coup selon l'heuristique de la classe fils
     */
    public double minimax(Simulation simulation, double alpha, double beta, int depth, int maxDepth) {
        PlayerEnum winner = simulation.getWinner();
        if (winner == this.getPlayerEnum()) { // Si on arrive à une situation gagnante pour l'IA, on renvoie la valeur maximale
            return Double.POSITIVE_INFINITY;
        }
        else if(winner == this.getPlayerEnum().getOpponent()) { // Si on arrive à une situation perdante pour l'IA, on renvoie la valeur minimale
            return Double.NEGATIVE_INFINITY;
        }
        else if (depth == maxDepth) { // Si on est arrivé a la profondeur maximale on renvoie la valeur selon l'heuristique définit dans la classe fils
            return this.heuristic(simulation, this.getPlayerEnum());
        }
        else { // On continue de créer et d'explorer l'arbre
            if (this.getPlayerEnum() == simulation.getPlayingPlayerEnum()) { //On est ici dans le cas du joueur
                for (Movement movement : simulation.getAllPossibleMoves()) {
                    alpha = Math.max(alpha, minimax(getNextSimulation(simulation, movement), alpha, beta, depth + 1, maxDepth));
                    if (alpha >= beta) { //Elagage
                        return beta;
                    }
                }

                return alpha; // On retourne la valeur alpha si elle est maximale
            }
            else { // On est ici dans le cas de l'adversaire
                for (Movement movement: simulation.getAllPossibleMoves()) {
                    beta = Math.min(beta, minimax(getNextSimulation(simulation, movement), alpha, beta, depth + 1, maxDepth));
                    if (alpha >= beta) { //Elagage
                        return alpha;
                    }
                }

                return beta; //On retourne la valeur beta si elle est minimale
            }
        }
    }

    private Simulation getNextSimulation(Simulation simulation, Movement movement) {
        Simulation newSimulation = new Simulation(simulation); // On clone la simulation pour ne pas perdre celle passée en paramètre
        newSimulation.move(movement.getFromC(), movement.getFromL(), movement.getToC(), movement.getToL()); // Obtention du nouvel état de la simulation copiée
        return newSimulation;
    }

    public abstract double heuristic(Simulation simulation, PlayerEnum playerEnum);
}
