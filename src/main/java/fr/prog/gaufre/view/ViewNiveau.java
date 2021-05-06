package fr.prog.gaufre.view;

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
		
		hauteur_case = gamewindow.largeur() / 10;
		largeur_case = gamewindow.hauteur() / 10;
		
		tracer_grille();
		tracer_cercle(0,0);
	}
	
	public void tracer_grille() {
		for(int i = 0; i < gamewindow.hauteur/hauteur_case() ; i++) {
			gamewindow.drawable.drawLine(0, i * hauteur_case(), gamewindow.largeur, i * hauteur_case());
		}
		
		for(int j = 0; j < gamewindow.largeur/largeur_case(); j++) {
			gamewindow.drawable.drawLine(j * largeur_case(), 0, j * largeur_case(), gamewindow.hauteur);
		}
	}
	
	public void tracer_cercle(int x, int y) {
		gamewindow.drawable.drawOval(x * gamewindow.largeur + 2, y * gamewindow.largeur + 2, hauteur_case() - 5, largeur_case() - 5);
	}
}
