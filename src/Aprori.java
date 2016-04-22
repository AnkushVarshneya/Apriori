import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @(#)Aprori.java
 * @author Ankush Varshneya
 * @student# 100853074 The solution searcher
 */

public class Aprori {
	private static HashMap<TreeSet<String>, Integer> supportMap = new HashMap<TreeSet<String>, Integer>();

	/**
	 * run the actual algorithm
	 * 
	 * @param app
	 *            the app to run it with
	 */
	public static void run(Application app) {
		supportMap.clear();

		long startTime = System.currentTimeMillis();
		int candidateSetsProcessed = 0;

		int transactionSize = app.getModel().getTransactions().size();
		double suppCutOff = app.getModel().getMinSupport() * transactionSize;
		double confCutOff = app.getModel().getMinConfidence();

		// generate the first item set
		candidateSetsProcessed += generate1stItemSet(transactionSize, suppCutOff, app);

		// Iteratively generate the rest of the item sets for k>2
		// until the item sets are generated are zero
		int k = 1;
		while (app.getModel().getLargeItemSets().get(k++).size() > 0) {
			candidateSetsProcessed += generatekthItemSet(k, transactionSize, suppCutOff, app);
		}

		// set stat
		app.getModel()
				.setStats("Took :" + ((System.currentTimeMillis() - startTime) / 1000.0) + " seconds\n" + "Processed: "
						+ candidateSetsProcessed + " Candidate set(s)\n" + "Min Support: "
						+ app.getModel().getMinSupport() * 100.0 + " %\n" + "Min Confidence: "
						+ app.getModel().getMinConfidence() * 100.0 + " %\n" + "File Selected: "
						+ (app.getModel().getFile() == null ? "" : app.getModel().getFile().getName()) + "\n");

		// get candidate set
		candidateSet(confCutOff, app);
	}

	/**
	 * gets the candidate set
	 * 
	 * @param confCutOff
	 *            the cutoff for strong association rules
	 * @param app
	 *            objects containing logic
	 */
	private static void candidateSet(double confCutOff, Application app) {
		String step = app.getModel().getSteps();
		TableBuilder itemSetTable = new TableBuilder();
		itemSetTable.setTitle("Association Rule(s) and their Confidence");
		itemSetTable.setRowHeader("Association Rule", "Confident", "Passed");

		// get union of all large item sets
		TreeSet<ItemSet> fullLargeItemSet = new TreeSet<ItemSet>();

		// skip large item sets of size 1 and below
		for (Entry<Integer, TreeSet<ItemSet>> entry : app.getModel().getLargeItemSets().entrySet())
			if (entry.getKey() <= 1)
				continue;
			else
				fullLargeItemSet.addAll(entry.getValue());

		// go through all large item sets and generate the the association rules
		for (ItemSet itemSet : fullLargeItemSet) {
			HashMap<Integer, Entry<String, Boolean>> map = new HashMap<Integer, Entry<String, Boolean>>();
			int i = 0;

			for (String item : itemSet.getSet())
				map.put(i++, new SimpleEntry<String, Boolean>(item, null));

			TreeSet<AssociationRule> rules = subsetAsAssociationRules(map, itemSet.getSet(), -1, 0, 0, app);

			for (AssociationRule rule : rules) {
				itemSetTable.addRow(rule.toString(), (rule.getConfidence() * 100) + "%",
						rule.getConfidence() >= confCutOff ? "Yes" : "No");
				if (rule.getConfidence() >= confCutOff)
					if (!app.getModel().getAssociationRules().add(rule))
						throw new RuntimeException("Unable to add:" + rule);
			}
		}
		step += itemSetTable.toString() + "\n";
		app.getModel().setSteps(step);
	}

	/**
	 * Generate 1st item set (not using aprori yet)
	 * 
	 * @param transactionSize
	 *            transaction size
	 * @param suppCutOff
	 *            the cutoff for larger item set
	 * @param app
	 *            objects containing logic
	 * @return
	 */
	private static int generate1stItemSet(int transactionSize, double suppCutOff, Application app) {
		int candidateSetsProcessed = 0;
		TreeSet<ItemSet> newLargeItemSet = new TreeSet<ItemSet>();
		app.getModel().getLargeItemSets().put(1, newLargeItemSet);

		// the item set
		TreeSet<ItemSet> itemSets = new TreeSet<ItemSet>();

		for (String item : app.getModel().getItems()) {
			TreeSet<String> set = new TreeSet<String>();
			set.add(item);
			int support = getSupport(set, app);
			itemSets.add(new ItemSet(set, support));
		}

		// mark the candidates processed
		candidateSetsProcessed += itemSets.size();

		// this means its a large item set
		for (ItemSet item : itemSets)
			if (item.getSupport() >= suppCutOff)
				newLargeItemSet.add(item);

		// print the steps
		printSteps(itemSets, newLargeItemSet, 1, suppCutOff, app);

		return candidateSetsProcessed;
	}

	/**
	 * Generate kth item set (not using aprori yet)
	 * 
	 * @param k
	 *            the value of the iteration
	 * @param transactionSize
	 *            transaction size
	 * @param suppCutOff
	 *            the cutoff for larger item set
	 * @param app
	 *            objects containing logic
	 * @return
	 */
	private static int generatekthItemSet(int k, int transactionSize, double suppCutOff, Application app) {
		if (k < 2)
			throw new RuntimeException("Illegal k =" + k + ".");

		int candidateSetsProcessed = 0;
		TreeSet<ItemSet> newLargeItemSet = new TreeSet<ItemSet>();
		app.getModel().getLargeItemSets().put(k, newLargeItemSet);

		TreeSet<ItemSet> oldLargeItemSet = app.getModel().getLargeItemSets().get(k - 1);

		// the item set via a join with the large item set on 1st round
		TreeSet<ItemSet> itemSets = join(oldLargeItemSet, k, app);

		// if pruning is on and k > 2 then prune the item set
		if (app.getModel().isPrune() && k > 2)
			itemSets = prune(itemSets, oldLargeItemSet, k, app);

		// mark the candidates processed
		candidateSetsProcessed += itemSets.size();

		// this means its a large item set
		for (ItemSet item : itemSets)
			if (item.getSupport() >= suppCutOff)
				newLargeItemSet.add(item);

		// print the steps
		printSteps(itemSets, newLargeItemSet, k, suppCutOff, app);

		return candidateSetsProcessed;
	}

	/**
	 * Join item sets of Lk to get Ck+1
	 * 
	 * @param itemSets
	 *            old item set to generate new one
	 * @param k
	 *            the value of the iteration
	 * @param app
	 *            objects containing logic
	 * @return the joined set
	 */
	private static TreeSet<ItemSet> join(TreeSet<ItemSet> itemSets, int k, Application app) {
		TreeSet<ItemSet> ret = new TreeSet<ItemSet>();

		// if old item set is less then 2 return empty set
		if (itemSets == null)
			return ret;
		if (itemSets.size() < 2)
			return ret;

		for (ItemSet i : itemSets) {
			for (ItemSet j : itemSets) {
				if (!i.getSet().equals(j.getSet())) {
					if (i.getSet().size() != (k - 1) || j.getSet().size() != (k - 1))
						throw new RuntimeException("Illegal size mismatch on join.");

					// get k-1 sub sets
					SortedSet<String> iSub = i.getSet().headSet(i.getSet().last());
					SortedSet<String> jSub = j.getSet().headSet(j.getSet().last());

					// only join if subsets are equal
					if (iSub.equals(jSub)) {

						// join sets
						TreeSet<String> set = new TreeSet<String>();
						set.addAll(i.getSet());
						set.addAll(j.getSet());
						int support = getSupport(set, app);
						ItemSet itemSet = new ItemSet(set, support);
						ret.add(itemSet);
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Prune item sets
	 * 
	 * @param itemSets
	 *            current item set to prune
	 * @param oldLargeItemSet
	 *            the k-1 item set to prune against
	 * @param k
	 *            the size of current item set
	 * @param app
	 *            objects containing logic
	 */
	private static TreeSet<ItemSet> prune(TreeSet<ItemSet> itemSets, TreeSet<ItemSet> oldLargeItemSet, int k,
			Application app) {
		TreeSet<ItemSet> ret = new TreeSet<ItemSet>();
		for (ItemSet itemSet : itemSets) {
			HashMap<Integer, Entry<String, Boolean>> map = new HashMap<Integer, Entry<String, Boolean>>();
			int i = 0;

			for (String item : itemSet.getSet())
				map.put(i++, new SimpleEntry<String, Boolean>(item, null));

			TreeSet<ItemSet> subSet = subsetAsItemSets(map, itemSet.getSet(), k - 1, 0, 0, app);
			boolean include = oldLargeItemSet.containsAll(subSet);
			if (include)
				ret.add(itemSet);
		}
		return ret;
	}

	/**
	 * Calculate subset of some length
	 * 
	 * @param input
	 *            inputed set with used logic
	 * @param fullSet
	 *            the fullset
	 * @param subsetLen
	 *            length of subsets
	 * @param start
	 *            start index
	 * @param currLen
	 *            current length
	 * @param app
	 *            objects containing logic
	 * @return association rules
	 */
	public static TreeSet<AssociationRule> subsetAsAssociationRules(HashMap<Integer, Entry<String, Boolean>> input,
			TreeSet<String> fullSet, int subsetLen, int start, int currLen, Application app) {
		TreeSet<AssociationRule> ret = new TreeSet<AssociationRule>();

		if (currLen == subsetLen || subsetLen == -1) {
			TreeSet<String> set = new TreeSet<String>();
			input.forEach((index, value) -> {
				if (value.getValue() != null && value.getValue() == true)
					set.add(value.getKey());
			});
			// dont add full set or empty set as subset
			if (!set.isEmpty() && !set.equals(fullSet)) {
				double fullSupport = getSupport(fullSet, app);
				double subSupport = getSupport(set, app);
				// set -> (FullSet - Set) with
				// conf = support(fullSet) / support(set)
				AssociationRule rule = new AssociationRule(set, fullSet, fullSupport / subSupport);
				rule.getRightSide().removeAll(set);
				ret.add(rule);
			}
		}

		if (start == input.size())
			return ret;

		// For every index we have two options,
		// 1.. Either we select it, means put true and make currLen+1
		input.get(start).setValue(true);
		ret.addAll(subsetAsAssociationRules(input, fullSet, subsetLen, start + 1, currLen + 1, app));
		// 2.. OR we dont select it, means put false and dont increase currLen
		input.get(start).setValue(false);
		ret.addAll(subsetAsAssociationRules(input, fullSet, subsetLen, start + 1, currLen, app));

		return ret;
	}

	/**
	 * Calculate subset of some length
	 * 
	 * @param input
	 *            inputed set with used logic
	 * @param fullSet
	 *            the fullset
	 * @param subsetLen
	 *            length of subsets
	 * @param start
	 *            start index
	 * @param currLen
	 *            current length
	 * @param app
	 *            objects containing logic
	 * @return Item Sets
	 */
	public static TreeSet<ItemSet> subsetAsItemSets(HashMap<Integer, Entry<String, Boolean>> input,
			TreeSet<String> fullSet, int subsetLen, int start, int currLen, Application app) {
		TreeSet<ItemSet> ret = new TreeSet<ItemSet>();

		if (currLen == subsetLen || subsetLen == -1) {
			TreeSet<String> set = new TreeSet<String>();
			input.forEach((index, value) -> {
				if (value.getValue() != null && value.getValue() == true)
					set.add(value.getKey());
			});
			ItemSet rule = new ItemSet(set, -1);
			ret.add(rule);
		}

		if (start == input.size())
			return ret;

		// For every index we have two options,
		// 1.. Either we select it, means put true and make currLen+1
		input.get(start).setValue(true);
		ret.addAll(subsetAsItemSets(input, fullSet, subsetLen, start + 1, currLen + 1, app));
		// 2.. OR we dont select it, means put false and dont increase currLen
		input.get(start).setValue(false);
		ret.addAll(subsetAsItemSets(input, fullSet, subsetLen, start + 1, currLen, app));

		return ret;
	}

	/**
	 * @param set
	 *            the set to get support of
	 * @param app
	 *            objects containing logic
	 * @return the support
	 */
	private static int getSupport(TreeSet<String> set, Application app) {
		if (supportMap.containsKey(set))
			return supportMap.get(set);

		int count = 0;
		for (TreeSet<String> transaction : app.getModel().getTransactions())
			if (transaction.containsAll(set))
				count++;

		supportMap.put(set, count);

		return count;
	}

	/**
	 * 
	 * @param itemSets
	 *            its set Ck
	 * @param largeItemSets
	 *            large item set Lk
	 * @param k
	 *            the kth iteration
	 * @param suppCutOff
	 *            the cutoff for larger item set
	 * @param app
	 *            objects containing logic
	 */
	private static void printSteps(TreeSet<ItemSet> itemSets, TreeSet<ItemSet> largeItemSets, int k, double suppCutOff,
			Application app) {
		String step = app.getModel().getSteps();

		TableBuilder itemSetTable = new TableBuilder();
		itemSetTable.setTitle("C{" + k + "} (All item sets of size " + k + " and their support.");
		itemSetTable.setRowHeader("ItemSet", "Support", "Passed");
		for (ItemSet itemSet : itemSets)
			itemSetTable.addRow(itemSet.getSet().toString(), new Integer(itemSet.getSupport()).toString(),
					itemSet.getSupport() >= suppCutOff ? "Yes" : "No");

		step += itemSetTable.toString() + "\n";

		TableBuilder largeItemSetTable = new TableBuilder();
		largeItemSetTable
				.setTitle("L{" + k + "} (All large item sets of size " + k + " with support >=" + suppCutOff + ".");
		largeItemSetTable.setRowHeader("ItemSet", "Support");
		for (ItemSet itemSet : largeItemSets)
			largeItemSetTable.addRow(itemSet.getSet().toString(), new Integer(itemSet.getSupport()).toString());

		step += largeItemSetTable.toString() + "\n";

		app.getModel().setSteps(step);
	}
}
