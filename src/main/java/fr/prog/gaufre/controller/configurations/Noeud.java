package fr.prog.gaufre.controller.configurations;

import java.util.HashSet;

import fr.prog.gaufre.model.Model;

public class Noeud {
	private Model model;
	private HashSet<Noeud> nextConfigurations;
	
	public Noeud(Model model) {
		this.model = model;
		this.nextConfigurations = new HashSet<>();
	}
	
	public void addConfiguration(Noeud noeud) {
		this.nextConfigurations.add(noeud);
	}
	
	public Model getModel() {
		return this.model;
	}
	
	public HashSet<Noeud> getNextConfigurations() {
		return this.nextConfigurations;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noeud noeud = (Noeud) o;
       
        if(model.get_x() != noeud.getModel().get_x() || model.get_y() != noeud.getModel().get_y()) return false;
        
        for(int i = 0 ; i < model.get_x() ; i++) for(int j = 0 ; j < model.get_y() ; j++) {
        	if(model.get_grid()[i][j] != noeud.getModel().get_grid()[i][j])
        		return false;
        }
        
        return true;
    }
	
	@Override
	public int hashCode() {
		int hash = 1;
	
        for(int i = 0 ; i < model.get_x() ; i++) for(int j = 0 ; j < model.get_y() ; j++) {
        	hash = hash * 31 + model.get_grid()[i][j];
        }

        return hash;
	}
}
