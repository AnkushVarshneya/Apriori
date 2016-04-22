
/**
 * @(#)ShopDialog.java
 * @author Ankush Varshneya
 * @student# 100853074
 * The Shop
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ShopDialog extends JDialog implements ActionListener {

	private Model			model;
	private JLabel			itemLabel, suggestionLabel;
	private JTextArea		suggestionField;
	private JList<String>	itemList;
	private JScrollPane		itemPane, suggestionPane;
	private JButton 		shopButton, exitButton;

    public ShopDialog(JFrame owner, String title, boolean modal, Model model) {
    	super(owner, title, modal);
    	this.model = model;

    	// Choose grid bag layout
		GridBagLayout layout = new GridBagLayout();
		this.getContentPane().setLayout(layout);

		// Item List
		this.itemLabel = new JLabel("Items:");
		layout.setConstraints(
			this.itemLabel,
			this.makeConstraints(	0, 0, 2, 1, 0, 0,
									new Insets(2, 2, 2, 2),
									GridBagConstraints.HORIZONTAL,
									GridBagConstraints.NORTH));
		this.getContentPane().add(this.itemLabel);

		this.itemList = new JList<String>(model.getItems().toArray(new String[model.getItems().size()]));
		this.itemList.setFont(new Font(		Font.MONOSPACED,
											this.itemList.getFont().getStyle(),
											this.itemList.getFont().getSize()));
		this.itemPane = new JScrollPane (	this.itemList, 
											JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
											JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		layout.setConstraints(
				this.itemPane,
				this.makeConstraints(	0, 1, 2, 2, 1, 1,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.BOTH,
										GridBagConstraints.NORTH));
		Dimension d1 = this.itemList.getPreferredSize();
		d1.height = 200;
		this.itemPane.setPreferredSize(d1);
		this.getContentPane().add(this.itemPane);

		// suggestion List
		this.suggestionLabel = new JLabel("You may also want to buy:");
		layout.setConstraints(
			this.suggestionLabel,
			this.makeConstraints(	0, 3, 2, 1, 0, 0,
									new Insets(2, 2, 2, 2),
									GridBagConstraints.HORIZONTAL,
									GridBagConstraints.NORTH));
		this.getContentPane().add(this.suggestionLabel);

		this.suggestionField = new JTextArea(100, 2);
		this.suggestionField.setEditable(false);
		this.suggestionField.setFont(new Font(	Font.MONOSPACED,
												this.suggestionField.getFont().getStyle(),
												this.suggestionField.getFont().getSize()));
		this.suggestionPane = new JScrollPane (	this.suggestionField, 
												JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
												JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		layout.setConstraints(
				this.suggestionPane,
				this.makeConstraints(	0, 4, 2, 1, 1, 1,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.BOTH,
										GridBagConstraints.NORTH));
		Dimension d2 = this.suggestionField.getPreferredSize();
		d2.height = 10;
		this.suggestionPane.setPreferredSize(d2);
		this.getContentPane().add(this.suggestionPane);

		// shop button
		this.shopButton = new JButton("Shop");
		layout.setConstraints(
				this.shopButton,
				this.makeConstraints(	0, 5, 1, 1, 1, 0,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.shopButton.addActionListener(this);
		this.getContentPane().add(this.shopButton);
		
		// quit button
		this.exitButton = new JButton("Exit Shop");
		layout.setConstraints(
				this.exitButton,
				this.makeConstraints(	1, 5, 1, 1, 1, 0,
										new Insets(2, 2, 2, 2),
										GridBagConstraints.HORIZONTAL,
										GridBagConstraints.NORTH));
		this.exitButton.addActionListener(this);
		this.getContentPane().add(this.exitButton);
		
		// Ensure that the dialog box appears close to the main window and initiate a size
    	setLocationRelativeTo(owner);
		setSize(200, 450);
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == this.shopButton) {
    		// get the selected items
    		TreeSet<String> cart = new TreeSet<String>(this.itemList.getSelectedValuesList());
    		TreeSet<String> suggestion = new TreeSet<String>();
    		if(!model.getItems().isEmpty()) {
    			for (AssociationRule rule : model.getAssociationRules())
    				if(rule.getLeftSide().equals(cart))
    					suggestion.addAll(rule.getRightSide());
    			if(!suggestion.isEmpty())
    				this.suggestionField.setText(suggestion.toString());
    			else
    				this.suggestionField.setText("No suggestions.");
    		}
    		else
    			this.suggestionField.setText("Run Algorithm First.");	
    	} else
    		dispose();
    }
    
    // Used make constraints and to shorten code.
	private GridBagConstraints makeConstraints(int gX, int gY, int gW, int gH, int wX, int wY, Insets insets, int fill, int anchor) {
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
}