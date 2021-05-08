package fr.prog.gaufre.model;

import java.util.ArrayList;
import java.util.List;

public class Play {
    private List<Couple<Integer, Integer>> couples;

    public Play() {
        this.couples = new ArrayList<>();
    }

    public List<Couple<Integer, Integer>> getCouples() {
        return couples;
    }

    public void addCouple(int x, int y) {
        this.couples.add(new Couple<>(x, y));
    }
}
