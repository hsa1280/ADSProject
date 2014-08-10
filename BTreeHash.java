import java.util.Random;
import java.util.Scanner;
import java.io.*;


public class BTreeHash {

	private static Random generator = new Random();
	// This function generates a random order of n numbers
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
    
	
	public static void main(String args[]){
		
		BTreeHash bthash = new BTreeHash();
		BTreeTest btree [] = new BTreeTest[101];
		
    	long start1 = System.currentTimeMillis();
    	Scanner keyboard1 = new Scanner(System.in);
    	Scanner keyboard2 = new Scanner(System.in);
    	    	
    	System.out.print("Enter a number: ");
    	
    	int number = keyboard1.nextInt();
    	
    	System.out.print("Enter the size of hash table: ");
    	int hashsize = keyboard2.nextInt();
    	
    	
    	int valueArray[] = new int[number];
    	int i=0;
    	int modeValue;
		int value;
		//this loop is used to initialize the Btree array
	   	for(int l=0;l<number;l++){
    		btree[(l+1)%hashsize] = new BTreeTest();
    	}

    	for (int n : BTreeTest.getRandomPermutation(number)){
    		
    		modeValue = (n+1)%hashsize;   // get the reminder of key mode s
    		btree[modeValue].insertValue(n+1, 2*(n+1)); //insert the value into corresponding tree
          	valueArray[i++] = n+1;//store these numbers into an array which is used in the search part
           }
    	long stop1 = System.currentTimeMillis();
    	long time1 = stop1 - start1;
    	System.out.println("The insert time is " + time1);
    	
    	long start2 = System.currentTimeMillis();
    	
    	for(int j=0;j<valueArray.length;j++){
    		btree[valueArray[j]%hashsize].searchKey(valueArray[j]);  //search for the key in the inserted order
    	}
    	
    	long stop2 = System.currentTimeMillis();
    	long time2 = stop2 - start2;
    	System.out.println("The search time is " + time2);
	}

}
