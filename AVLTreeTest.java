import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;



public class AVLTreeTest {
	private Node root=null;
	ArrayList<Integer> postOrderArray = new ArrayList<Integer>();
	ArrayList<Integer> inOrderArray = new ArrayList<Integer>();
		
	public class Node{
		
		private Node leftNode=null;
		private Node rightNode=null;
		private Node parent=null;
		private int nodeValue;
		private int nodeKey;

		private int balance;
		private int height;
		//default constructor
		public Node(){
			
			
		}
		//Setting the key and value for the node
		public Node(int key, int value){
			
			this.nodeValue = value;
			this.nodeKey = key;
		}
		
		private void setLeftNode(Node node){
			this.leftNode = node;
			if(leftNode != null)
			node.parent = this;
		}
		
		private void setRightNode(Node node){
			this.rightNode = node;
			if(rightNode != null)
			node.parent = this;
		}
		
		//setting the root and root's parent is null
		private void setRoot(){
			AVLTreeTest.this.root=this;
			parent = null;
		}
		//exchange a node with specified node
		private void exchangeNode(Node node1, Node node2) {
			
			if(leftNode == node1)
				setLeftNode(node2);
			
			else if(rightNode == node1)
				setRightNode(node2);
			
			else
				throw new IllegalArgumentException("Cannot replace node .");
			
		}
		
		private void update() {
			
			int[] childHeight = childHeight();
			
			balance = childHeight[0] - childHeight[1];
			height = Math.max(childHeight[0], childHeight[1]) + 1;
			
		}
		
		//Computes an array containing the heights of both sons
		private int[] childHeight() {
			
			return new int[] {
				rightNode == null ? 0 : rightNode.height,
				leftNode == null ? 0 : leftNode.height
			};
			
		}		
			
	}
		// search for a specified key, and return the corresponding key's value
	public boolean searchValue(int key){
		
		Node node = root;
		while(node !=null){
			if(node.nodeKey == key){
				//System.out.println("The value " + node.nodeValue + " is found !");
				//Found the key, return. 
				return true;
			}
			
			else if(key < node.nodeKey){
				node = node.leftNode;
				continue;
			}
			
			else if(key > node.nodeKey){
				node = node.rightNode;
				continue;
			}
		}
		
		System.out.println("The value is not found !");
		return false;
	}
			
	public void insertValue(int key, int value){
		
		if(root == null){
			new Node(key, value).setRoot();  // set the inserted value as the root
			//System.out.println("Setting the key " + key + " as the root, " + "Its value is " + value);
			return;
		}
		Node node = root;
		while(true){				
			
			if(value < node.nodeValue){
				//System.out.printf("Adding key " + key + " The key's value is " + value + " -> Smaller than %d : Going left%n", node.nodeKey);
				if(node.leftNode == null){
					node.setLeftNode(new Node(key, value)); // place the value to the left node
					//System.out.printf(" -> Setting as left child of %d%n", node.nodeValue);
					up(node); // inform all the nodes 
					return;
				}
				else
				node = node.leftNode; // if the left node is non empty, continue to search 

			}
			
			if(value > node.nodeValue){
				//System.out.printf("Adding key " + key + " The key's value is " + value + " -> Larger than %d : Going right%n", node.nodeKey);
				if(node.rightNode == null){
					node.setRightNode(new Node(key, value)); // place the value to the left node
					//System.out.printf(" -> Setting as right child of %d%n", node.nodeValue);
					up(node);
					return;
				}
				else
				node = node.rightNode;

			}
			
		}

	}
	
	// return root
	public Node getRoot(){
		return root;
	}
		
	private void up(Node node) {
		
		Node nextNode = node.parent;
		int preHeight = node.height;
		
		node.update();
		
		// Check if tree node is out of balance, if yes, we need to rotate the node
		if(Math.abs(node.balance) >= 2)
			node = Rotation(node);
		
		// Update parent nodes only if the tree root was not yet reached
		// and if the height of the current node was altered.
		if(nextNode != null && preHeight != node.height)
			up(nextNode);
		
	}
		
	private Node Rotation(Node node) {
		
		//the tree is not balanced
		if (node.balance == 2)		
			if(node.rightNode.balance == -1)
				return rotateLeftDouble(node);		
			else 
				return rotateLeft(node);
		
		else 
			if(node.leftNode.balance == 1)
				return rotateRightDouble(node);			
			else 
				return rightRotation(node);
		
	}
	
