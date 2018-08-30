import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BellmanFords {

	private static int[] distances = null;
	private static int[] parent = null;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your input file name:");
		String filename = in.nextLine();
		int[][] graph = getAdjacencyMatrix(readFromFile(filename));
		System.out.println("Input adjacency matrix.");
		displayMatrix(graph);
		findShortestPath(graph, 0);
		in.close();
	}
	private static void findShortestPath(int[][] graph, int source) {
		int totalNodes = graph.length;
		distances = new int[totalNodes];
		parent = new int[totalNodes];
		for (int i = 0; i < totalNodes; i++) {
			distances[i] = Integer.MAX_VALUE / 2;
			parent[i] = -1; // initially no node has any parent.
		}
		distances[source] = 0;
		int maxLoopCount = 0;
		while (maxLoopCount < totalNodes) {
			for (int i = 0; i < totalNodes; i++) {
				for (int j = 0; j < totalNodes; j++) {
					if (graph[i][j] != 0 && distances[i] + graph[i][j] < distances[j]) {
						distances[j] = distances[i] + graph[i][j];
						parent[j] = i;
					}
				}
			}
			maxLoopCount++;
		}
		for (int i = 0; i < totalNodes; i++) {
			for (int j = 0; j < totalNodes; j++) {
				if (graph[i][j] != 0 && distances[i] + graph[i][j] < distances[j]) {
					throw new RuntimeException("Graph contains a negative cylce.");
				}
			}
		}

		prepareSpanningTree(parent, distances, graph);
	}

	private static void prepareSpanningTree(int[] parent, int[] distances, int[][] graph) {
		int totalNodes = distances.length;
		int[][] spanningTreeMatrix = new int[totalNodes][totalNodes];
		for (int i = 0; i < totalNodes; i++) {
			spanningTreeMatrix[i][i] = distances[i];
			setEdgeDistances(spanningTreeMatrix, parent, i, graph);
		}
		System.out.println("\nOutput adjacency matrix.");
		displayMatrix(spanningTreeMatrix);
	}


	private static void setEdgeDistances(int[][] spanningTreeMatrix, int[] parent, int nodeId, int[][] graph) {
		while (parent[nodeId] != -1) {
			int father = parent[nodeId];
			spanningTreeMatrix[father][nodeId] = graph[father][nodeId];
			nodeId = father;
		}
	}

	private static int[][] getAdjacencyMatrix(String fileContent) {
		String[] lines = fileContent.split("\n");
		int totalNodes = lines.length;
		int[][] matrix = new int[totalNodes][totalNodes];
		for (int i = 0; i < lines.length; i++) {
			String[] parts = lines[i].split("\\s+");
			for (int j = 0; j < parts.length; j++) {
				if ("inf".equals(parts[j])) {
					matrix[i][j] = 0;
				} else {
					matrix[i][j] = Integer.parseInt(parts[j]);
				}
			}
		}
		return matrix;
	}

	public static String readFromFile(String filename) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
		} catch (Exception e) {
			System.out.println("Couldn't read from file " + e.toString());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static void displayMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
