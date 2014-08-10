import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.*;


public class AVLTreeHash {

	// This function generates a random order of n numbers
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
    
    // hash function to get the mode of key and hash table size
	public int keyModeS(int number, int hashSize){
		return number%hashSize;
	}
	
	
	public static void main(String args[]){
		
		AVLTreeHash avlhash = new AVLTreeHash();
		
		//According to the reminder of key and table size, we store keys into different trees
		AVLTreeTest avltree [] = new AVLTreeTest[101];
		
    	Scanner keyboard1 = new Scanner(System.in);
    	Scanner keyboard2 = new Scanner(System.in);
    	    	
    	System.out.print("Enter a number: ");
    	
    	int number = keyboard1.nextInt();
    	
    	System.out.print("Enter the size of hash table: ");
    	int hashsize = keyboard2.nextInt();
    	
    	int modeValue;
    	int i=0;
    	int array [] = new int[number];

    	long start1 = System.currentTimeMillis();
    	
    	//this loop is used to initialize the AVLTree array
    	for(int l=0;l<hashsize;l++){
    		avltree[l%hashsize] = new AVLTreeTest();
    	}
    	for (int n : avlhash.getRandomPermutation(number)){
    		
    		modeValue = n%hashsize;
          	avltree[modeValue].insertValue(n, 2*n);
          	array[i] = n;  //store these numbers into an array which is used in the search part
          	i++;
           }
    	long stop1 = System.currentTimeMillis();
    	long time1 = stop1 - start1;
    	System.out.println("The insertion time is " + time1);
    	
    	long start2 = System.currentTimeMillis();
    	for(int j=0;j<array.length;j++){
    		avltree[array[j]%hashsize].searchValue(array[j]);
    		
    	}
    	

    	// write out the AVLTree Hash AVLHash_inorder.out file
		String sr="";
    	for(int k = 0;k<hashsize;k++){
    		System.out.println(k);
    		sr = sr + avltree[k%hashsize].inOrder(avltree[array[k]%hashsize].getRoot()) + " \n";
    	}
    	long stop2 = System.currentTimeMillis();
    	long time2 = stop2 - start2;
    	System.out.println("The search time is " + time2);

		File f1 = new File("AVLHash_inorder.txt");
    				FileOutputStream fos1;

    				try {
    					fos1 = new FileOutputStream(f1);

    					PrintWriter pw1 = new PrintWriter(fos1);
    					pw1.write(sr);
    					pw1.flush();
    					fos1.close();
    					pw1.close();
    					System.out.println();

    				} catch (FileNotFoundException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    		}
    	
    	
}
