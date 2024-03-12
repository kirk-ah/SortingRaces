import java.util.Arrays;

public class QuickSort {

	public QuickSort() {
	}

	public static void sort(int[] array) {
		QuickSort quickSort = new QuickSort();

		quickSort.sortArray(array, 0, array.length - 1);
	}

	public void sortArray(int[] array, int low, int high) {
		if (low >= high)
			return;

		// Selecting the pivot
		int middle = low + (high - low) / 2;
		swap(array, low, middle);
		int pivot = array[low];
		int part = partition(array, low, high, pivot);

		sortArray(array, low, part - 1);
		sortArray(array, part + 1, high);
	}

	private int partition(int[] array, int low, int high, int pivot) {
		int i = low + 1;
		int j = high;
		while (i <= j) {
			while (array[i] < pivot && i < array.length - 1) {
				i++;
			}

			while (array[j] > pivot && j > 0) {
				j--;
			}

			if (i < j) {
				swap(array, i, j);
				i++;
				j--;
			} else {
				break;
			}
		}
		swap(array, low, j);
		return j;
	}

	private void swap(int[] array, int first, int second) {
		int s = array[second];
		array[second] = array[first];
		array[first] = s;

	}

}
