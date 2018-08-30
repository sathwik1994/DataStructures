import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DijkstraAlgorithm {

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
		int n = graph.length;
		int[] weights = new int[n];
		int[] parent = new int[n];
		boolean[] visited = new boolean[n];

		for (int i = 0; i < graph.length; i++) {
			weights[i] = Integer.MAX_VALUE;
			parent[i] = -1;
		}

		weights[source] = 0;
		while (true) {
			int v = getMinimumWeightedVertex(weights, visited);
			if (v == -1) {
				break;
			}
			for (int i = 0; i < graph.length; i++) {
				if (graph[v][i] > 0 && weights[i] > weights[v] + graph[v][i] && !visited[i]) {
					weights[i] = weights[v] + graph[v][i];
					parent[i] = v;
				}
			}
			visited[v] = true;
		}
		prepareSpanningTree(parent, weights, graph);
	}

	private static void prepareSpanningTree(int[] parent, int[] weights, int[][] graph) {
		int totalNodes = weights.length;
		int[][] spanningTreeMatrix = new int[totalNodes][totalNodes];
		for (int i = 0; i < totalNodes; i++) {
			spanningTreeMatrix[i][i] = weights[i];
			setEdgeWeights(spanningTreeMatrix, parent, i, graph);
		}
		System.out.println("\nOutput adjacency matrix.");
		displayMatrix(spanningTreeMatrix);
	}

	private static void setEdgeWeights(int[][] spanningTreeMatrix, int[] parent, int nodeId, int[][] graph) {
		while (parent[nodeId] != -1) {
			int father = parent[nodeId];
			spanningTreeMatrix[father][nodeId] = graph[father][nodeId];
			nodeId = father;
		}
	}

	private static int getMinimumWeightedVertex(int[] weights, boolean[] visited) {
		int minWeight = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < weights.length; i++) {
			if (weights[i] < minWeight && !visited[i]) {
				minWeight = weights[i];
				minIndex = i;
			}
		}
		return minIndex;
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