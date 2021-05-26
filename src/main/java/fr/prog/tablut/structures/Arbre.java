package fr.prog.tablut.structures;

import java.util.List;

public class Arbre {
	public int heuristique;
	public List<Arbre> allChild;
	public Arbre Father;
	
	public Arbre(List<Arbre> list, Arbre Father) {
		// TODO Auto-generated constructor stub
		this.allChild = list;
		this.Father = Father;
	}
	
	public boolean isRoot() {
		return Father == null;
	}
	
	public boolean isLeaf() {
		return allChild == null;
	}
	
	
}
