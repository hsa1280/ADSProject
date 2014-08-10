import java.io.File;
import java.util.*; 
import java.util.TreeMap;
class TreeMapDemo { 
	
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
	
	public static void main(String args[]) { 

		TreeMapDemo tmdemo = new TreeMapDemo();
		TreeMap tm = new TreeMap(); 
		
		int number;	
		long start1;
    	start1 = System.currentTimeMillis();
		
    	Scanner keyboard1 = new Scanner(System.in);
		System.out.print("Enter a number: "); 	
    	number = keyboard1.nextInt();
    
    	int i=0;
    	int array [] = new int[number];

    	//According to the reminder of key and table size, we store keys into different trees
    	for (int n : tmdemo.getRandomPermutation(number)){
    		
          	 tm.put(n+1, 2*(n+1)); //insert the values into the red black tree
          	 array[i++] = n+1;  //store these numbers into an array which is used in the search part
           }
		long stop1=0;
    	stop1 = System.currentTimeMillis();
    	long time1;
    	time1 = stop1 - start1;
    	System.out.println("The insert time is " + time1);
    	
    	long start2 = System.currentTimeMillis();
    	for(int j=0;j<array.length;j++){
    		tm.containsKey(array[j]);  // search for the keys in the inserted order
    	}
		long stop2=0;
    	stop2 = System.currentTimeMillis();
    	long time2;
    	time2 = stop2 - start2;
    	System.out.println("The search time is " + time2);
    	
    } 
}