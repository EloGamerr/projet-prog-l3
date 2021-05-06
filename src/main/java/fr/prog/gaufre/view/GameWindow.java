package fr.prog.gaufre.view;

import fr.prog.gaufre.model.TimonModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameWindow extends JComponent implements PropertyChangeListener{
	TimonModel model;
    ViewNiveau vn;
    int largeur,hauteur;
    Graphics2D drawable;
    
    public GameWindow(TimonModel model) {
    	model.addPropertyChangeListener(this);
    	vn = new ViewNiveau(this);
    	this.model = model;
	}

    @Override
    protected void paintComponent(Graphics graphics) {
        drawable = (Graphics2D) graphics;
        
    	largeur = getSize().width;
    	hauteur = getSize().height;
    	
    	vn.dessiner_niveau();

    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.repaint();
	}
	
	public int hauteur() {
		return hauteur;
	}
	
	public int largeur() {
		return largeur;
	}
	
	public int longueur_case() {
		return vn.longueur_case();
	}
	
	public int largeur_case(){
		return vn.largeur_case();
	}
}
