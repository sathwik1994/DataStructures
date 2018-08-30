import java.util.*;

public class MBSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] inp= new int[100000];
		Random random = new Random();
		for(int i=0; i<100000;i++) {
			inp[i]=random.nextInt();
		}
		for(int i=0; i<inp.length;i++) {
			for(int j=i+1; j<inp.length;j++) {
				if(i==j) {
					System.out.println(i);
					
				}
				
			}
		
		}
		System.out.println("no same number found");
		
		

	}

}
