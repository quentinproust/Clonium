
import javax.swing.JButton;

public class Case extends JButton {

	private static final long serialVersionUID = 9L;

	private Joueur owner;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Case() {

	}

	public Case(int id) {


		this.setText("1");

		this.setId(id);
		this.setBounds((id % 8) * 82 + 10, (id / 8) * 54 + 10, 70, 40);
	}

	public char getValue() {
		return getText().charAt(0);
		// return getJeton().getValue();
	}

	public void increaseValue() {

		char currentValue = getValue();
		currentValue += 1;
		setText("" + currentValue);

	}

	public void resetValue() {
		setText("0");
		// setJeton().setValue(0);
	}


}
