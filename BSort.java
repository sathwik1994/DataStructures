import java.util.Random;
import java.util.Scanner;

public class BSort {
	public static void sort(int[] a, int maxVal) {
	      int [] bucket=new int[maxVal+1];
	 
	      for (int i=0; i<bucket.length; i++) {
	         bucket[i]=0;
	      }
	 
	      for (int i=0; i<a.length; i++) {
	         bucket[a[i]]++;
	      }
	 
	      int outPos=0;
	      for (int i=0; i<bucket.length; i++) {
	         for (int j=0; j<bucket[i]; j++) {
	            a[outPos++]=i;
	         }
	      }
	   }
	int maxValue(int[] arr) {
		int maxvalue=0;
		for(int i=0; i<arr.length;i++) {
			if(arr[i]>maxvalue) {
				maxvalue=arr[i];
			}
			
		}
		return maxvalue;
		
	}
	void printRange(int[] arr, int m, int n ) {
	    	for(int i=m; i<n;i++) {
	    		System.out.println(arr[i]);
	    	}
	    }


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BSort bs= new BSort();
		int[] inp= new int[10000];
		Random random = new Random();
		for(int i=0; i<10000;i++) {
			inp[i]=Math.abs(random.nextInt());
		}
		int maxvalue=bs.maxValue(inp);
		
		sort(inp,maxvalue);
		Scanner sc= new Scanner(System.in);
		System.out.println("enter range, min and max value");
		int min= sc.nextInt();
		int max= sc.nextInt();
		bs.printRange(inp, min, max);

	}

}