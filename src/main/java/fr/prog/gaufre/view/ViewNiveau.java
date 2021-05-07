package fr.prog.gaufre.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class ViewNiveau {
	GameWindow gamewindow;
	int hauteur_case,largeur_case;
	public ViewNiveau(GameWindow g) {
		this.gamewindow = g;
	}
	
	public int largeur_case(){
		return largeur_case;
	}
	
	public int hauteur_case(){
		return hauteur_case;
	}
	
	public void dessiner_niveau() {
		
		hauteur_case = gamewindow.largeur() / gamewindow.model.get_x();
		largeur_case = gamewindow.hauteur() / gamewindow.model.get_y();
		
		tracer_grille();
		tracer_cercle(0,0);
	}
	
	public void tracer_grille() {
		/*
		for(int i = 0; i < gamewindow.hauteur/hauteur_case() ; i++) {
			gamewindow.drawable.drawLine(0, i * hauteur_case(), gamewindow.largeur, i * hauteur_case());
		}
		
		for(int j = 0; j < gamewindow.largeur/largeur_case(); j++) {
			gamewindow.drawable.drawLine(j * largeur_case(), 0, j * largeur_case(), gamewindow.hauteur);
		}
		*/
		Stroke oldStroke = gamewindow.drawable.getStroke();
		gamewindow.drawable.setStroke(new BasicStroke(5));
		short[][] g = gamewindow.model.get_grid();
		for(int i = 0; i < gamewindow.model.get_x(); i++) {
			for(int j = 0; j < gamewindow.model.get_y(); j++) {
				
				if(g[i][j] == 0) {
					gamewindow.drawable.setColor(new Color(255, 180, 0));
				}
				else {
					gamewindow.drawable.setColor(Color.white);
				}
				gamewindow.drawable.fillRect(i * hauteur_case(), j * largeur_case(), hauteur_case, largeur_case );
				gamewindow.drawable.setColor(Color.orange);
				gamewindow.drawable.drawRect(i * hauteur_case(), j * largeur_case(), i * hauteur_case() + hauteur_case(), j * largeur_case() + largeur_case());
			}
		}
		
		gamewindow.drawable.setStroke(oldStroke);
		
	}
	
	public void tracer_cercle(int x, int y) {
		gamewindow.drawable.setColor(Color.green);
		gamewindow.drawable.fillOval(x * gamewindow.largeur + 2, y * gamewindow.largeur + 2, hauteur_case() - 5, largeur_case() - 5);
	}
}
