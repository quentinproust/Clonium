package new_idea;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Clonium extends JFrame {

	private static final long serialVersionUID = 173486282482136772L;

	private JPanel humanMachineInterface;

	private JLayeredPane layeredPane;

	private ArrayList<Case> listeComposantPlateau = new ArrayList<Case>();

	private ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();

	private ArrayList<Color> listeCouleur = new ArrayList<Color>();

	private short numeroJoueur;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ChooseNumberPlayer choixJoueur = new ChooseNumberPlayer();
					choixJoueur.setVisible(true);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Clonium() {

	}

	public Clonium(int nbJoueur) {
		// All default operations

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// quitting button.
		setBounds(100, 100, 680, 475); // 880,675
		humanMachineInterface = new JPanel();


		// Create border
		humanMachineInterface.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Allows JFrame to have Component(s)
		setContentPane(humanMachineInterface);
		humanMachineInterface.setLayout(null);
		humanMachineInterface.add(getLayeredPane_1());
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);

		// Create Players List
		creerJoueur(listeJoueur, nbJoueur);
		entrezPseudo(listeJoueur);
		quiEstUnOrdi(listeJoueur);
		remplirCouleur();
		attribuerCouleur();
		
		// début de partie
		numeroJoueur = 0;
		this.setTitle("tour de " + listeJoueur.get(numeroJoueur).getPseudo() + ".");

		// Create List of components
		for (int id = 0; id < 64; id++) {

			Case laCase = creerCase(id);
			if (id == 9 || id == 14 || id == 49 || id == 54) {
				laCase.setBackground(Color.red);
			}
			listeComposantPlateau.add(laCase);

			// Add every Case to the JFrame
			humanMachineInterface.add(laCase);
		}

		JOptionPane.showMessageDialog(humanMachineInterface,
				"Appuyez sur les cases rouges pour choisir ou vous alez commencez", "Information",
				JOptionPane.INFORMATION_MESSAGE);

	}

	private Case creerCase(int id) {
		// Creating new Object
		Case laCase = new Case(id);
		attachActionToCase(laCase);

		return laCase;
	}

	private void attachActionToCase(Case laCase) {
		// Add actions to my object
		laCase.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent leftClick) {

				if (leftClick != null) {

					// Récupération de la valeur du jeton.
					Case currentCase = (Case) leftClick.getSource();
					int currentValue = currentCase.getValue();
					// Récupération du numero du proprietaire.
					Joueur currentOwner = currentCase.getOwner();

					if (currentCase.getBackground() == Color.RED
							&& etesVousPlacer(listeComposantPlateau, listeJoueur)) {


						currentCase.increaseValue();

						currentCase.setOwner(listeJoueur.get(numeroJoueur));
						enleverRouge(currentCase);
						currentCase.setBackground(listeJoueur.get(numeroJoueur).getCouleur());
						for (Case c : listeComposantPlateau) {
							if (c.getBackground() == Color.RED) {
								enleverRouge(c);
							}
						}

						tourSuivant(listeJoueur);

					} else if (currentCase.getBackground() == Color.RED
							&& !etesVousPlacer(listeComposantPlateau, listeJoueur)) {


						currentCase.increaseValue();
						currentCase.setOwner(listeJoueur.get(numeroJoueur));
						currentCase.setBackground(listeJoueur.get(numeroJoueur).getCouleur());
						tourSuivant(listeJoueur);

					} else if (currentOwner != listeJoueur.get(numeroJoueur) && listeJoueur.get(numeroJoueur) != null
							&& currentValue != '0') {

						JOptionPane.showMessageDialog(humanMachineInterface,
								"Vous ne pouvez pas jouer ici car le jeton dans cette case appartient à quelqu'un d'autre",
								"Error", JOptionPane.WARNING_MESSAGE);

					} else if (currentValue == '0') {


						JOptionPane.showMessageDialog(humanMachineInterface,
								"Vous ne pouvez pas jouer ici car il n'y a pas de jeton",
								"Error", JOptionPane.WARNING_MESSAGE);

					} else if (currentValue >= '1' && currentValue < '3') {

						currentCase.increaseValue();
						tourSuivant(listeJoueur);

					} else if (currentValue >= '3') {

						currentCase.resetValue();
						int explosingCase = currentCase.getId();
						explosion(explosingCase);

						tourSuivant(listeJoueur);
					}
				}
			}
		});
	}

	private JLayeredPane getLayeredPane_1() {
		if (layeredPane == null) {
			layeredPane = new JLayeredPane();
			layeredPane.setBounds(181, 81, 1, 1);
		}
		return layeredPane;
	}

	private void explosion(int explosingCase) {

		List<Case> casesAdjacentes = getCasesAdjacentes(explosingCase);

		for (Case c : casesAdjacentes) {
			c.setOwner(listeJoueur.get(numeroJoueur));
			c.setBackground(c.getOwner().getCouleur());
			c.increaseValue();
			System.out.println(c.getId() + "->" + c.getValue());

			if (c.getValue() >= '4') {
				c.resetValue();
				explosion(c.getId());

			}
		}

		eliminerJoueur();

	}

	private List<Case> getCasesAdjacentes(int explosingCase) {

		List<Case> cases = new ArrayList<>();
		System.out.println(listeComposantPlateau.contains(explosingCase - 1));
		if (isValidPosition(explosingCase - 1)) {
			cases.add(listeComposantPlateau.get(explosingCase - 1));
		}
		if (isValidPosition(explosingCase + 1)) {
			cases.add(listeComposantPlateau.get(explosingCase + 1));
		}
		if (isValidPosition(explosingCase - 8)) {
			cases.add(listeComposantPlateau.get(explosingCase - 8));
		}
		if (isValidPosition(explosingCase + 8)) {
			cases.add(listeComposantPlateau.get(explosingCase + 8));
		}

		return cases;
	}

	private boolean isValidPosition(int position) {
		return position >= 0 && position < listeComposantPlateau.size();
	}

	private void creerJoueur(ArrayList<Joueur> listeJoueur, int nbJoueur) {
		for (int numeroJoueur = 0; numeroJoueur < nbJoueur; numeroJoueur++) {
			Joueur player = new Joueur(numeroJoueur + 1);
			listeJoueur.add(player);
		}
	}

	private void entrezPseudo(ArrayList<Joueur> listeJoueur) {

		for (Joueur joueur : listeJoueur) {
			
			String playerName = null;
			playerName = JOptionPane
.showInputDialog("Entrez le pseudo du joueur " + joueur.getNumeroJoueur() + ". ");
			if (playerName == null) {
				playerName = "Anonymous " + joueur.getNumeroJoueur();
			}
			joueur.setPseudo(playerName);

		}

	}

	private void quiEstUnOrdi(ArrayList<Joueur> listeJoueur) {

		for (Joueur joueur : listeJoueur) {

			int yes = JOptionPane.showConfirmDialog(null, "Shall this player be controlled by computer", "Warning",
				DO_NOTHING_ON_CLOSE);
			if (yes == 0) {
				joueur.setEstHumain(true);
			} else {
				joueur.setEstHumain(false);
			}

		}
	}

	private boolean etesVousPlacer(ArrayList<Case> listeComposantPlateau,ArrayList<Joueur> listeJoueur) {

		int nombreJoueur = listeJoueur.size();
		int nbCaseA1 = 0;

		for(Case bouton : listeComposantPlateau){

			if (bouton.getValue() == '1') {
				nbCaseA1++;


			}
			

		}
		System.out.println(nombreJoueur == nbCaseA1 + 1);
		return nombreJoueur == nbCaseA1 + 1;

	}

	private void enleverRouge(JButton bouton) {

			if (bouton.getBackground() == Color.RED) {
			bouton.setBackground(null);
			}

	}

	private void tourSuivant(ArrayList<Joueur> listeJoueur) {
		if (numeroJoueur != listeJoueur.size() - 1) {
			numeroJoueur++;
		} else {
			numeroJoueur = 0;
		}
		String playerName = listeJoueur.get(numeroJoueur).getPseudo();
		System.out.println(numeroJoueur);


		this.setTitle("tour de " + playerName + ".");
		// JOptionPane.showMessageDialog(humanMachineInterface, "C'est
		// maintenant le tour de " + playerName, "Information",
		// JOptionPane.INFORMATION_MESSAGE);

	}

	private void remplirCouleur() {
		listeCouleur.add(Color.YELLOW);
		listeCouleur.add(Color.CYAN);
		listeCouleur.add(Color.GREEN);
		listeCouleur.add(Color.WHITE);

	}

	private void attribuerCouleur() {
		for (Joueur joueur : listeJoueur) {

			int numeroJoueur = listeJoueur.indexOf(joueur);
			joueur.setCouleur(listeCouleur.get(numeroJoueur));

		}

	}

	private void eliminerJoueur() {
		int reponse = -10000;
		boolean uneFois = false;
		
		ArrayList<Boolean> listeJoueurNonEliminee = new ArrayList<Boolean>();

		for (int numeroJoueur = 0; numeroJoueur < listeJoueur.size(); numeroJoueur++) {
			listeJoueurNonEliminee.add(new Boolean(false));
		}
		
		for(Case c : listeComposantPlateau ){
			for (Joueur joueur : listeJoueur) {
				if (c.getOwner() == joueur && c.getValue() != '0') {
					listeJoueurNonEliminee.set(listeJoueur.indexOf(joueur), true);
				}
			}
		}
		
		for (Boolean estElimine : listeJoueurNonEliminee) {
			if (estElimine.equals(false)) {
				int joueurElimine = listeJoueurNonEliminee.indexOf(estElimine);
				listeJoueur.remove(joueurElimine);
			}
		}
		if (listeJoueur.size() == 1 && !uneFois) {
			
			uneFois = true;
			reponse = JOptionPane.showConfirmDialog(humanMachineInterface,
					listeJoueur.get(0).getPseudo() + " à gagner la partie. Voulez vous rejouez? ");

		}
		if (reponse == 0) {

			ChooseNumberPlayer choixJoueur = new ChooseNumberPlayer();
			choixJoueur.setVisible(true);
			this.dispose();

		} else if (reponse == 1 || reponse == 2) {
			this.dispose();
		} else {

		}
		
	}



}

/**
 * 
 * Bug du message application terminée
 * 
 * Bug teleportation de Jeton
 * 
 * Update l'interface graphique pour afficher les infos sur chaque joueur. 5)
 * 
 * Couleur + titre Jframe
 * 
 */

/**
 * Enfin nous verrons pour implementer differentes choses comme choisir la
 * taille du terrain ajouter un Jmenu pour sauvegarder une partie finie dans un
 * fichier pour pouvoir la rejouer coup par coup et pour finir un service pour
 * passer d'une JFrame a une autre pour que tout ressemble à un jeu fini et
 * complet enfin si on a le temps on étudieras la question de l'appli Android.
 * 
 */
