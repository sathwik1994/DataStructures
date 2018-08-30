import java.util.*;

public class MergeSort {

	public static void main(String[] args) {
		MergeSort obj = new MergeSort();
		
	    int[] rand = obj.generateRandomNumbers(100000);
	    Scanner sc = new Scanner(System.in);
	    int n = 100000;
	    int[] in = new int[n];
	    for(int i=0; i<n;i++)
	    {
	    	in[i]=rand[i];
	    }
	    long startTime = System.nanoTime();
	    int[] finalresult=obj.mergeSort(in);
	    long stopTime = System.nanoTime();
	    long elapsed = stopTime-startTime;
	    System.out.println(elapsed);
	    System.out.println("Enter range");
	    int min = sc.nextInt();
	    int max = sc.nextInt();
	    if(min>max)
   	    {
   	    	System.out.println("Enter minimum first, then maximum");
   	    }
	    for(int i=min; i<max;i++)
	    {
	    	System.out.println(finalresult[i]);
	    	
	    }
	    
	}
	
	 int[] mergeSort(int[] rand){
		
		if(rand.length == 1){
			return rand;
		}
		
		int middle = (int) Math.ceil((double)rand.length / 2);
		int[] left = new int[middle];
		
		int rightLength = 0;
		if(rand.length % 2 == 0){
			rightLength = middle;
		}
		else{
			rightLength = middle - 1;
		}
		int[] right = new int[rightLength];
		
		int leftIndex = 0; 
		int rightIndex = 0;
		
		for (int i = 0; i < rand.length; i++) {
			if(i < middle){
				left[leftIndex] = rand[i];
				leftIndex++;
			}
			else{
				right[rightIndex] = rand[i];
				rightIndex++;
			}
		}
		
		left = mergeSort(left);
		right = mergeSort(right);
		
		return merge(left, right);
	}
	
	int[] merge(int[] left, int[] right){
		int[] finalresult = new int[left.length + right.length];
		int leftIndex = 0;
		int rightIndex = 0;
		int resultIndex = 0;
		
		while(leftIndex < left.length || rightIndex < right.length){
			if(leftIndex < left.length && rightIndex < right.length){
				if(left[leftIndex] < right[rightIndex]){
					finalresult[resultIndex] = left[leftIndex];
					leftIndex++;
					resultIndex++;
				}
				else{
					finalresult[resultIndex] = right[rightIndex];
					rightIndex++;
					resultIndex++;
				}
			}
			else if(leftIndex < left.length){
				for (int i = resultIndex; i < finalresult.length; i++) {
					finalresult[i] = left[leftIndex];
					leftIndex++;
				}
			}
			else if(rightIndex < right.length){
				for (int i = resultIndex; i < finalresult.length; i++) {
					finalresult[i] = right[rightIndex];
					rightIndex++;
				}
			}
		}
		
		return finalresult;
	}
	
	
 int[] generateRandomNumbers(int n){
		
	    int[] rand = new int[n];
	    Random random = new Random();
		
	    for (int i = 0; i < rand.length; i++) {
		    rand[i] = random.nextInt();
	    }
	
	    return rand;
	}

}