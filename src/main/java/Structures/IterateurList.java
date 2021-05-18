package Structures;

public class IterateurList<Riri> extends Iterateur<Riri> {
	Maillon<Riri> courant, precedent, arrierePrecedent;
	SequenceList<Riri> s;

	IterateurList(SequenceList<Riri> l) {
		courant = l.tete;
		s = l;
	}

	@Override
	public boolean aProchain() {
		return courant != null;
	}

	@Override
	public Riri prochain() {
		super.prochain();
		Riri resultat = courant.element;
		arrierePrecedent = precedent;
		precedent = courant;
		courant = courant.suivant;
		return resultat;
	}

	@Override
	public void supprime() {
		super.supprime();
		if (arrierePrecedent == null) {
			s.tete = courant;
		} else {
			arrierePrecedent.suivant = courant;
		}
		// Correction après la vidéo
		if (s.queue == precedent)
			s.queue = arrierePrecedent;
		precedent = arrierePrecedent;
	}
}
