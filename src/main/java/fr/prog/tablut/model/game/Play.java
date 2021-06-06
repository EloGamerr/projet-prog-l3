package fr.prog.tablut.model.game;

import java.awt.Point;

import java.util.HashMap;

public class Play {
    private final Movement movement;
    private final HashMap<Point, CellContent> modifiedOldCellContents;
    private final HashMap<Point, CellContent> modifiedNewCellContents;

    public Play(Movement movement) {
        this.movement = movement;
        modifiedOldCellContents = new HashMap<>();
        modifiedNewCellContents = new HashMap<>();
    }

    public Movement getMovement() {
        return movement;
    }

    public void putModifiedOldCellContent(Point cell, CellContent cellContent) {
        modifiedOldCellContents.put(cell, cellContent);
    }

    /**
     *
     * @return each cell which has been modified after the move, the value of the entry corresponds to the cell content before the play
     */
    public HashMap<Point, CellContent> getModifiedOldCellContents() {
        return modifiedOldCellContents;
    }

    public void putModifiedNewCellContent(Point cell, CellContent cellContent) {
        modifiedNewCellContents.put(cell, cellContent);
    }

    /**
     *
     * @return each cell which has been modified after the move, the value of the entry corresponds to the cell content after the play
     */
    public HashMap<Point, CellContent> getModifiedNewCellContents() {
        return modifiedNewCellContents;
    }
}
