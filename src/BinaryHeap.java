import java.lang.reflect.Array;
import java.util.Arrays;

public class BinaryHeap<T extends Comparable<? super T>> {
	public static final int INITIAL_CAPACITY = 5;

	Class<T> type;
	T[] array;
	int capacity, size;

	@SuppressWarnings("unchecked")
	public BinaryHeap(Class<T> type) {
		this.type = type;
		this.array = (T[]) Array.newInstance(this.type, INITIAL_CAPACITY);
		this.capacity = INITIAL_CAPACITY - 1;
		this.size = 0;
		this.array[0] = null;
	}

	public T deleteMin() {
		if (this.size == 0) {
			return null;
		}
		T min = this.array[1];
		array[1] = array[size];
		size--;
		percolateDown(1);
		return min;
	}

	public void percolateDown(int hole) {
		int child;
		T temp = this.array[hole];

		while (hole * 2 <= size) {
			child = hole * 2;
			if (child != size && this.array[child + 1].compareTo(this.array[child]) < 0) {
				child++;
			}
			if (this.array[child].compareTo(temp) < 0) {
				this.array[hole] = this.array[child];
			} else {
				break;
			}
			hole = child;
		}
		this.array[hole] = temp;
		this.array[size + 1] = null;
	}

	public void insert(T i) {
		if (size + 1 == this.array.length) {
			doubleArray();
		}

		if (this.array[1] == null) {
			this.array[1] = i;
			size++;
			return;
		}

		int hole = size + 1;
		while (compare(i, this.array[hole / 2]) < 0) {
			this.array[hole] = this.array[hole / 2];
			hole = hole / 2;
		}
		array[hole] = i;
		size++;
		array[0] = null;
	}

	public int compare(T first, T second) {
		if (second == null) {
			return 0;
		} else {
			return first.compareTo(second);
		}
	}

	@SuppressWarnings("unchecked")
	public void doubleArray() {
		T[] newArr = (T[]) Array.newInstance(this.type, this.array.length * 2);

		for (int i = 0; i < this.size + 1; i++) {
			newArr[i] = this.array[i];
		}
		this.array = newArr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Comparable<? super T>> void sort(T[] csLegends, Class<T> class1) {
		BinaryHeap heap = new BinaryHeap(class1);
		for (int i = 0; i < csLegends.length; i++) {
			heap.insert(csLegends[i]);
		}

		for (int i = 0; i < heap.array.length; i++) {
			T min = (T) heap.deleteMin();
			if (min != null) {
				csLegends[i] = min;
			}
		}

	}

	public void buildHeap() {
		for (int i = this.size / 2; i > 0; i--) {
			percolateDown(i);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < this.size; i++) {
			if (this.array[i] != null) {
				sb.append(this.array[i].toString() + ", ");
			} else {
				sb.append("null, ");
			}
		}
		if (this.array[this.size] != null) {
			sb.append(this.array[this.size].toString());
		} else {
			sb.append("null");
		}
		sb.append("]");
		return sb.toString();

	}

}
