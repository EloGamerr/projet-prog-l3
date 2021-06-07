package fr.prog.tablut.model.game;

public enum Orientation {
    NORTH(0,-1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    int dl;
    int dc;
    
    Orientation(int dc, int dl) {
        this.dc = dc;
        this.dl = dl;

    }

    public int getDl() {
        return dl;
    }

    public int getDc() {
        return dc;
    }
}
