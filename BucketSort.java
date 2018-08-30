import java.util.Random;
import java.util.Scanner;

public class BucketSort {
	
    static int[] bucketSort(int[] input,int highValue) {
 
           int[] arrayBucket = new int[highValue + 1];
 
           for (int i = 0; i < input.length; i++)
                  arrayBucket[input[i]]++;
            int[] arraySorted = new int[input.length];
 
           int x = 0;
           for (int i = 0; i < arrayBucket.length; i++)
                  for (int j = 0; j < arrayBucket[i]; j++)
                        arraySorted[x++] = i;          
           return arraySorted;
    }
 
    static int highValue(int[] input) {
           int highValue = 0;
           for (int i = 0; i < input.length; i++)
                  if (input[i] > highValue){
                	  highValue = input[i];
                  }
           return highValue;
    }

 
    public static void main(String[] args) {
 
    	BucketSort buck = new BucketSort();
    	int[] arr = buck.generateRandomNumbers(100000);
    	 int n = 100000;
 	    int[] in = new int[n];
 	    for(int i=0; i<n;i++)
 	    {
 	    	in[i]=arr[i];
 	    }
	    long startTime = System.nanoTime();
    	int[] arraySorted=BucketSort.bucketSort(in, highValue(arr));
    	long stopTime = System.nanoTime();
	    long elapsed = stopTime-startTime;
	    System.out.println(elapsed);
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter range");
   	    int min = sc.nextInt();
   	    int max = sc.nextInt();
   	    if(min>max)
   	    {
   	    	System.out.println("Enter minimum first, then maximum");
   	    }
   	    
   	    for(int i=min; i<max;i++)
   	    {
   	    	System.out.println(arraySorted[i]);
   	    	
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