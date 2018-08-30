import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Dag {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your input file name:");
		String filename = in.nextLine();
		int[][] graph = getAdjacencyMatrix(readFromFile(filename));
		System.out.println("Input adjacency matrix.");
		displayMatrix(graph);
		List<Integer> topologicallySortedOrder = getTopologicalOrder(graph);
		findShortestPath(graph, topologicallySortedOrder);
		in.close();
	}

	private static void findShortestPath(int[][] graph, List<Integer> topologicallySortedOrder) {
		int totalNodes = topologicallySortedOrder.size();
		int[] weights = new int[totalNodes];
		int[] parent = new int[totalNodes];

		for (int i = 0; i < graph.length; i++) {
			weights[i] = Integer.MAX_VALUE;
			parent[i] = -1;
		}

		weights[topologicallySortedOrder.get(0)] = 0;
		topologicallySortedOrder.forEach(nodeId -> {
			for (int i = 0; i < totalNodes; i++) {
				if (graph[nodeId][i] != 0 && graph[nodeId][i] + weights[nodeId] < weights[i]) {
					weights[i] = graph[nodeId][i] + weights[nodeId];
					parent[i] = nodeId;
				}
			}
		});
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

	private static int getIndegree(int nodeId, int[][] graph) {
		int indegree = 0;
		for (int i = 0; i < graph.length; i++) {
			if (graph[i][nodeId] != 0) {
				indegree++;
			}
		}
		return indegree;
	}

	private static List<Integer> getTopologicalOrder(int[][] graph) {
		int[] indegree = new int[graph.length];
		List<Integer> topologicalSortList = new ArrayList<Integer>();
		for (int i = 0; i < graph.length; i++) {
			indegree[i] = getIndegree(i, graph);
		}

		Queue<Integer> queue = new LinkedList<Integer>();
		for (int i = 0; i < graph.length; i++) {
			if (indegree[i] == 0) {
				queue.add(i);
			}
		}
		if (queue.size() == 0) {
			System.out.println("Deadlock");
		}

		while (!queue.isEmpty()) {
			int v = queue.remove();
			topologicalSortList.add(v);
			for (int i = 0; i < graph.length; i++) {
				if (graph[v][i] != 0) {
					indegree[i]--;
					if (indegree[i] == 0) {
						queue.add(i);
					}
				}
			}
		}
		if (topologicalSortList.size() != graph.length) {
			throw new RuntimeException("There is some deadlock in jobs.");
		}
		topologicalSortList.forEach(item -> System.out.print(item + "\t"));
		return topologicalSortList;
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
