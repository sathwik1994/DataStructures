import java.io.File;
import java.util.Scanner;

public class DirectedDijkstra {
	private static void shortestPath(int[][] matrix, int startPoint) {
        int n = matrix.length;
        int[] distance = new int[n]; 
        int[] before = new int[n];
        boolean[] visited_node = new boolean[n];

        for (int i = 0; i < matrix.length; i++) {
            distance[i] = Integer.MAX_VALUE;
            before[i] = -1;
        }

        distance[startPoint] = 0;
        while (true) {
        	 int min_Distance = Integer.MAX_VALUE;
             int min_Index = -1;
             for (int i = 0; i < distance.length; i++) {
                 if (distance[i] < min_Distance && !visited_node[i]) {
                     min_Distance = distance[i];
                     min_Index = i;
                 }
             }
             
            
            if (min_Index == -1) {
                break;
            }
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[min_Index][i] > 0 && distance[i] > distance[min_Index] + matrix[min_Index][i] && !visited_node[i]) {
                    distance[i] = distance[min_Index] + matrix[min_Index][i];
                    before[i] = min_Index;
                }
            }
            visited_node[min_Index] = true;
        }
        
        int nodesCount = distance.length;
        int[][] SPTree = new int[nodesCount][nodesCount];
        for (int i = 0; i < nodesCount; i++) {
        	SPTree[i][i] = distance[i];
            setEdgeDistance(SPTree, before, i, matrix);
       
            
        }
        
        System.out.println("output File:");
		for(int k=0;k<SPTree.length;k++) {
			for(int j=0;j<SPTree.length;j++) {
				System.out.print(SPTree[k][j]+"\t");
			}
			System.out.println();
			
		}
    }
    
    private static void setEdgeDistance(int[][] SPTree, int[] before, int i, int[][] matrix) {
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
