package comparators;

import java.util.Comparator;

/**
 * An {@link ReverseIntegerComparator} compares integers in reverse order, e.g.,
 * compare(2, 1) returns a negative number.
 *
 */
public class ReverseIntegerComparator implements Comparator<Integer> {
	@Override
	public int compare(Integer arg0, Integer arg1) {
		return arg0 > arg1 ? -1 : arg0 < arg1 ? 1 : 0;
	}
}
