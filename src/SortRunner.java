import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

/**
 * This program runs various sorts and gathers timing information on them.
 *
 * @author Aidan Kirk Created May 7, 2013.
 */
public class SortRunner {
	private static Random rand = new Random(17); // uses a fixed seed for debugging. Remove the parameter later.

	/**
	 * Starts here.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// array size must be an int. You will need to use something much larger
		int size = 10000000;

		// Each integer will have the range from [0, maxValue). If this is significantly
		// higher than size, you
		// will have small likelihood of getting duplicates.
		int maxValue = Integer.MAX_VALUE;

		// Test 1: Array of random values.
		int[] randomIntArray = getRandomIntArray(size, maxValue);
		System.out.println("Running random tests: ");
		runAllSortsForOneArray(randomIntArray);
		System.out.println();

		// TODO: Tests 2-4
		// Generate the three other types of arrays (shuffled, almost sorted, almost
		// reverse sorted)
		// and run the sorts on those as well.
		List<Integer> shuffled = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			shuffled.add(i);
		}
		Collections.shuffle(shuffled);
		int[] shuff = new int[size];
		for (int i = 0; i < size; i++) {
			shuff[i] = shuffled.get(i);
		}
		System.out.println("Running shuffled tests: ");
		runAllSortsForOneArray(shuff);
		System.out.println();
		
		Arrays.sort(shuff);
		Random rand = new Random();
		for (int i = 0; i < shuff.length * 0.01; i++) {
			int index1 = rand.nextInt(shuff.length - 1);
			int index2 = rand.nextInt(shuff.length - 1);
			swap(shuff, index1, index2);
		}
		System.out.println("Running almost sorted tests: ");
		runAllSortsForOneArray(shuff);
		System.out.println();
		
		int[] reverse = new int[size];
		for (int i = 0; i < shuff.length; i++) {
			reverse[i] = shuff[shuff.length - 1 - i];
		}
		
		System.out.println("Running reverse almost sorted: ");
		runAllSortsForOneArray(reverse);
		System.out.println();

	}
	
	private static void swap(int[] array, int first, int second) {
		int s = array[second];
		array[second] = array[first];
		array[first] = s;

	}

	/**
	 * 
	 * Runs all the specified sorts on the given array and outputs timing results on
	 * each.
	 *
	 * @param array
	 */
	private static void runAllSortsForOneArray(int[] array) {
		long startTime, elapsedTime;
		boolean isSorted = false;

		// TODO: Read this.
		// We prepare the arrays. This can take as long as needed to shuffle items,
		// convert
		// back and forth from ints to Integers and vice-versa, since you aren't timing
		// this
		// part. You are just timing the sort itself.

		int[] sortedIntsUsingDefaultSort = array.clone();
		Integer[] sortedIntegersUsingDefaultSort = copyToIntegerArray(array);
		Integer[] sortedIntegersUsingHeapSort = sortedIntegersUsingDefaultSort.clone();
		Integer[] sortedIntegersUsingTreeSort = sortedIntegersUsingDefaultSort.clone();
		int[] sortedIntsUsingQuickSort = array.clone();

		int size = array.length;

		// What is the default sort for ints? Read the javadoc.
		startTime = System.currentTimeMillis();
		Arrays.sort(sortedIntsUsingDefaultSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingDefaultSort);
		displayResults("int", "the default sort", elapsedTime, size, isSorted);

		// What is the default sort for Integers (which are objects that wrap ints)?
		startTime = System.currentTimeMillis();
		Arrays.sort(sortedIntegersUsingDefaultSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingDefaultSort);
		displayResults("Integer", "the default sort", elapsedTime, size, isSorted);

		// Sort using the following methods, and time and verify each like done above.
		// TODO: a simple sort that uses a TreeSet but handles a few duplicates
		// gracefully.
		startTime = System.currentTimeMillis();
		TreeMap<Integer, Integer> tree = new TreeMap<Integer, Integer>();
		for (int i = 0; i < sortedIntegersUsingTreeSort.length; i++) {
			tree.put(sortedIntegersUsingTreeSort[i], i);
		}
		Iterator<Entry<Integer, Integer>> iterator = tree.entrySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			sortedIntegersUsingTreeSort[i] = iterator.next().getKey();
			i++;
		}
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingDefaultSort);
		displayResults("Integer", "tree sort", elapsedTime, size, isSorted);

		// TODO: your implementation of quick sort. I suggest putting this in a static
		// method in a Quicksort class.
		startTime = System.currentTimeMillis();
		QuickSort.sort(sortedIntsUsingQuickSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingQuickSort);
		displayResults("int", "quick sort", elapsedTime, size, isSorted);
		// TODO: your BinaryHeap sort. You can put this sort in a static method in
		// another class.
		startTime = System.currentTimeMillis();
		BinaryHeap.sort(sortedIntegersUsingHeapSort, Integer.class);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingHeapSort);
		displayResults("Integer", "heap sort", elapsedTime, size, isSorted);

	}

	private static void displayResults(String typeName, String sortName, long elapsedTime, int size, boolean isSorted) {
		if (isSorted) {
			System.out.printf("Sorted %.1e %ss using %s in %d milliseconds\n", (double) size, typeName, sortName,
					elapsedTime);
		} else {
			System.out.println("ARRAY NOT SORTED");
		}
	}

	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a An array to check to see if it is sorted.
	 */
	private static boolean verifySort(int[] a) {
		int previous = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] < previous) {
				return false;
			}
			previous = a[i];
		}
		return true;
	}

	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a An array to check to see if it is sorted.
	 */
	private static boolean verifySort(Integer[] a) {
		Integer previous = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] < previous) {
				return false;
			}
			previous = a[i];
		}
		return true;
	}

	/**
	 * Copies from an int array to an Integer array.
	 *
	 * @param randomIntArray
	 * @return A clone of the primitive int array, but with Integer objects.
	 */
	private static Integer[] copyToIntegerArray(int[] ints) {
		Integer[] integers = new Integer[ints.length];
		for (int i = 0; i < ints.length; i++) {
			integers[i] = ints[i];
		}
		return integers;
	}

	/**
	 * Creates and returns an array of random ints of the given size.
	 *
	 * @return An array of random ints.
	 */
	private static int[] getRandomIntArray(int size, int maxValue) {
		int[] a = new int[size];
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(maxValue);
		}
		return a;
	}

	/**
	 * Creates a shuffled random array.
	 *
	 * @param size
	 * @return An array of the ints from 0 to size-1, all shuffled
	 */
	private static int[] getUniqueElementArray(int size) {
		// TODO: implement and call this method.
		return null;
	}

}
