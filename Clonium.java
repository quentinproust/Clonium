package new_idea;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Clonium extends JFrame {

	private static final long serialVersionUID = 173486282482136772L;

	private JPanel humanMachineInterface;

	private ArrayList<Case> listeComposantPlateau = new ArrayList<Case>();

	private ArrayList<Joueur> listeJoueur = new ArrayList<Joueur>();

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
		humanMachineInterface.add(createLayeredPane());
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);

		// Create Players List
		creerJoueur(nbJoueur);
		entrezPseudo();
		quiEstUnOrdi();
		numeroJoueur = 0;

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
				// Récupération de la valeur du jeton.
				Case currentCase = (Case) leftClick.getSource();
				int currentValue = currentCase.getValue();
				// Récupération du numero du proprietaire.
				Joueur currentOwner = currentCase.getOwner();

				if (currentCase.getBackground() == Color.RED
						&& etesVousPlacer(listeComposantPlateau)) {

					currentCase.increaseValue();
					currentCase.setOwner(listeJoueur.get(numeroJoueur));
					enleverRouge(listeComposantPlateau);
					tourSuivant();

				} else if (currentCase.getBackground() == Color.RED
						&& !etesVousPlacer(listeComposantPlateau)) {

					currentCase.increaseValue();
					currentCase.setOwner(listeJoueur.get(numeroJoueur));
					tourSuivant();

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
					tourSuivant();

				} else if (currentValue >= '3') {

					currentCase.resetValue();
					int explosingCase = currentCase.getId();
					explosion(explosingCase);

					tourSuivant();
				}
			}
		});
	}

	private JLayeredPane createLayeredPane() {
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(181, 81, 1, 1);
		return layeredPane;
	}

	private void explosion(int explosingCase) {
		List<Case> casesAdjacentes = getCasesAdjacentes(explosingCase);

		for (Case c : casesAdjacentes) {
			c.setOwner(listeJoueur.get(numeroJoueur));
			c.increaseValue();
			System.out.println(c.getId() + "->" + c.getValue());
			if (c.getValue() >= '4') {
				c.resetValue();
				explosion(c.getId());

			}
		}
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

	private void creerJoueur(int nbJoueur) {
		for (int numeroJoueur = 0; numeroJoueur < nbJoueur; numeroJoueur++) {
			Joueur player = new Joueur(numeroJoueur + 1);
			listeJoueur.add(player);
		}
	}

	private void entrezPseudo() {
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

	private void quiEstUnOrdi() {

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

	private boolean etesVousPlacer(ArrayList<Case> listeComposantPlateau) {
		int nombreJoueur = listeJoueur.size();
		int nbCaseA1 = 0;
		for(Case bouton : listeComposantPlateau){
			if (bouton.getValue() == '1') {
				nbCaseA1++;
			}
			
		}
		return nombreJoueur == nbCaseA1 + 1;

	}

	private void enleverRouge(ArrayList<Case> listeComposantPlateau) {
		for (Case bouton : listeComposantPlateau) {
			if (bouton.getBackground() == Color.RED) {
				bouton.setBackground(new Color(240, 240, 240));
			}
		}
	}

	private void tourSuivant() {
		if (numeroJoueur != listeJoueur.size() - 1) {
			numeroJoueur++;
		} else {
			numeroJoueur = 0;
		}

	}

}

/**
 * Enfin nous verrons pour implementer differentes choses comme choisir la
 * taille du terrain ajouter un Jmenu pour sauvegarder une partie finie dans un
 * fichier pour pouvoir la rejouer coup par coup et pour finir un service pour
 * passer d'une JFrame a une autre pour que tout ressemble à un jeu fini et
 * complet enfin si on a le temps on étudieras la question de l'appli Android.
 * 
 */
