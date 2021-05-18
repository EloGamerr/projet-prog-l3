package Structures;

import Structures.Iterateur;

public interface Sequence<Toto> {
	void insereTete(Toto element);
	void addTail(Toto element);
	Toto extraitTete();
	boolean estVide();
	Iterateur<Toto> iterateur();
}
