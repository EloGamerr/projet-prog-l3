package fr.prog.gaufre.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class TimonModel implements Model {
	public short[][] grille;
	int x;
	int y;
	boolean fini = false;
	private PropertyChangeSupport support;
	
	public TimonModel(int x, int y) {
		grille = new short[x][y];
		this.x = x;
		this.y = x;
		create_grid();
		this.support = new PropertyChangeSupport(this);
	}
	
	public void affiche() {
		for(int j = 0; j < y; j++) {
			for(int i = 0; i < x; i++) {
				System.out.print(grille[i][j]);
			}
			System.out.println();
		}
	}
	
	private void create_grid() {
		for(int i = 0; i < x ; i++) {
			for(int j = 0; j < y; j++) {
				grille[i][j] = 0;
			}
		}
	}
	
	public void reset() {
		create_grid();
	}

	@Override
	public boolean play(int l, int c) {
		if(grille[l-1][c-1] == 1) return false;
		if(l == x && c == y) fini = true;
		for(int i2 = 0; i2 < l; i2++) for(int j2 = 0; j2 < c; j2++) {
			grille[i2][j2] = 1;
		}
		return true;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }
		
	public short[][] get_grid() {
		return grille;
	}
	
	public int get_x() {
		return x;
	}
	
	public int get_y() {
		return y;
	}
}
