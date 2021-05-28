package fr.prog.tablut.structures;

import java.util.List;

public class Arbre {
	public int heuristique;
	public List<Arbre> allChild;
	public Arbre Father;
	
	public Arbre(List<Arbre> list, Arbre parent) {
		allChild = list;
		Father = parent;
	}
	
	public boolean isRoot() {
		return Father == null;
	}
	
	public boolean isLeaf() {
		return allChild == null;
	}
	
	
}
