//package hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class runs the game 
 * it takes a file of strings, then puts them in a hash table
 * after, it then takes the arguments and find the hops needed to get from word 1 to word 2
 * @author Nikolo
 *
 */
public class Game{
	
	//array list of alphabet
	private static ArrayList<Character> abc = new ArrayList<Character>();
	//queue of TNodes
	private static QueueList<TNode> q = new QueueList<TNode>();

	/**
	 * main method
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main (String args[]) throws FileNotFoundException {
		File f = new File("dictionary.txt");
		HashChain<String, String> hash = new HashChain<String, String>(13);
		String w1 = args[0].toLowerCase();
		String w2 = args[1].toLowerCase();
		int x = Integer.parseInt(args[2]);
		fill();
		read(f, hash);
		if (queen(w1, w2)) { //easter egg =D
			System.exit(0);
		}
		if (check(hash, w1, w2)) {
			System.exit(0);
		}
		if (sameWord(w1, w2, x)) {
			System.exit(0);
		}
		if(negative(x)) {
			System.exit(0);
		}
		makeTree(hash, w1, w2, x);
		hops(w2, x);
	}
	
	/**
	 * this method takes the file and creates a hash chain of the given strings
	 * @param f
	 * @param hash
	 * @throws FileNotFoundException
	 */
	public static void read(File f, HashChain hash) throws FileNotFoundException {
		String word = "";
		Scanner file = new Scanner(f);
		while (file.hasNextLine()) {
			word = file.nextLine();
			hash.insert(new HashNode(word, word));
		}
	}
	
	/**
	 * this method makes a pseudo tree of TNodes used to find the hops needed for the game
	 * @param hash
	 * @param w1
	 */
	public static void makeTree(HashChain hash, String w1, String w2, int x) {
		int count = 0;//count to keep track of hops made
		ArrayList<String> queued = new ArrayList<String>();//array list of words already queued
		TNode parent = new TNode();//parent node
		QueueList<TNode> q2 = new QueueList<TNode>();//queue off all children nodes made to make there children
		parent.setElement(w1);//set to 1st node (root node)
		q.enqueue(parent);//enques the root into the queue for the breadth first search
		queued.add(parent.getElement().toString());//enqueues the parent node in queue two
		StringBuilder str = new StringBuilder(w1);//creates string builder to make new words
		String word = w1;//sets word to w1
		String word2 = w1;//sets word2 to word
		boolean flag = true;//flag used to end while loop
		while(flag) {// flag false until destination word is made
			for(int i = 0; i <= w1.length() - 1; i++) {//loop through each letter in the word
				word2 = word;//sets word2 to word
				str = new StringBuilder(word2);//sets string builder to word2
				for(int j = 0; j < 26; j++) {//loop each letter in alphabet
					str.setCharAt(i, abc.get(j));//change letter i to a-z
					word2 = str.toString();//sets word to new word
					if (hash.search(word2, word2) != null) {//check if new word is in hash table
						if(isNotQueued(word2, queued)) {//checks to see if new word is in tree
							TNode t = new TNode();//create new node
							t.setElement(word2);//set element of new word
							t.setParent(parent);//set parent node
							queued.add(word2);//add word to the queued list
							q.enqueue(t);//enqueues node to the queue for breadth first search 
							q2.enqueue(t);//enques node into 2nd queue
							if(word2.equals(w2)) {//if destination word is found exit while loop
								flag = false;
							}
						}
					}
				}
			}
			if(!q2.isEmpty()) {
				parent = q2.dequeue();//sets new parent from queue of kids to make that kid's kids
				word = parent.getElement().toString(); //sets word to parent to make kids of current parent
			}
		}
	}
	
	/**
	 * this method uses the pseudo tree to preform a breadth first search, after it count the hops needed 
	 * to go from word one to word two
	 * @param w2
	 * @param x
	 */
	public static void hops(String w2, int x) {
		TNode t = new TNode();
		String temp = "";
		int count = 0;
		while(!q.isEmpty()) {
			t = q.dequeue();
			temp = t.getElement().toString();
			if(temp.equals(w2)) {
				while(t.getParent() != null) {
					t = t.getParent();
					temp = t.getElement().toString() + " " + temp;
					count++;
				}
				if(count > x) {
					System.out.println("Solution may be beyond given depth.");
					System.out.println("There is no solution.");
					System.exit(0);

				}
				else {
					System.out.println("Can make it in " + count +" hops.");
					System.out.println(temp);
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * this method checks if the words passed are valid
	 * @param hash
	 * @param w1
	 * @param w2
	 */
	public static boolean check(HashChain hash, String w1, String w2) {
		if(w1.length() != w2.length()) { //checks length is same
			System.out.println("The given words are not the same length.");
			System.out.println("There is no solution.");
			return true;
		}
		if(hash.search(w1, w1) == null){ //checks word is in hash chain
			System.out.println(w1 + " is not in the hash chain.");
			System.out.println("There is no solution.");
			return true;
		}
		if(hash.search(w2, w2) == null){ //checks word is in hash chain
			System.out.println(w2 + " is not in the hash chain.");
			System.out.println("There is no solution.");
			return true;
		}
		return false;
	}
	
	/**
	 * this method checks if the two words are the same
	 * @param w1
	 * @param w2
	 * @return
	 */
	public static boolean sameWord(String w1, String w2, int x) {
		if (w1.equals(w2)) {
			System.out.println(".... Seriously?  Those are the same word."); 
			return true;
		}
		return false;
	}
	
	/**
	 * this is a helper method that checks if the word is already in the tree
	 * @param word
	 * @param queued
	 * @return
	 */
	public static boolean isNotQueued(String word, ArrayList queued) {
		for(int i = 0; i < queued.size(); i++) {
			if (word.equals(queued.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * this method fills the array list with the alphabet
	 */
	public static void fill() {
		abc.add('a');
		abc.add('b');
		abc.add('c');
		abc.add('d');
		abc.add('e');
		abc.add('f');
		abc.add('g');
		abc.add('h');
		abc.add('i');
		abc.add('j');
		abc.add('k');
		abc.add('l');
		abc.add('m');
		abc.add('n');
		abc.add('o');
		abc.add('p');
		abc.add('q');
		abc.add('r');
		abc.add('s');
		abc.add('t');
		abc.add('u');
		abc.add('v');
		abc.add('w');
		abc.add('x');
		abc.add('y');
		abc.add('z');
		//abc.add('\'');
	}
	
	/**
	 * method used for debugging
	 * prints queue
	 */
	public static void printQueue() {
		while(!q.isEmpty()) {
			System.out.println(q.dequeue().getElement().toString());
		}
	}
	
	public static boolean negative (int x) {
		if(x < 0) {
			System.out.println("That's a negative number, you silly goose.");
			return true;
		}
		return false;
	}
	
	/**
	 * easter egg... I was listening to RuPaul while coding and Drag Race All Stars season 4 airs today (12/14) 
	 * I am really excited
	 * @param w1
	 * @param w2
	 * @return
	 */
	public static boolean queen (String w1, String w2) {
		if(w1.equals("rupaul")) {
			System.out.println("I have one thing to say.");
			System.out.println("You better WERK!");
			return true;
		}
		if(w2.equals("rupaul")) {
			System.out.println("If you can't love yourself, how the hell can you love somebody else?");
			System.out.println("Can I get an amen?!?");
			return true;
		}
		return false;
	}
}