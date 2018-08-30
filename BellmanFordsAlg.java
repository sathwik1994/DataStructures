import java.io.File;
import java.util.Scanner;

public class BellmanFordsAlg {
	private static void shortestPath(int[][] matrix, int startPoint) {
		int n = matrix.length;
		 int[] distance = new int[n];
		 int[] before = new int[n];
		 for (int i = 0; i < matrix.length; i++) {
	            distance[i] = Integer.MAX_VALUE/2;
	            before[i] = -1;
	        }
		distance[startPoint] = 0;
		int maxLoopCount = 0;
		while (true) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (matrix[i][j] != 0 && distance[i] + matrix[i][j] < distance[j]) {
						distance[j] = distance[i] + matrix[i][j];
						before[j] = i;
					}
				}
			}
			maxLoopCount++;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] != 0 && distance[i] + matrix[i][j] < distance[j]) {
					throw new RuntimeException("Graph contains a negative cylce.");
				}
			}
		}

		int totalNodes = distance.length;
		int[][] spanningTreeMatrix = new int[totalNodes][totalNodes];
		for (int i = 0; i < totalNodes; i++) {
			spanningTreeMatrix[i][i] = distance[i];
			setEdgeDistances(spanningTreeMatrix, before, i, graph);
		}
		System.out.println("\nOutput adjacency matrix.");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}	
	}
  
    
    private static void setEdgeDistances(int[][] SPTree, int[] before, int i, int[][] matrix) {
        while (before[i] != -1) {
            int dad = before[i];
            SPTree[dad][i] = matrix[dad][i];
            i = dad;
        }
    }
    
 

	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {
		Scanner sc= new Scanner(System.in);
		System.out.println("enter file name");
	
		 //Scanner inp = new Scanner(System.in);

         File input_file = new File(sc.nextLine());
         Scanner inp = new Scanner(input_file);
         String[] arr1 = (inp.nextLine().trim().split("\\s+"));
         int len1=arr1.length;
         Scanner inp1 = new Scanner(input_file);
         
         
        

	    
	    int i = 0;
	    
	    int[][] matrix= new int[len1][len1];
	    
	 

	    while (inp1.hasNextLine()) {
	        String[] arr = (inp1.nextLine().trim().split("\\s+"));
	        
	        
	       
	        for(int p=0; p<len1;p++) {
	        	if(arr[p].equals("inf")) {
	        		matrix[i][p]=0;
	        	}
	        	else {
	        	matrix[i][p]=Integer.parseInt(arr[p]);
	        	}
	        	
	        }
	        
	        

	      

	        i++;
	    }
	    System.out.println("Input File:");
		for(int k=0;k<matrix.length;k++) {
			for(int j=0;j<matrix.length;j++) {
				System.out.print(matrix[k][j]+"\t");
			}
			System.out.println();
			
		}
		shortestPath(matrix, 0);
		}
		catch(Exception ex) {
			System.out.println("Enter valid file name!!");
		}

	    
		
	}

}
