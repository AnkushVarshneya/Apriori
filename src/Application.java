
// Needed for ActionListener.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.util.TreeSet;
import java.util.Scanner;

// Needed for JFrame.
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Application extends JFrame implements ActionListener, ChangeListener, ItemListener {

	private Model model; // The model to which this view is attached.
	private View view; // The view will show the state of the model.

	public Model getModel() {
		return model;
	}

	public View getView() {
		return view;
	}

	public Application(String title) {
		// Sets the title for the window.
		super(title);

		// set model and the view
		this.model = new Model();
		this.view = new View(this.model);

		// Add action listener.
		this.view.getRunButton().addActionListener(this);
		this.view.getSelectFileButton().addActionListener(this);
		this.view.getShopButton().addActionListener(this);

		// Add change listener to the slider
		this.view.getMinSupportSlider().addChangeListener(this);
		this.view.getMinConfidenceSlider().addChangeListener(this);

		// add item listener
		this.view.getPruneButton().addItemListener(this);

		// Add the view.
		this.getContentPane().add(view);

		// Manually computed sizes.
		this.setSize(View.DIMENTION);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This is the single event handler for all the buttons
	 * 
	 * @param e
	 *            event
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.view.getRunButton()) {
			handleRun();
		} else if (e.getSource() == this.view.getSelectFileButton()) {
			handleSelectFile();
		} else if (e.getSource() == this.view.getShopButton()) {
			// Now bring up the dialog box
			ShopDialog dialog = new ShopDialog(this, "Shop", true, this.model);
			dialog.setVisible(true);
		}

		this.view.update(); // Update the view.
	}

	/**
	 * This is the single change handler for all the sliders
	 * 
	 * @param e
	 *            event
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == this.view.getMinSupportSlider()) {
			this.model.setMinSupport(new Double(this.view.getMinSupportSlider().getValue()) / 100.0);
		} else if (e.getSource() == this.view.getMinConfidenceSlider()) {
			this.model.setMinConfidence(new Double(this.view.getMinConfidenceSlider().getValue()) / 100.0);
		}
	}

	/**
	 * This it the single item state change handler
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.view.getPruneButton()) {
			int state = e.getStateChange();
			if (this.model != null) {
				this.model.setPrune(state == ItemEvent.SELECTED);
			}
		}
		this.view.update(); // Update the view.
	}

	private void handleRun() {
		// clear old stuff
		this.model.getLargeItemSets().clear();
		this.model.getAssociationRules().clear();
		this.model.setStats("");
		this.model.setSteps("");
		Aprori.run(this);
	}

	private void handleSelectFile() {
		if (this.view.getFileChooser().showOpenDialog(this.view) == JFileChooser.APPROVE_OPTION) {
			// set file
			this.model.setFile(this.view.getFileChooser().getSelectedFile());

			// clear old stuff
			this.model.getTransactions().clear();
			this.model.getItems().clear();
			this.model.getLargeItemSets().clear();
			this.model.getAssociationRules().clear();
			this.model.setStats("");
			this.model.setSteps("");

			String extension = "";

			int i = this.model.getFile().toString().lastIndexOf('.');
			if (i > 0) {
				extension = this.model.getFile().toString().substring(i + 1);
			}

			// parse for transactions
			try {
				Scanner inputStream = new Scanner(this.model.getFile());
				while (inputStream.hasNextLine()) {
					TreeSet<String> transaction = new TreeSet<String>();
					for (String item : inputStream.nextLine().split(extension.equals("csv") ? "," : " ")) {
						transaction.add(item);
						this.model.getItems().add(item);
					}
					this.model.getTransactions().add(transaction);
				}
				inputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This is where the program begins.
	 */
	public static void main(String[] args) {
		JFrame frame = new Application("Aprori Rule Miner");
		frame.setVisible(true);
	}
}