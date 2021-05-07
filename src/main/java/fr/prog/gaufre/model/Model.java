package fr.prog.gaufre.model;

public interface Model {
    boolean play(int c, int l); //return true if view need update
    void reset();
    short[][] get_grid();
    
    int get_x();
    int get_y();
}
