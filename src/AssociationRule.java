
/**
 * @(#)AssociationRule.java
 * @author Ankush Varshneya
 * @student# 100853074
 * Association Rule object
 */

import java.util.Iterator;
import java.util.TreeSet;

public class AssociationRule implements Comparable<AssociationRule> {
	private TreeSet<String> leftSide; // Left hand side of the rules
	private TreeSet<String> rightSide; // Right Side of the rule
	private double confidence; // the confidence

	public AssociationRule(TreeSet<String> leftSide, TreeSet<String> rightSide, double confidence) {
		super();
		this.leftSide = new TreeSet<String>(leftSide);
		this.rightSide = new TreeSet<String>(rightSide);
		this.confidence = confidence;
	}

	public TreeSet<String> getRightSide() {
		return rightSide;
	}

	public void setRightSide(TreeSet<String> rightSide) {
		this.rightSide = rightSide;
	}

	public TreeSet<String> getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(TreeSet<String> leftSide) {
		this.leftSide = leftSide;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	@Override
	public String toString() {
		return leftSide + " --> " + rightSide;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leftSide == null) ? 0 : leftSide.hashCode());
		result = prime * result + ((rightSide == null) ? 0 : rightSide.hashCode());
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
		AssociationRule other = (AssociationRule) obj;
		if (leftSide == null) {
			if (other.leftSide != null)
				return false;
		} else if (!leftSide.equals(other.leftSide))
			return false;
		if (rightSide == null) {
			if (other.rightSide != null)
				return false;
		} else if (!rightSide.equals(other.rightSide))
			return false;
		return true;
	}

	@Override
	public int compareTo(AssociationRule o) {
		TreeSet<String> ol1 = this.getLeftSide();
		TreeSet<String> ol2 = o.getLeftSide();
		TreeSet<String> or1 = this.getRightSide();
		TreeSet<String> or2 = o.getRightSide();

		if (ol1.size() == ol2.size()) {
			Iterator<String> otherLeftSideRecords = ol2.iterator();
			for (String thisRecord : ol1) {
				// Shorter sets sort first
				if (!otherLeftSideRecords.hasNext())
					return 1;
				int comparison = thisRecord.compareTo(otherLeftSideRecords.next());
				if (comparison != 0)
					return comparison;
			}
			// Shorter sets sort first
			if (otherLeftSideRecords.hasNext())
				return -1;
			// test other side
			else {
				if (or1.size() == or2.size()) {
					Iterator<String> otherRightSideRecords = or2.iterator();
					for (String thisRecord : or1) {
						// Shorter sets sort first
						if (!otherRightSideRecords.hasNext())
							return 1;
						int comparison = thisRecord.compareTo(otherRightSideRecords.next());
						if (comparison != 0)
							return comparison;
					}
					// Shorter sets sort first
					if (otherRightSideRecords.hasNext())
						return -1;
					else
						return 0;
				} else
					return or1.size() - or2.size();
			}
		} else
			return ol1.size() - ol2.size();
	}
}