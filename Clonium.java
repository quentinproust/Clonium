package new_idea;

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

	/**
	 * Create the frame.
	 */

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
		// Create second list of components

		// create Players List
		creerJoueur(listeJoueur, nbJoueur);
		entrezPseudo(listeJoueur);
		quiEstUnOrdi(listeJoueur);

		// Create List of components
		for (int id = 0; id < 64; id++) {

			Case laCase = creerCase(id);
			listeComposantPlateau.add(laCase);

			// Add every Case to the JFrame
			humanMachineInterface.add(laCase);
		}


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
					int newValue = currentCase.getValue();

					// Test s'il y a un jeton.
					if (newValue == '0') {

						JOptionPane.showMessageDialog(null, "Vous ne pouvez pas jouer ici car il n'y a pas de jeton",
								"Error", JOptionPane.WARNING_MESSAGE);

					} else if (newValue >= '1' && newValue < '3') {

						currentCase.increaseValue();

					} else if (newValue >= '3') {

						currentCase.resetValue();
						int explosingCase = currentCase.getId();

						List<Case> casesAdjacentes = getCasesAdjacentes(explosingCase);

						for (Case c : casesAdjacentes) {
							c.increaseValue();
							System.out.println(c.getId() + "->" + c.getValue());

						}
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

	private void placerLesJoueur() {
		ArrayList<Case> premierJeton = new ArrayList<Case>();
		premierJeton.add(listeComposantPlateau.get(10));

		for (JButton Case : premierJeton) {

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
