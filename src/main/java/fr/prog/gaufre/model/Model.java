package fr.prog.gaufre.model;

public interface Model {
    public boolean play(int l, int c); //return l
    public void reset();
    public short[][] get_grid();
    
    public int get_x();
    public int get_y();
}
