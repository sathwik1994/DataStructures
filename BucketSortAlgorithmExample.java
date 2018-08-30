import java.util.Random;
import java.util.Scanner;

public class BucketSortAlgorithmExample {
 
    /**
     * Declare initialize array which has to be sorted using Bucket sort in java
     */
	
  
 
    
    /**
     * This method Sort Array using Bucket Sort in java
     */
    static int[] bucketSort(int[] arr,int maxValue) {
 
           int[] bucketArray = new int[maxValue + 1];
 
           for (int i = 0; i < arr.length; i++)
                  bucketArray[arr[i]]++;
 
           // Create sortedArray - to store sorted arr
           int[] sortedArray = new int[arr.length];
 
           int x = 0;
           for (int i = 0; i < bucketArray.length; i++)
                  for (int j = 0; j < bucketArray[i]; j++)
                        sortedArray[x++] = i;
 
           // Now,
           // Copy elements of sortedArray array to arr
           // So, arr will be the sorted array now.
           
           return sortedArray;
    }
 
 
    /**
     * Method to find the maximum value in the array
     */
    static int maxValue(int[] arr) {
           int maxValue = 0;
           for (int i = 0; i < arr.length; i++)
                  if (arr[i] > maxValue){
                        maxValue = arr[i];
                  }
           return maxValue;
    }
   
    /**
     * This method Display Array
     */
 
    public static void main(String[] args) {
 
    	BucketSortAlgorithmExample buck = new BucketSortAlgorithmExample();
    	int[] arr = buck.generateRandomNumbers(100000);
    	int[] sortedArray=BucketSortAlgorithmExample.bucketSort(arr, maxValue(arr));
          
 
            // bucket sort the array
           Scanner sc= new Scanner(System.in);
 
           System.out.println("Enter range");
   	    int min = sc.nextInt();
   	    int max = sc.nextInt();
   	    for(int i=min; i<max;i++)
   	    {
   	    	System.out.println(sortedArray[i]);
   	    	
   	    }
           
    }
 int[] generateRandomNumbers(int n){
		
	    int[] rand = new int[n];
	    Random random = new Random();
		
	    for (int i = 0; i < rand.length; i++) {
		    rand[i] = random.nextInt((100000-0)+1);
	    }
	
	    return rand;
	}
 
}