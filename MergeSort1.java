import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class MergeSort1 {
    int [] sortedArray = new int[1000010];
    int [] tempArray = new int[1000010];
    MergeSort1(){
        Arrays.fill(sortedArray,-1);
    }
    public void merge(int low, int middle, int high){
        for(int i= low; i<= high; i++){
            tempArray[i] = sortedArray[i];
        }
        int i = low;
        int j = middle+1;
        int k= low;
        while (i<=middle && j<=high){
            if(tempArray[i]<=tempArray[j]) {
                sortedArray[k] = tempArray[i];
                i++;
            }else{
                sortedArray[k] = tempArray[j];
                j++;
            }
            k++;
        }
        while(i<=middle){
            sortedArray[k] = tempArray[i];
            k++;
            i++;
        }
        while(j<=high){
            sortedArray[k] = tempArray[j];
            k++;
            j++;
        }
    }
    public void mergeSort(int low, int high){
        if(low < high){
            int middle = low+(high - low)/2;
            mergeSort(low,middle);
            mergeSort(middle+1, high);
            merge(low, middle, high);
        }
    }
    public static void main(String[] args){
    System.out.println("Enter your input file name:");
    Scanner fileName = new Scanner(System.in);       
	String file = fileName.nextLine();
    try{
	int numberOfLines = 0;
	for(int k =0;k < 100;k++)
	{
	int startIndex = 0;
	int count = 0;
	MergeSort1 sort = new MergeSort1();
	File locationOfInputFile = new File(file);
        Scanner inputFile = new Scanner(locationOfInputFile);
		numberOfLines = numberOfLines+100;
		for(int l = 0;l<numberOfLines;l++)
		{
            String[] inputIntegers = inputFile.next().split(",");
            int j = 0;
                for (int i = startIndex; i < inputIntegers.length+startIndex; i++) {
                            sort.sortedArray[i] = Integer.parseInt(inputIntegers[j]);
                            count++;
                    j++;
                }
            startIndex = count;
                if(count == (numberOfLines * 10)) {
			inputFile.close();
                    int highIndex = count-1;
                    long startTime = System.nanoTime();
			System.out.println("Before Sort");
			for(int i = 0;sort.sortedArray[i] != -1;i++){
			System.out.print(sort.sortedArray[i]+" ");
			}
			System.out.println();
                    sort.mergeSort(0, highIndex);
                    long endTime = System.nanoTime();
			System.out.println("After Sort");
			for(int i = 0;sort.sortedArray[i] != -1;i++){
                        System.out.print(sort.sortedArray[i]+" ");
                        }
			System.out.println();
                    long mergeTime = endTime - startTime;
		    System.out.println("Execution time in NanoSeconds: "+mergeTime);
                    try(FileWriter fw = new FileWriter("MergeSort.csv", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw))
                    {
                        out.println(mergeTime+","+count);
                    } catch (IOException e) {
                        System.out.println("Unable to create file");
                    }
		break;		
		}
                }
	}
    }catch(FileNotFoundException e){
        System.out.println("Input file path is not valid");
    }
}
}