package fr.prog.gaufre.controller;

import java.util.HashMap;
import java.util.List;

import fr.prog.gaufre.controller.configurations.Noeud;
import fr.prog.gaufre.model.Couple;
import fr.prog.gaufre.model.Model;

public class IAminmax extends IA{

	private HashMap<Noeud, Noeud> configurations;
	
	public IAminmax(Model model) {
		super(model);
		
		this.configurations = new HashMap<>();
	
		this.createConfigurations(new Noeud(model.clone()));
	}

	private void createConfigurations(Noeud noeud) {
		/*this.configurations.put(noeud, noeud);
		
		for(Couple<Integer, Integer> couple : noeud.getModel().playableCells()) {
			Model newModel = noeud.getModel().clone();
			newModel.play(couple.getFirst(), couple.getSecond(), newModel.getPlayingPlayer());
			Noeud noeud2 = new Noeud(newModel);
			if(!this.configurations.containsKey(noeud2)) {
				noeud.addConfiguration(noeud2);
				this.createConfigurations(noeud2);
			}
			else {
				noeud.addConfiguration(this.configurations.get(noeud2));
			}
		}*/
	}
	
	@Override
	public Couple<Integer, Integer> play() {
		/*Noeud noeud = this.configurations.get(new Noeud(model));
		
		if(noeud == null) return null;
		
		
		if(noeud.getNextConfigurations().size() == 1) {
			System.out.println("Configuration perdante");
			return noeud.getNextConfigurations().iterator().next();
		}*/
		return null;
	}
}
