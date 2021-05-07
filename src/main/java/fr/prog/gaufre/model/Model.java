package fr.prog.gaufre.model;

public interface Model {
    boolean play(int l, int c); //return l
    void reset();
    short[][] get_grid();
    
    int get_x();
    int get_y();
}
