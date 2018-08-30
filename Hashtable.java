import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Hashtable {

	private static int hashtable[];
	private static int currSize = 0;
	private static int maxHashTableSize = 0;
	private static int prime = 0;
	private static final String IN = "in";
	private static final String DEL = "del";
	private static final String SEARCH = "sch";

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

	/**
	 * @return true: if hashtable is full false: otherwise
	 */
	private static boolean isHashTableFull() {
		return currSize == hashtable.length;
	}

	/**
	 * 
	 * @return true if hashtable is empty false: otherwise
	 */
	private static boolean isHashTableEmpty() {
		return currSize == 0;
	}

	/**
	 * used to initialize hashtable with some default values
	 */
	private static void initializeHashtable(int tableSize) {
		for (int i = 0; i < tableSize; i++) {
			hashtable[i] = -1;
		}
	}

	private static int hash1(int key) {
		return key % maxHashTableSize;
	}

	private static int hash2(int key) {
		return prime - key % prime;
	}

	private static void display() {
		for (int i = 0; i < maxHashTableSize; i++) {
			if (hashtable[i] != -1) {
				System.out.println(i + ": " + hashtable[i] + " ");
			} else {
				System.out.println(i + ": ");
			}
		}
	}

	/**
	 * to insert into hashtable
	 * 
	 * @param key
	 */
	private static void insert(int key) {
		if (isHashTableFull()) {
			System.out.println("Exiting because hashtable is full.");
			System.exit(0);
		}

		int index = hash1(key);
		if (hashtable[index] != -1) {
			if (hashtable[index] == key) {
				System.out.print("\nDuplicate key " + key);
				return;
			}
			int index2 = hash2(key);
			int i = 1;
			while (true) {
				int newIndex = (index + i * index2) % maxHashTableSize;
				if (hashtable[newIndex] == -1) {
					hashtable[newIndex] = key;
					break;
				} else if (hashtable[newIndex] == key) {
					System.out.println("\nDuplicate key " + key);
					return;
				}

				i++;
			}
		} else {
			hashtable[index] = key;
		}
		currSize++;
	}

	/**
	 * 
	 * @param key
	 * @return true: if key is found and deleted false: otherwise
	 */
	private static boolean delete(int key) {
		if (isHashTableEmpty()) {
			System.out.println("There are no elements present in hashtable.");
		}
		boolean status;
		try {
			int index = hash1(key);
			if (hashtable[index] == key) {
				hashtable[index] = -1;
				return true;
			}
			int index2 = hash2(key);
			int i = 1;
			while (true) {
				int newIndex = (index + i * index2) % maxHashTableSize;
				if (hashtable[newIndex] == key) {
					hashtable[index] = -1;
					status = true;
					break;
				}
				i++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			status = false;
		}
		return status;
	}

	private static String search(int key) {
		String status = "not found";
		try {
			int index = hash1(key);
			if (hashtable[index] == key) {
				return "found";
			}
			int index2 = hash2(key);
			int i = 1;
			while (true) {
				int newIndex = (index + i * index2) % maxHashTableSize;
				if (hashtable[newIndex] == key) {
					status = "found";
					break;
				}
				i++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			status = "not found";
		}
		return status;
	}

	private static void process(String input) {
		String[] parts = input.split("\\.");
		if (parts.length == 2) {
			int number = Integer.valueOf(parts[0]);
			String operation = parts[1];
			if (operation.equals(IN)) {
				System.out.print("\nInserting " + number);
				insert(number);
			} else if (operation.equals(SEARCH)) {
				System.out.print("\nSearch result for " + number + " = " + search(number));
			} else if (operation.equals(DEL)) {
				if (delete(number)) {
					System.out.print("\nDeleted " + number);
				} else {
					System.out.println("\n" + number + " to delete not found");
				}
			} else {
				System.out.println("Invalid input");
			}
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your input file name: ");
		String fileName = in.nextLine();
		String filecontents = getFileContents(fileName);
		System.out.println("Display the input before: " + filecontents);
		String[] parts = filecontents.split("\\s+");
		maxHashTableSize = Integer.valueOf(parts[0]);
		hashtable = new int[maxHashTableSize];
		initializeHashtable(maxHashTableSize);
		prime = Integer.valueOf(parts[1]);
		for (int i = 2; i < parts.length; i++) {
			process(parts[i]);
		}
		System.out.println("\nDisplay the output after the operation: ");
		display();
		in.close();
	}

}
