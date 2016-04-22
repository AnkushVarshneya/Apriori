/**
 * @(#)View.java
 * Assignment#1
 * @author Ankush Varshneya
 * @student# 100853074
 * Main View
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class View extends JPanel {
	// offsets
	public static final Dimension DIMENTION = new Dimension(1375, 520);
		
	private Model model; // The model to which this view is attached.

	private JSlider 		minSupportSlider, minConfidenceSlider;
	private JButton 		selectFileButton, runButton, shopButton;
	private JToggleButton	pruneButton;
	private JFileChooser	fileChooser;
	private JTextArea 		transactionField, itemField, associationRuleField, statField, stepField;
	private JScrollPane		transactionPane, itemPane, associationRulePane, statPane, stepPane;
	private JLabel 			minSupportLabel, minConfidenceLabel, transactionLabel, itemLabel, associationRuleLabel, statLabel, stepLabel;

	// Public method to allow access to JComponents.
	public Model 			getModel() {return model;}

	public JSlider 			getMinSupportSlider() 		{ return minSupportSlider; }
	public JSlider 			getMinConfidenceSlider()	{ return minConfidenceSlider; }

	public JButton 			getSelectFileButton()		{ return selectFileButton; }
	public JButton			getRunButton() 				{ return runButton; }
	public JButton 			getShopButton() 			{ return shopButton; }

	public JToggleButton 	getPruneButton()			{ return pruneButton; }

	public JFileChooser		getFileChooser() 			{ return fileChooser; }

	public JTextArea		getTransactionField()		{ return transactionField; }
	public JTextArea		getItemField()				{ return itemField; }
	public JTextArea 		getAssociationRuleField() 	{ return associationRuleField; }
	public JTextArea 		getStatField()				{ return statField; }
	public JTextArea 		getStepField() 				{ return stepField; }

	public View(Model aModel) {
		// set model
		this.model = aModel;
		
		// Choose grid bag layout
		this.setLayout(new GridBagLayout());

		// File selection stuff
		fileChooser = new JFileChooser();
	    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Comma Seperated Value", "csv"));
	    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Data", "dat"));
	    fileChooser.setCurrentDirectory(new File("data/."));

		
		// minSupport
		this.minSupportLabel = new JLabel("Minimum Support:");
		((GridBagLayout) this.getLayout()).setConstraints(
			this.minSupportLabel,
			this.makeConstraints(	0, 0, 1, 1, 0, 0,
									new Insets(2, 4, 2, 2),
									GridBagConstraints.HORIZONTAL,
									GridBagConstraints.NORTH));
		this.add(this.minSupportLabel);

		this.minSupportSlider = new JSlider(JSlider.HORIZONTAL, 0,100,80);
		this.minSupportSlider.setPreferredSize(new Dimension(240, 50));
		this.minSupportSlider.setMajorTickSpacing(10);
		this.minSupportSlider.setPaintTicks(true);
		this.minSupportSlider.setPaintLabels(true);
		this.minSupportSlider.setEnabled(true);
		((GridBagLayout) this.getLayout()).setConstraints(
			this.minSupportSlider,
			this.makeConstraints(	0, 1, 1, 1, 0, 0,
									new Insets(2, 4, 2, 2),
									GridBagConstraints.HORIZONTAL,
									GridBagConstraints.NORTH));
		this.add(this.minSupportSlider);
		
		// minConfident
		this.minConfidenceLabel = new JLabel("Minimum Confident:");
		((GridBagLayout) this.getLayout()).setConstraints(
			this.minConfidenceLabel,
			this.makeConstraints(	0, 2, 1, 1, 0, 0,
									new Insets(2, 4, 2, 2),
									GridBagConstraints.HORIZONTAL,
									GridBagConstraints.NORTH));
		this.add(this.minConfidenceLabel);

		this.minConfidenceSlider = new JSlider(JSlider.HORIZONTAL, 0,100,80);
		this.minConfidenceSlider.setPreferredSize(new Dimension(240, 50));
		this.minConfidenceSlider.setMajorTickSpacing(10);
		this.minConfidenceSlider.setPaintTicks(true);
		this.minConfidenceSlider.setPaintLabels(true);
		this.minConfidenceSlider.setEnabled(true);
		((GridBagLayout) this.getLayout()).setConstraints(
			this.minConfidenceSlider,
			this.makeConstraints(	0, 3, 1, 1, 0, 0,
									new Insets(2, 4, 2, 2),
									GridBagConstraints.HORIZONTAL,
									GridBagConstraints.NORTH));
		this.add(this.minConfidenceSlider);

		// buttons
		
		this.selectFileButton = new JButton("Select File");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.selectFileButton,
				this.makeConstraints(	0, 4, 1, 1, 0, 0,
										new Insets(2, 4, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.selectFileButton);
		
		this.runButton = new JButton("Run Algorithm");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.runButton,
				this.makeConstraints(	0, 5, 1, 1, 0, 0,
										new Insets(2, 4, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.runButton);
		
		this.pruneButton = new JToggleButton();
		((GridBagLayout) this.getLayout()).setConstraints(
			this.pruneButton,
			this.makeConstraints(	0, 6, 1, 1, 0, 0,
									new Insets(2, 4, 2, 2),
									GridBagConstraints.HORIZONTAL,
									GridBagConstraints.NORTH));
		this.add(this.pruneButton);


		this.shopButton = new JButton("Shop");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.shopButton,
				this.makeConstraints(	0, 7, 1, 1, 0, 0,
										new Insets(2, 4, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.shopButton);
		
		// Stat
		this.statLabel = new JLabel("Stats:");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.statLabel,
				this.makeConstraints(	0, 8, 1, 1, 0, 0,
										new Insets(2, 4, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.statLabel);

		this.statField = new JTextArea(1, 1);
		this.statField.setEditable(false);
		this.statField.setFont(new Font(	Font.MONOSPACED,
											this.statField.getFont().getStyle(),
											this.statField.getFont().getSize()));
		this.statPane = new JScrollPane (	this.statField, 
											JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
											JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		((GridBagLayout) this.getLayout()).setConstraints(
				this.statPane,
				this.makeConstraints(	0, 9, 1, 1, 0, 1,
										new Insets(2, 4, 2, 2),
										GridBagConstraints.BOTH,
										GridBagConstraints.NORTH));
		this.add(this.statPane);

		// transaction
		this.transactionLabel = new JLabel("Transactions:");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.transactionLabel,
				this.makeConstraints(	1, 0, 1, 1, 0, 0,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.transactionLabel);

		this.transactionField = new JTextArea(1, 35);
		this.transactionField.setEditable(false);
		this.transactionField.setFont(new Font(		Font.MONOSPACED,
													this.transactionField.getFont().getStyle(),
													this.transactionField.getFont().getSize()));
		this.transactionPane = new JScrollPane (	this.transactionField, 
													JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
													JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		((GridBagLayout) this.getLayout()).setConstraints(
				this.transactionPane,
				this.makeConstraints(	1, 1, 1, 9, 1, 1,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.BOTH,
										GridBagConstraints.NORTH));
		this.add(this.transactionPane);

		// Item
		this.itemLabel = new JLabel("Items:");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.itemLabel,
				this.makeConstraints(	2, 0, 1, 1, 0, 0,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.itemLabel);

		this.itemField = new JTextArea(1, 15);
		this.itemField.setEditable(false);
		this.itemField.setFont(new Font(	Font.MONOSPACED,
											this.itemField.getFont().getStyle(),
											this.itemField.getFont().getSize()));
		this.itemPane = new JScrollPane (	this.itemField, 
											JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
											JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		((GridBagLayout) this.getLayout()).setConstraints(
				this.itemPane,
				this.makeConstraints(	2, 1, 1, 9, 1, 1,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.BOTH,
										GridBagConstraints.NORTH));
		this.add(this.itemPane);

		// association rule
		this.associationRuleLabel = new JLabel("Association Rules:");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.associationRuleLabel,
				this.makeConstraints(	3, 0, 1, 1, 0, 0,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.associationRuleLabel);

		this.associationRuleField = new JTextArea(1, 40);
		this.associationRuleField.setEditable(false);
		this.associationRuleField.setFont(new Font(		Font.MONOSPACED,
														this.associationRuleField.getFont().getStyle(),
														this.associationRuleField.getFont().getSize()));
		this.associationRulePane = new JScrollPane (	this.associationRuleField, 
														JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
														JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		((GridBagLayout) this.getLayout()).setConstraints(
				this.associationRulePane,
				this.makeConstraints(	3, 1, 1, 9, 1, 1,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.BOTH,
										GridBagConstraints.NORTH));
		this.add(this.associationRulePane);

		// Step
		this.stepLabel = new JLabel("Steps:");
		((GridBagLayout) this.getLayout()).setConstraints(
				this.stepLabel,
				this.makeConstraints(	4, 0, 1, 1, 0, 0,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.add(this.stepLabel);

		this.stepField = new JTextArea(1, 56);
		this.stepField.setEditable(false);
		this.stepField.setFont(new Font(	Font.MONOSPACED,
											this.stepField.getFont().getStyle(),
											this.stepField.getFont().getSize()));
		this.stepPane = new JScrollPane (	this.stepField, 
											JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
											JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		((GridBagLayout) this.getLayout()).setConstraints(
				this.stepPane,
				this.makeConstraints(	4, 1, 1, 9, 1, 1,
										new Insets(2, 2, 2, 4),
										GridBagConstraints.BOTH,
										GridBagConstraints.NORTH));
		this.add(this.stepPane);

		// Update the view
		this.update();
	}

	// Used make constraints and to shorten code.
	private GridBagConstraints makeConstraints(int gX, int gY, int gW, int gH, int wX, int wY, Insets insets, int fill,
			int anchor) {
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = gX;
		c.gridy = gY;
		c.gridwidth = gW;
		c.gridheight = gH;
		c.weightx = wX;
		c.weighty = wY;
		c.insets = insets;
		c.fill = fill;
		c.anchor = anchor;

		return c;
	}

	// Updates the view based on the inputed model.
	public void update() {
		if (this.model != null) {

			// If the button is not selected its labeled "prune" otherwise its labeled "Dont prune".
			if(!this.pruneButton.isSelected()) this.pruneButton.setText("Prune");
			else this.pruneButton.setText("Dont Prune");

			String transactions = "";
			for(TreeSet<String> transaction : this.model.getTransactions())
				transactions += (transaction != null? transaction.toString() : "") + "\n";
			this.transactionField.setText(transactions);
			
			String items = "";
			for(String item : this.model.getItems())
				items += (item != null? item.toString() : "") + "\n";
			this.itemField.setText(items);

			String associationRules = "";
			for(AssociationRule associationRule : this.model.getAssociationRules())
				associationRules += (associationRule != null? associationRule.toString() : "") + "\n";
			this.associationRuleField.setText(associationRules);

			this.minSupportSlider.setValue((int) (this.model.getMinSupport() * 100));
			this.minConfidenceSlider.setValue((int) (this.model.getMinConfidence() * 100));

			this.statField.setText(this.model.getStats());
			this.stepField.setText(this.model.getSteps());
		}
	}

	/**
	 * Testing
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		View aView = new View(new Model());

		JFrame frame = new JFrame("Aprori Rule Miner - View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(View.DIMENTION); // Manually computed sizes.
		frame.getContentPane().add(aView);
		frame.setVisible(true);
	}

}