
/**
 * @(#)Model.java
 * @author Ankush Varshneya
 * @student# 100853074
 * Main Model
 */

import java.io.File;
import java.util.*;

public class Model {
	private File 						file; 				// File containing association rules
	private ArrayList<TreeSet<String>>	transactions; 		// transactions
	private TreeSet<String> 			items;				// set of all possible items
	private HashMap<Integer, TreeSet<ItemSet>>
										largeItemSets; 		// Large Item Set
	private TreeSet<AssociationRule> 	associationRules; 	// Association Rule
	private double 						minSupport;			// support level cutoff of the mining
	private double 						minConfidence;		// confidence level cutoff of the mining
	private String 						stats;				// stats
	private	String						steps;				// steps
	private boolean						prune;				// turn on pruning?
	
	public Model() {
		this.transactions = new ArrayList<TreeSet<String>>();
		this.items = new TreeSet<String>();
		this.largeItemSets = new HashMap<Integer, TreeSet<ItemSet>>();
		this.associationRules = new TreeSet<AssociationRule>();
		this.minSupport = .50;
		this.minConfidence = .70;
		this.stats = "";
		this.steps = "";
	}
	public File 						getFile() 				{ return file; }
	public ArrayList<TreeSet<String>> 	getTransactions() 		{ return transactions; }
	public TreeSet<String> 				getItems() 				{ return items; }
	public HashMap<Integer, TreeSet<ItemSet>>
										getLargeItemSets() 		{ return largeItemSets; }
	public TreeSet<AssociationRule> 	getAssociationRules() 	{ return associationRules; }
	public double 						getMinSupport() 		{ return minSupport; }
	public double 						getMinConfidence() 		{ return minConfidence; }
	public String 						getStats() 				{ return stats; }
	public String 						getSteps() 				{ return steps;	}
	public boolean 						isPrune() 				{ return prune;	}
	
	public void setFile(File file) 								{ this.file = file; }
	public void setTransactions(ArrayList<TreeSet<String>> transactions)
																{ this.transactions = transactions; }
	public void setItems(TreeSet<String> items) 				{ this.items = items; }
	public void setLargeItemSets(HashMap<Integer, TreeSet<ItemSet>> largeItemSets)
																{ this.largeItemSets = largeItemSets; }
	public void setAssociationRules(TreeSet<AssociationRule> associationRules)
																{ this.associationRules = associationRules; }
	public void setMinSupport(double minSupport) 				{ this.minSupport = minSupport; }
	public void setMinConfidence(double minConfidence) 			{ this.minConfidence = minConfidence; }
	public void setStats(String stats) 							{ this.stats = stats; }
	public void setSteps(String steps) 							{ this.steps = steps; }
	public void setPrune(boolean prune) 						{ this.prune = prune; }
}