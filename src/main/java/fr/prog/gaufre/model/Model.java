package fr.prog.gaufre.model;

public interface Model {
    boolean play(int c, int l); //return true if view need update
    short[][] get_grid();
    
    int get_x();
    int get_y();

    boolean newGame();
    boolean rollback();
    boolean save();
    boolean load();
}
