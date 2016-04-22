
/**
 * @(#)ItemSet.java
 * @author Ankush Varshneya
 * @student# 100853074
 * Item Set object
 */

import java.util.Iterator;
import java.util.TreeSet;

public class ItemSet implements Comparable<ItemSet> {
	private TreeSet<String>	set; 		// the set
	private int 			support;	// the support as a number (not a percentage)

	public TreeSet<String> 	getSet() 		{ return set; }
	public int 				getSupport() 	{ return support; }

	public void setSet(TreeSet<String> set) { this.set = set; }
	public void setSupport(int support) 	{ this.support = support; }

	public ItemSet(TreeSet<String> set, int support) {
		super();
		this.set = new TreeSet<String>(set);
		this.support = support;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((set == null) ? 0 : set.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSet other = (ItemSet) obj;
		if (set == null) {
			if (other.set != null)
				return false;
		} else if (!set.equals(other.set))
			return false;
		return true;
	}

	@Override
	public int compareTo(ItemSet o) {
		TreeSet<String> o1 = this.getSet();
		TreeSet<String> o2 = o.getSet();

		if (o1.size() == o2.size()) {
			Iterator<String> otherRecords = o2.iterator();
			for (String thisRecord : o1) {
				// Shorter sets sort first.
				if (!otherRecords.hasNext())
					return 1;
				int comparison = thisRecord.compareTo(otherRecords.next());
				if (comparison != 0)
					return comparison;
			}
			// Shorter sets sort first
			if (otherRecords.hasNext())
				return -1;
			else
				return 0;
		}

		return o1.size() - o2.size();
	}
}