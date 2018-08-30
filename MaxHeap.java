
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MaxHeap {

	private static final int MAX_HEAP_SIZE = 100;
	private static final String IN = "in";
	private static final String PRE = "pre";
	private static final String POST = "post";
	private static final String DEL = "del";
	private static final String SEARCH = "sch";
	private static int[] heap = new int[MAX_HEAP_SIZE];
	private static int size = 0;

	private static int getParentIndex(int index) {
		return (index - 1) / 2;
	}

	private static int getLeftIndex(int index) {
		return 2 * index + 1;
	}

	private static int getRightIndex(int index) {
		return 2 * index + 2;
	}

	/**
	 * Inserting into heap
	 * 
	 * @param info
	 */
	private static void insert(int info) {
		if (size == MAX_HEAP_SIZE) {
			System.out.println("OVERFLOW: Heap size is full.");
			return;
		}
		size++;
		int i = size - 1;
		heap[i] = info;

		int parent = getParentIndex(i);
		while (i != 0 && heap[i] > heap[parent]) {
			int temp = heap[i];
			heap[i] = heap[parent];
			heap[parent] = temp;
			i = parent;
			parent = getParentIndex(i);
		}
	}

	/**
	 * @return the max element from heap
	 */
	private static int removeMax() {
		if (size <= 0) {
			System.out.println("Underflow: No elements in heap");
			return Integer.MIN_VALUE;
		}
		if (size == 1) {
			size--;
			return heap[size];
		}
		int maxElement = heap[0];
		heap[0] = heap[size - 1];
		size--;
		heapify(0);
		return maxElement;
	}

	private static void heapify(int index) {
		int left = getLeftIndex(index);
		int right = getRightIndex(index);
		int largest = index;
		if (left < size && heap[index] < heap[left]) {
			largest = left;
		}
		if (right < size && heap[index] < heap[right]) {
			largest = right;
		}
		if (largest != index) {
			int temp = heap[index];
			heap[index] = heap[largest];
			heap[largest] = temp;
			heapify(largest);
		}
	}

	private static void printPreorder(int index) {
		if (index >= size) {
			return;
		}
		System.out.print(heap[index] + " ");
		printPreorder(getLeftIndex(index));
		printPreorder(getRightIndex(index));
	}

	private static void printInorder(int index) {
		if (index >= size) {
			return;
		}
		printInorder(getLeftIndex(index));
		System.out.print(heap[index] + " ");
		printInorder(getRightIndex(index));
	}

	private static void printPostorder(int index) {
		if (index >= size) {
			return;
		}
		printPostorder(getLeftIndex(index));
		printPostorder(getRightIndex(index));
		System.out.print(heap[index] + " ");
	}

	private static String getFileContents(String fileName) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String st;
			while ((st = br.readLine()) != null) {
				sb.append(st);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static String search(int number) {
		for (int i = 0; i < size; i++) {
			if (heap[i] == number) {
				return "Found";
			}
		}
		return "Not found.";
	}

	private static void print(String traversalType) {
		System.out.print("\nHeap: ");
		switch (traversalType) {
		case IN:
			printInorder(0);
			break;
		case PRE:
			printPreorder(0);
			break;
		case POST:
			printPostorder(0);
			break;
		}
	}

	private static void process(String input, String traversalType) {
		String[] parts = input.split("\\.");
		if (parts.length == 2) {
			int number = Integer.valueOf(parts[0]);
			String operation = parts[1];
			if (operation.equals(IN)) {
				System.out.print("\nInserting " + number);
				insert(number);
				print(traversalType);
			} else if (operation.equals(SEARCH)) {
				System.out.print("\nSearch result for " + number + " = " + search(number));
			}
		} else if (input.equals(DEL)) {
			System.out.print("\nDeleted " + removeMax());
			print(traversalType);
		} else {
			System.out.println("Invalid input");
		}
		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your input file name: ");
		String fileName = in.nextLine();
		String filecontents = getFileContents(fileName);
		System.out.println("Display the input before: " + filecontents);
		System.out.println("Display the output after the operation: ");
		String[] parts = filecontents.split("\\s+");
		String traversalType = parts[0];
		for (int index = 1; index < parts.length; index++) {
			process(parts[index], traversalType);
		}
		in.close();
	

	
	
	}
}