	// Single right rotation
	private Node rightRotation(Node node) {
		
		Node s = node.leftNode;

		//System.out.printf(" -> Right single rotation on elements a: %d, s: %d%n", node.nodeValue, node.nodeValue);
		
		if (node == root)
			s.setRoot();
		else
			node.parent.exchangeNode(node, s);
		
		node.setLeftNode(s.rightNode);
		node.update();
		
		s.setRightNode(node);
		s.update();
		
		return s;
		
	}
	
	// Single left rotation
	private Node rotateLeft(Node node) {
		
		Node s = node.rightNode;

		//System.out.printf(" -> Left single rotation on elements a: %d, s: %d%n", node.nodeValue,s.nodeValue);
		
		if (node == root)
			s.setRoot();
		else
			node.parent.exchangeNode(node, s);
		
		node.setRightNode(s.leftNode);
		node.update();
		
		s.setLeftNode(node);
		s.update();
		
		return s;
		
	}
	
	//Left -Left rotation
	private Node rotateLeftDouble(Node node) {
		
		Node s = node.rightNode;
		Node b = s.leftNode;

		//System.out.printf(" -> Double left rotation on elements a: %d, s: %d, b: %d%n", node.nodeValue, node.nodeValue, b.nodeValue);
		
		if (node == root)
			b.setRoot();
		else
			node.parent.exchangeNode(node, b);

		node.setRightNode(b.leftNode);
		node.update();
		
		s.setLeftNode(b.rightNode);
		s.update();
		
		b.setLeftNode(node);
		b.setRightNode(s);
		b.update();	
		
		return b;
		
	}
	
	//RR rotation
	private Node rotateRightDouble(Node node) {
		
		Node s = node.leftNode;
		Node b = s.rightNode;
		
		//System.out.printf(" -> Double right rotation on elements a: %d, s: %d, b: %d%n", node.nodeValue, s.nodeValue, b.nodeValue);
		
		if (node == root)
			b.setRoot();
		else
			node.parent.exchangeNode(node, b);
			
		node.setLeftNode(b.rightNode);
		node.update();
		
		s.setRightNode(b.leftNode);
		s.update();
		
		b.setRightNode(node);
		b.setLeftNode(s);
		b.update();
		
		return b;
		
	}
		
	// inorder output the tree
	public ArrayList inOrder(Node node){
		
		if(node != null)
		{
			inOrder(node.leftNode);
			//System.out.print("inorder " + node.nodeValue + " ");
			inOrderArray.add(node.nodeValue);
			inOrder(node.rightNode);
			return inOrderArray;
		}
		return null;
	}
	
	//postOrder 
	public ArrayList postOrder(Node node)
	{
		if(node != null)
		{
			postOrder(node.leftNode);
			postOrder(node.rightNode);
			//System.out.print("postorder " + node.nodeValue + " ");
			postOrderArray.add(node.nodeValue);
			return postOrderArray;
			
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		
		AVLTreeTest avl = new AVLTreeTest();
		
		long start1;
    	start1 = System.currentTimeMillis();
    	
    	Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter a number: ");
		int number = keyboard.nextInt();
		int array [] = new int[number];
		int i = 0;
		// get the random permutations then insert them into AVLTree
		for (int n : BTreeTest.getRandomPermutation(number)){
	      	 int m = n+1;
	      	 array[i++] = m;
	      	 avl.insertValue(m, 2*m);  // insert the values
      	 }
		long stop1 = System.currentTimeMillis();
		long time1 = stop1 - start1;
		System.out.println("The insert time is " + time1);

		long start2 = System.currentTimeMillis();
	
		for(int j=0;j<array.length;j++){
		
			avl.searchValue(array[j]);  //search for the keys in inserted order
		}

		long stop2=0;
		stop2 = System.currentTimeMillis();
		long time2;
		time2 = stop2 - start2;
		System.out.println("The search time is " + time2);
	
	//write out the inserted numbers into AVL_postorder.txt and d:/AVL_inorder.txt respectively
	File f1 = new File("AVL_postorder.txt");
	FileOutputStream fos1;
	File f2 = new File("AVL_inorder.txt");
	FileOutputStream fos2;
	try {
		fos1 = new FileOutputStream(f1);
		fos2 = new FileOutputStream(f2);
		
		PrintWriter pw1 = new PrintWriter(fos1);
		pw1.write(avl.postOrder(avl.getRoot()) + "");
		pw1.flush();
		fos1.close();
		pw1.close();
		System.out.println();
		PrintWriter pw2 = new PrintWriter(fos2);
		pw2.write(avl.inOrder(avl.getRoot()) + "");
		pw2.flush();
		fos2.close();
		pw2.close();
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
}
