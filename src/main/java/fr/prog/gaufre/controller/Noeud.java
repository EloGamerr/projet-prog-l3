package fr.prog.gaufre.controller;

import java.util.ArrayList;
import java.util.List;

public class Noeud {

	private int value;
	private Noeud father;
	private List<Noeud> sons;

	
	public Noeud() {
		value = 0;
		father = null;
		sons = new ArrayList<Noeud>();
	}
	
	
	public Noeud(int v) {
		value = v;
		father = null;
		sons = new ArrayList<Noeud>();
	}
	
	public Noeud(int v, Noeud f) {
		value = v;
		father = f;
		sons = new ArrayList<Noeud>();
	}
	
	public boolean isEmpty() {
		return sons.isEmpty();
	}
	
	public int value() {
		return value;
	}
	
	public Noeud father() {
		return father;
	}
	
	public List<Noeud> sons(){
		return sons;
	}
	
	public Noeud sons(int i) {
		return sons.get(i);
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public void setFather(Noeud f) {
		father = f;
	}
	
	public void setSons(List<Noeud> l) {
		sons = l;
	}
	
	public void addSon(Noeud son) {
		sons.add(son);
	}
	
	public void removeSon(int i) {
		sons.remove(i);
	}
}
