package new_idea;

import java.awt.Label;
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

	private short numberOfPlayer;

	public ChooseNumberPlayer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 325);
		
		JPanel numberOfPlayerFrame = new JPanel();
		numberOfPlayerFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(numberOfPlayerFrame);
		numberOfPlayerFrame.setLayout(null);
		numberOfPlayerFrame.add(createQuestionLabel());
		setLocationRelativeTo(null);
		
		for (int numeroBouton = 1; numeroBouton <= 3; numeroBouton++) {
			JButton choix = createNbJoueurButton(numeroBouton);
			ajouterAction(choix);
			listeBouton.add(choix);
			numberOfPlayerFrame.add(choix);
		}
		
		numberOfPlayerFrame.add(createConfirmationButton());
	}

	private void ajouterAction(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent leftClick) {
				JButton currentlyClicked = ((JButton) leftClick.getSource());
				short nbJoueur = (short) (listeBouton.indexOf(currentlyClicked) + 1);
				numberOfPlayer = nbJoueur;
				System.out.println(getNumberOfPlayer());
			}
		});
		
	}
	
	private Label createQuestionLabel() {
		Label label = new Label("Combien de joueur vont jouer ?");
		label.setBounds(0, 10, 190, 30);
		return label;
	}

	private JButton createNbJoueurButton(int numeroBouton) {
		JButton btn = new JButton(numeroBouton + " joueurs");
		btn.setBounds(10, 50 + (60 * numeroBouton), 160, 50);
		return btn;
	}

	private JButton createConfirmationButton() {
		JButton boutonConfirmation = new JButton("OK");
		boutonConfirmation.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent leftClick) {
				if (numberOfPlayer != 0) {
					try {
						Clonium plateau = new Clonium(numberOfPlayer);
						plateau.setVisible(true);

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erreur fatale",
							"Une erreur est survenue, le programme va se fermer.", JOptionPane.WARNING_MESSAGE);
						throw new RuntimeException(e);
					}

				} else if (numberOfPlayer == 0) {
					JOptionPane.showMessageDialog(null, "Vous devez choisir un nombre de joueur ",
							"Error: Pas de selection", JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		boutonConfirmation.setBounds(50, 230, 90, 45);
		return boutonConfirmation;
	}

}
