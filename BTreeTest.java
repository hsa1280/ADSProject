import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;


public class BTreeTest<T extends Comparable<T>> {


    private int order = 1;
    private int keyofSize = 2 * order; //2
    private int childrenofSize = keyofSize + 1; //3
    // user define the size for the childrenArray, keyArray and valueArray
    public static int inputSize = 10000;
    
    
    private Node<T> root = null;
    private int size = 0;
    
    // produce the random array
    private static Random generator = new Random();

    //Default constructor 2-3 tree
    public BTreeTest(){
    	
    }

    public BTreeTest(int order) {
        this.order = order;
        this.keyofSize = 2 * order;
        this.childrenofSize = keyofSize + 1; // the number of children is larger than key's number
    }

    public void insertValue(T key, T value) {
    	// if the tree is an empty tree, create a root for this tree then add the value to this root
        if (root == null) {
            root = new Node<T>(inputSize, inputSize, inputSize);
            root.addValue(value);
            root.addKey(key);
            
        } 
        else {
                Node<T> node = root;
                while (node != null) {
                	// if there is just a root in the tree
                    if (node.numberOfChildren() == 0) {
                        node.addValue(value);
                        node.addKey(key);
                        if (node.numberOfValues() <= keyofSize) {
                            break;
                        } 
                        else if(node.numberOfValues()>keyofSize){
                            // split the node that is full size
                            split(node);
                            break;
                        }
                    } 
                    else {//if the root has children, then we need to find the position for this inserted value
                        
                        T leftValue = node.getValue(0);
                        // compared the inserted value with the smallest number in the root, if the value is smaller than this number, we go to the left most child of the root
                        if (value.compareTo(leftValue) < 0) {
                            node = node.getChild(0);
                            continue;
                        }
                        // compared the inserted value with the largest number in the root, if the value is larger than this number, we go to the right most child of the root
                        int sizeKey = node.numberOfValues();
                        int last = sizeKey - 1;
                        T rightValue = node.getValue(last);
                        if (value.compareTo(rightValue) > 0) {
                            node = node.getChild(sizeKey);
                            continue;
                        }
                        
                        // if the inserted value is between the smallest and largest number in the root, we need to locate the place for this value
                        for (int i = 1; i < node.numberOfValues(); i++) {
                            T current = node.getValue(i - 1);
                            T next = node.getValue(i);
                            if (value.compareTo(current) > 0 && value.compareTo(next) < 0) {
                                node = node.getChild(i);
                                break;
                            }
                        }
                    }
                }
        }

    }

// split the node when the number of keys in this node is larger than keyofSize
    private void split(Node<T> node) {
        int sizeOfTree = node.numberOfValues();
        int middle = sizeOfTree / 2;
        T medianValue = node.getValue(middle);
        T mdeianKey = node.getKey(middle);

        Node<T> left = new Node<T>(inputSize, inputSize, inputSize);
        //Node<T> left = new Node<T>();
        //copy elements in the left side of middle index to a new node 
        for (int i=0; i<middle; i++) {
            left.addValue(node.getValue(i));
            left.addKey(node.getKey(i));
        }
        //copy the corresponding children to the left node
        if (node.numberOfChildren()>0) {
            for (int j=0; j<=middle; j++) {
                left.addChild(node.getChild(j));
            }
        }
        
      //copy elements in the right side of middle index to a new node 
        Node<T> right = new Node<T>(inputSize, inputSize, inputSize);
        //Node<T> right = new Node<T>();
        for (int i = middle+1; i < sizeOfTree; i++) {
            right.addValue(node.getValue(i));
            right.addKey(node.getKey(i));
        }
      //copy the corresponding children to the right node
        if (node.numberOfChildren()>0) {
            for (int j=middle+1; j<node.numberOfChildren(); j++) {
                right.addChild(node.getChild(j));
            }
        }
        

        if (node.parent == null) {
            Node<T> newnode = new Node<T>(inputSize, inputSize,inputSize);
        	//Node<T> newnode = new Node<T>();
            newnode.addValue(medianValue); //adding the median value to parent
            newnode.addKey(mdeianKey);
            node.parent = newnode;
            root = newnode;
            node = root;
            node.addChild(left);
            node.addChild(right);
        } else {

        	//Node<T> parent = root;
            node.parent.addValue(medianValue);
            node.parent.addKey(mdeianKey);
            node.parent.removeChild(node); // remove the node that is full size 
            node.parent.addChild(left);
            node.parent.addChild(right);

            if (node.parent.numberOfValues() > keyofSize) split(node.parent);  // after adding median value to parent, parent's size is full, split it
        }
    }
    
