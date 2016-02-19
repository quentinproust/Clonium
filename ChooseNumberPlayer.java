package new_idea;

import java.awt.Label;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChooseNumberPlayer extends JFrame {

	private static final long serialVersionUID = -5283744935972905768L;

	private ArrayList<JButton> listeBouton = new ArrayList<JButton>();

	private ArrayList<Joueur> listeDesJoueurs = new ArrayList<Joueur>();

	private JPanel numberOfPlayerFrame;

	private Label label;

	private JButton boutonConfirmation;

	private short numberOfPlayer;

	public ArrayList<Joueur> getListeDesJoueurs() {
		return listeDesJoueurs;
	}

	public void setListeDesJoueurs(ArrayList<Joueur> listeDesJoueurs) {
		this.listeDesJoueurs = listeDesJoueurs;
	}

	public short getNumberOfPlayer() {
		return numberOfPlayer;
	}

	private void setNumberOfPlayer(short numberOfPlayer) {
		this.numberOfPlayer = numberOfPlayer;
	}

	public ChooseNumberPlayer() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 200, 325);
		numberOfPlayerFrame = new JPanel();
		numberOfPlayerFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(numberOfPlayerFrame);
		numberOfPlayerFrame.setLayout(null);
		numberOfPlayerFrame.add(getLabel());
		setLocationRelativeTo(null);
		for (int numeroBouton = 0; numeroBouton < 3; numeroBouton++) {
			JButton laCasette = null;
			listeBouton.add(creerBoutonNbJoueur(laCasette, numeroBouton));
			numberOfPlayerFrame.add(listeBouton.get(numeroBouton));
			ajouterAction(listeBouton.get(numeroBouton));
		}
		listeBouton.add(getBoutonConfirmation());
		numberOfPlayerFrame.add(listeBouton.get(listeBouton.size() - 1));
	}

	private void ajouterAction(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent leftClick) {
				JButton currentlyClicked = ((JButton) leftClick.getSource());

				if (leftClick != null) {
					// 2 car on commence avec 2 joueur (jeu 2-4 joueurs)
					short nbJoueur = (short) (listeBouton.indexOf(currentlyClicked) + 2);
					setNumberOfPlayer(nbJoueur);


				}
				
			}
		});
		
	}
	
	private Label getLabel() {
		if (label == null) {
			label = new Label("Combien de joueur vont jouer ?");
			label.setBounds(0, 10, 190, 30);
		}
		return label;
	}

	private JButton creerBoutonNbJoueur(JButton choixParticipants, int numeroBouton) {
		if (choixParticipants == null) {
			choixParticipants = new JButton((numeroBouton + 2) + " joueurs");
			choixParticipants.setBounds(10, 50 + (60 * numeroBouton), 160, 50);
		}
		return choixParticipants;
	}

	private JButton getBoutonConfirmation() {
		if (boutonConfirmation == null) {
			boutonConfirmation = new JButton("OK");
			boutonConfirmation.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent leftClick) {
					JButton currentlyClicked = ((JButton) leftClick.getSource());

					if (leftClick != null && numberOfPlayer != 0) {
						try {
							Clonium plateau = new Clonium(getNumberOfPlayer());
							plateau.setVisible(true);
							((Window) currentlyClicked.getTopLevelAncestor()).dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (numberOfPlayer == 0) {
						JOptionPane.showMessageDialog(null, "Vous devez choisir un nombre de joueur ",
								"Error: Pas de selection", JOptionPane.WARNING_MESSAGE);
					}

				}
			});

			boutonConfirmation.setBounds(50, 230, 90, 45);
		}
		return boutonConfirmation;
	}

	public ArrayList<JButton> getListeBouton() {
		return listeBouton;
	}

	public void creerJoueur(int nbJoueur) {
		for (int numeroJoueur = 0; numeroJoueur < nbJoueur; numeroJoueur++) {

			Joueur player = new Joueur(numeroJoueur + 1);
			listeDesJoueurs.add(player);

		}

	}
}
