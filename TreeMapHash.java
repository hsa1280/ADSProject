import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class TreeMapHash {

private static Random generator = new Random();
	
    public static int[] getRandomPermutation (int length){

	    int[] array = new int[length];
	    for(int i = 0; i < array.length; i++)
	        array[i] = i;

	    for(int i = 0; i < length; i++){

	        int ran = i + generator.nextInt (length-i);

	        int temp = array[i];
	        array[i] = array[ran];
	        array[ran] = temp;
	    }                       
	    return array;
	}    
	
	public int keyModeS(int key, int tableSize){
		return key%tableSize;
	}
	
	public static void main(String args []){
		
		TreeMapHash tmhash = new TreeMapHash();
		TreeMap tm [] = new TreeMap[101];
		int number, hashsize;
		long start1;
    	start1 = System.currentTimeMillis();
		
    	Scanner keyboard1 = new Scanner(System.in);
    	Scanner keyboard2 = new Scanner(System.in);
		System.out.print("Enter a number: ");
    	
    	number = keyboard1.nextInt();
    	
    	System.out.print("Enter the size of hash table: ");
    	hashsize = keyboard2.nextInt();	


    	int i=0;
    	int array [] = new int[number];
    	int modeValue;

    	// initialize the Red Black tree array
       	for(int l=0;l<number;l++){
    		tm[(l+1)%hashsize] = new TreeMap();
    	}
      //According to the reminder of key and table size, we store keys into different trees
    	for (int n : tmhash.getRandomPermutation(number)){
    		
    		modeValue = (n+1)%hashsize;   //get the reminder of key mode s
          	 tm[modeValue].put(n+1, 2*(n+1));
          	 array[i++] = n+1;//store these numbers into an array which is used in the search part
           }
		long stop1=0;
    	stop1 = System.currentTimeMillis();
    	long time1;
    	time1 = stop1 - start1;
    	System.out.println("The insert time is " + time1);
    	
    	long start2 = System.currentTimeMillis();
    	for(int j=0;j<array.length;j++){
    		tm[array[j]%hashsize].containsKey(array[j]); // search for the keys in inserted order
    	}
		long stop2=0;
    	stop2 = System.currentTimeMillis();
    	long time2;
    	time2 = stop2 - start2;
    	System.out.println("The search time is " + time2);
	}
}
