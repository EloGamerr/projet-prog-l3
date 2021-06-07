package fr.prog.tablut.controller.game.ia.Heuristique;

import java.util.Random;

import fr.prog.tablut.model.game.CellContent;

public class HeuristiqueAleatoire extends Heuristique{

    @Override
    protected int heuristique(CellContent[][] grille) {
        // TODO Auto-generated method stub
        return new Random().nextInt(100);
    }
    
}
