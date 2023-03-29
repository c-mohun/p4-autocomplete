import java.util.*;

/**
 * Facilitates using fast binary search with
 * a Comparator. The methods firstIndex and lastIndex
 * run in time ceiling(1 + log(n)) where n is size of list.
 * 
 * @author ola for framework
 * @author 201 student implementing firstIndex and lastIndex
 *
 */

public class BinarySearchLibrary {

	/**
	 * Return smallest index of target in list using comp
	 * 
	 * @param list   is list of Items being searched
	 * @param target is Item searched for
	 * @param comp   how Items are compared for binary search
	 * @return smallest index k such that list.get(k).equals(target)
	 */
	public static <T> int firstIndexSlow(List<T> list,
			T target, Comparator<T> comp) {
		int index = Collections.binarySearch(list, target, comp);
		if (index < 0)
			return index;
		while (0 <= index && comp.compare(list.get(index), target) == 0) {
			index -= 1;
		}
		return index + 1;
	}

	/**
	 * Return smallest index of target in list using comp.
	 * Guaranteed to make ceiling(1 + log(list.size())) comparisons
	 * 
	 * @param list   is list of Items being searched
	 * @param target is Item searched for
	 * @param comp   how Items are compared for binary search
	 * @return smallest index k such that list.get(k).equals(target),
	 *         Return -1 if there is no such object in list.
	 */
	public static <T> int firstIndex(List<T> list, T target, Comparator<T> comp) {
		int low = 0;
		int high = list.size() - 1;
		int foundAt = -1;

		if (list == null || list.size() == 0) {
			return foundAt;
		}
		while (low + 1 < high) {
			int mid = (low + high) / 2;
			T midval = list.get(mid);
			int cmp = comp.compare(midval, target);
			if (cmp < 0) {
				low = mid + 1;
			} else if (cmp >= 0) {
				high = mid;
			}
		}
		T curr = list.get(low);
		if (comp.compare(curr, (target)) == 0) {
			return low;
		}
		T curr2 = list.get(high);
		if (comp.compare(curr2, (target)) == 0) {
			return high;
		}
		return foundAt;
	}

	public static <T> int lastIndex(List<T> list, T target, Comparator<T> comp) {
		int low = 0;
		int high = list.size() - 1;
		int foundAt = -1;

		if (list == null || list.size() == 0) {
			return foundAt;
		}
		while (low + 1 < high) {
			int mid = (low + high) / 2;
			T midval = list.get(mid);
			int cmp = comp.compare(midval, target);
			if (cmp < 0) {
				low = mid + 1;
			} 
			else if (cmp > 0) {
				high = mid;
			}
			else{
				low = mid;
			}
		}
		T curr2 = list.get(high);
		if (comp.compare(curr2, (target)) == 0) {
			return high;
		}
		T curr = list.get(low);
		if (comp.compare(curr, (target)) == 0) {
			return low;
		}
		
		return foundAt;
	}
}