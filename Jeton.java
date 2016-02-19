package new_idea;

public class Jeton {

	private int valeur;

	private Joueur propriétaire;

	public int getValue() {
		return valeur;
	}

	public void increaseValue() {
		int currentValue = getValue();
		currentValue += 1;
		setValue(currentValue);
	}

	public void setValue(int valeur) {
		this.valeur = valeur;
	}

	public Joueur getProprietaire() {
		return propriétaire;
	}

	public void setProprietaire(Joueur propriétaire) {
		this.propriétaire = propriétaire;
	}


}
