package fr.prog.gaufre.controller;

import java.util.ArrayList;


public class ConfigurationGraph {
	
	private Noeud racine;
	
	
	
	ConfigurationGraph(){
		racine = new Noeud();
	}
	
	ConfigurationGraph(int v){
		racine = new Noeud(v);
	}
	
	ConfigurationGraph(int v, ArrayList<Noeud> sons){
		racine = new Noeud(v);
		racine.setSons(sons);
	}
	
	public Noeud racine() {
		return racine;
	}
	
	


}
