package new_idea;

import java.awt.Color;

public class Joueur {

	private int numeroJoueur;

	private String pseudo;

	private Color couleur;

	private boolean estHumain;

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public int getNumeroJoueur() {
		return numeroJoueur;
	}

	public void setNumeroJoueur(int numeroJoueur) {
		this.numeroJoueur = numeroJoueur;
	}

	public boolean isEstHumain() {
		return estHumain;
	}

	public void setEstHumain(boolean estHumain) {
		this.estHumain = estHumain;
	}

	public Joueur() {



	}

	public Joueur(int numeroJoueur) {
		setNumeroJoueur(numeroJoueur);
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

}

