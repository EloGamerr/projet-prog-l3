package fr.prog.tablut.model.game;

public enum Orientation {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    int dl;
    int dc;
    
    Orientation(int dl, int dc) {
        this.dl = dl;
        this.dc = dc;
    }

    public int getDl() {
        return dl;
    }

    public int getDc() {
        return dc;
    }
}