  //display the keys
   public void display(){
    	
			    	for(int i=0;i<root.numberOfValues();i++){
			    		System.out.print("The root keys are " + root.getValue(i));
			    		System.out.println();
			    		//System.out.println(node.numberOfValues());
			    	}
			    	Node node = null;
			    	while(true){
			    		
			    		//print out the second level of the tree
					    	for(int i=0;i<root.numberOfChildren();i++){
					    		System.out.print("The number of keys for second level " + i + "th child are ");
					    		for(int j = 0;j<root.getChild(i).numberOfValues();j++){
					    			System.out.print(root.getChild(i).getValue(j) + "  ");
					    		}
						    	System.out.println();
					    	}
						    // print out the third level of the tree
						    for(int m=0;m<root.numberOfChildren();m++){
						    		node = root.getChild(m);
						    			    		
						    	for(int n = 0;n<node.numberOfChildren();n++){
						    		System.out.print("The number of keys for third level " + n + "th child are ");
						    		for(int z = 0;z<node.getChild(n).numberOfValues();z++)
						    			System.out.print(node.getChild(n).getValue(z) + "  ");
						    			System.out.println();
						    		}
						    	System.out.println();
					   
					    	//root = root.getChild(m);
			    		}
					
					    	break;
			    	}
			    	}
   // Getting the random permutation 
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

    //get the specified value
    public boolean searchKey(T key) {
        Node<T> node = root;
        while (node != null) {
        	//compare the value with smallest value in the root

            T smallKey = node.getKey(0);
            if (key.compareTo(smallKey) < 0) {
                if (node.numberOfChildren() > 0) node = node.getChild(0);
                else node = null;
                continue;
            }
          //compare the value with largest value in the root
            int size = node.numberOfKeys();
            int last = size - 1;
            T largeKey = node.getKey(last);
            if (key.compareTo(largeKey) > 0) {
                if (node.numberOfChildren() > size) node = node.getChild(size);
                else node = null;
                continue;
            }            
          //if the value is between the smallest value and largest value of the node
            for (int i = 0; i < size; i++) {
                T current = node.getKey(i);
                if (current.compareTo(key) == 0) {
                	//Found the key
                    return true;
                }
                else{
                    int next = i + 1;
                    if (next <= last) {
                        T nextKey = node.getKey(next);
                        if (current.compareTo(key) < 0 && nextKey.compareTo(key) > 0) {
                            if (next < node.numberOfChildren()) {
                                node = node.getChild(next);
                                break;
                            } 
                            else {
                                return false;
                            }
                        }
                    }
                }

  
            }
        }
        return false; // Not find the value return false
    }
    
    //Level order output
    public static void levelorder(Node n)
    {
    	int size = 0;
     Queue<Node> nodequeue = new LinkedList<Node>();
     if (n != null)
      nodequeue.add(n);
     while (!nodequeue.isEmpty())
     {
      Node next = nodequeue.remove();
      for(int i = 0;i<n.numberOfValues();i++){
          System.out.print(next.getValue(i) + " ");
      }

      if (n.numberOfChildren()>0)
      {
    	  //if(size < )
       //nodequeue.add(next.getLeft());
      }
     }
    }

//Definition of Node class
    private static class Node<T extends Comparable<T>> {

        private T[] valueArray = null;
        private int valueSize = 0;
        private Node<T>[] childrenArray = null;
        private int childrenofSize = 0;
        private T[] keyArray = null;
        private int keySize = 0;

        private Comparator<Node<T>> comparator = new Comparator<Node<T>>() {
            @Override
            public int compare(Node<T> arg0, Node<T> arg1) {
                return arg0.getValue(0).compareTo(arg1.getValue(0));
            }
        };
        
