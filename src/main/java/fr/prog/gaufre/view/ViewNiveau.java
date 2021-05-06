package fr.prog.gaufre.view;

public class ViewNiveau {
	GameWindow gamewindow;
	int longueur_case,largeur_case;
	public ViewNiveau(GameWindow g) {
		this.gamewindow = g;
	}
	
	public int largeur_case(){
		return largeur_case;
	}
	
	public int longueur_case(){
		return longueur_case;
	}
	
	public void dessiner_niveau() {
		
		longueur_case = gamewindow.largeur() / 10;
		largeur_case = gamewindow.hauteur() / 10;
		
		tracer_grille();
		tracer_cercle(0,0);
	}
	
	public void tracer_grille() {
		
	}
	
	public void tracer_cercle(int x, int y) {
		gamewindow.drawable.drawOval(x * gamewindow.largeur, y * gamewindow.largeur, longueur_case(), largeur_case());
	}
}
