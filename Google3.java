
public class Google3 {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		int[] sample= new int[] {5,4,3,2,6,5,4,3,2,1};
		// akalestundi... emi tini raledu
		for(int i=0; i<sample.length-1;i++) {
			int j=i+1;
			if(sample[j]>sample[i]) {
				System.out.println("wanted:"+sample[j]);
			}
		}
		
		
	}

}
