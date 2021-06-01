package fr.prog.tablut.model.game;

import fr.prog.tablut.structures.Couple;

import java.util.HashMap;

public class Play {
    private final Movement movement;
    private final HashMap<Couple<Integer, Integer>, CellContent> modifiedOldCellContents;
    private final HashMap<Couple<Integer, Integer>, CellContent> modifiedNewCellContents;

    public Play(Movement movement) {
        this.movement = movement;
        modifiedOldCellContents = new HashMap<>();
        modifiedNewCellContents = new HashMap<>();
    }

    public Movement getMovement() {
        return movement;
    }

    public void putModifiedOldCellContent(Couple<Integer, Integer> cell, CellContent cellContent) {
        modifiedOldCellContents.put(cell, cellContent);
    }

    public HashMap<Couple<Integer, Integer>, CellContent> getModifiedOldCellContents() {
        return modifiedOldCellContents;
    }

    public void putModifiedNewCellContent(Couple<Integer, Integer> cell, CellContent cellContent) {
        modifiedNewCellContents.put(cell, cellContent);
    }

    public HashMap<Couple<Integer, Integer>, CellContent> getModifiedNewCellContents() {
        return modifiedNewCellContents;
    }
}