        // set the node's parent to null
        protected Node<T> parent = null;


        @SuppressWarnings("unchecked")
        
        public Node(){
        	
        }
        private Node(int valueSize, int childrenofSize, int keySize) {
            this.valueArray = (T[]) new Comparable[valueSize];
            this.childrenArray = new Node[childrenofSize];
            this.keyArray = (T[]) new Comparable[keySize];
        }
        
        //define some functions for the node value
        private T getValue(int index) {
            return valueArray[index];
        }
        //add the value to the tree and sort the key value in the node
        private void addValue(T value) {
        	valueArray[valueSize++] = value;
            Arrays.sort(valueArray,0,valueSize);
        }
        
        private int numberOfValues() {
            return valueSize;
        }
        
        //define some functions for the node key
        private T getKey(int index){
        	return keyArray[index];
        }
        //adding the key to the node that calls this function
        private void addKey(T key){
        	
        	keyArray[keySize++] = key;
        	Arrays.sort(keyArray,0,keySize);
        }
        
        private int numberOfKeys(){
        	
        	return keySize;
        }
        
        //define some functions for the child
        private Node<T> getChild(int index) {
            if (index>=childrenofSize) return null;
            return childrenArray[index];
        }
        
        //add a child to the node and set the node that calls this function to the child's parent
        private void addChild(Node<T> child) {
            child.parent = this;
            childrenArray[childrenofSize++] = child;
            Arrays.sort(childrenArray,0,childrenofSize,comparator);
            //return true;
        }
        
        private void displaykeys(){
        	for(int i = 0;i<valueArray.length;i++){
        		System.out.println("The array is " + valueArray[i]);
        	}
        }
        

        private boolean removeChild(Node<T> child) {
            boolean tof = false;
            if (childrenofSize==0) return tof;
            for (int i=0; i<childrenofSize; i++) {
                if (childrenArray[i].equals(child)) {
                    tof = true;
                    
                } else if(tof){
                    //shift the rest of the keys down
                	childrenArray[i-1] = childrenArray[i];
                }
            }
            if(tof){
                childrenofSize--; // decrease the size of children array by 1
                childrenArray[childrenofSize] = null;
            }
            return tof;
        }
        
        private int numberOfChildren() {
            return childrenofSize;
        }
       
    }
    
    public static void main(String args[]){
    	int order;
    	Scanner orderInput = new Scanner(System.in);
    	System.out.println("Enter the order for the Btree: ");
    	order = orderInput.nextInt();
    	BTreeTest btreeTest = new BTreeTest(order);
    	Node node = new Node(inputSize,inputSize,inputSize);
    	
    	long start1 = 0;
    	start1 = System.currentTimeMillis();
    	
    	Scanner keyboard = new Scanner(System.in);
    	System.out.print("Enter a number: ");
    	int number = keyboard.nextInt();
    	int i=0;
    	int array[] = new int[number];
    	
    	//Generate the random permutation and add them into tree
    	for (int n : BTreeTest.getRandomPermutation(number)){
           int m = n+1;
           btreeTest.insertValue(m, 2*m); // insert the value into Btree
           array[i++] = m;  // create an array for the later search part
           }

    	
    	// sorted the inserted keys, then output it to the BTreesorted file
    	String sr = "";
    	for(int j = 0;j<array.length;j++){
    		
    		Arrays.sort(array, 0, array.length);
    		sr= sr + array[j] + " ";
    	}

    	long stop1=0;
    	stop1 = System.currentTimeMillis();
    	long time1;
    	time1 = stop1 - start1;
    	System.out.println("The insert time is " + time1);
    	
    	long start2 = System.currentTimeMillis();
    	for(int j=0;j<array.length;j++){
    		
    		btreeTest.searchKey(array[j]);  // search for the keys
    	}
    	
    	long stop2=0;
    	stop2 = System.currentTimeMillis();
    	long time2;
    	time2 = stop2 - start2;
    	System.out.println("The search time is " + time2);
    	
    	//the location of the file created to store the BTreeSorted result
		File f1 = new File("BTreeSorted.txt");
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