import java.io.*;
import java.util.Scanner;

public class Dijkstra {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter input file name:");
		String file_name = sc.nextLine();
		int[][] graphInput = inputmatrix(readFile(file_name));
		System.out.println("Input");
		inputDisplay(graphInput);
		shortestPathAlg(graphInput, 0);
		sc.close();
	}
	
	private static int[][] inputmatrix(String file_data) {
		String[] rows = file_data.split("\n");
		int rowscount = rows.length;
		int[][] input = new int[rowscount][rowscount];
		for (int i = 0; i < rows.length; i++) {
			String[] indnodes = rows[i].split("\\s+");
			for (int j = 0; j < indnodes.length; j++) {
				if ("inf".equals(indnodes[j])) {
					input[i][j] = 0;
				} else {
					input[i][j] = Integer.parseInt(indnodes[j]);
				}
			}
		}
		return input;
	}

	public static String readFile(String file_name) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(file_name));
			String row = br.readLine();

			while (row != null) {
				sb.append(row);
				sb.append(System.lineSeparator());
				row = br.readLine();
			}
		} catch (Exception e) {
			System.out.println("Can't read from file " + e.toString());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static void inputDisplay(int[][] matrix_input) {
		for (int i = 0; i < matrix_input.length; i++) {
			for (int j = 0; j < matrix_input[0].length; j++) {
				System.out.print(matrix_input[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	private static void shortestPathAlg(int[][] graph_input, int startPoint) {
		int n = graph_input.length;
		int[] distance = new int[n]; 
		int[] before = new int[n];
		boolean[] visited_node = new boolean[n];

		for (int i = 0; i < graph_input.length; i++) {
			distance[i] = Integer.MAX_VALUE;
			before[i] = -1;
		}

		distance[startPoint] = 0;
		while (true) {
			int d = getMinimumDistanceNode(distance, visited_node);
			if (d == -1) {
				break;
			}
			for (int i = 0; i < graph_input.length; i++) {
				if (graph_input[d][i] > 0 && distance[i] > distance[d] + graph_input[d][i] && !visited_node[i]) {
					distance[i] = distance[d] + graph_input[d][i];
					before[i] = d;
				}
			}
			visited_node[d] = true;
		}
		spanningTree(before, distance, graph_input);
	}
	
	private static void setEdgeDistance(int[][] spanningTreeInput, int[] before, int i, int[][] input) {
		while (before[i] != -1) {
			int dad = before[i];
			spanningTreeInput[dad][i] = input[dad][i];
			i = dad;
		}
	}
	
	private static int getMinimumDistanceNode(int[] distance, boolean[] visited_node) {
		int min_Distance = Integer.MAX_VALUE;
		int min_Index = -1;
		for (int i = 0; i < distance.length; i++) {
			if (distance[i] < min_Distance && !visited_node[i]) {
				min_Distance = distance[i];
				min_Index = i;
			}
		}
		return min_Index;
	}
	
	private static void spanningTree(int[] before, int[] distance, int[][] input) {
		int nodesCount = distance.length;
		int[][] spanningTreeInput = new int[nodesCount][nodesCount];
		for (int i = 0; i < nodesCount; i++) {
			spanningTreeInput[i][i] = distance[i];
			setEdgeDistance(spanningTreeInput, before, i, input);
		}
		System.out.println("\nOutput");
		inputDisplay(spanningTreeInput);
	}

}