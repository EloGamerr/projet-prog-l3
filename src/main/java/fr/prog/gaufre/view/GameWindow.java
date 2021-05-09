package fr.prog.gaufre.view;

import fr.prog.gaufre.model.Model;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GameWindow extends JComponent {
	Model model;
    ViewNiveau vn;
    int largeur,hauteur;
    Graphics2D drawable;
    
    public GameWindow(Model model) {
    	vn = new ViewNiveau(this);
    	this.model = model;
	}
    
    public void actualize() {
    	repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        drawable = (Graphics2D) graphics;
        
    	largeur = getSize().width;
    	hauteur = getSize().height;
    	
    	vn.dessiner_niveau();

    }
	
	public int hauteur() {
		return hauteur;
	}
	
	public int largeur() {
		return largeur;
	}
	
	public int longueur_case() {
		return vn.hauteur_case();
	}
	
	public int largeur_case(){
		return vn.largeur_case();
	}
}
