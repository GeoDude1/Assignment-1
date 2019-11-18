//Class that represents a Trie for the words of the dictionary
public class Trie implements Storage { 
	private TrieNode root;
	public int size;

	public Trie() {
		root = new TrieNode();
		size = 0;
	}

	//method that inserts a new String word into the Trie
	public void insert(String value) 
	{
		size++;
		//Here, it needs to look for the actual length of the word, which would be the level on the trie
		int length = value.length();
		TrieNode actual = root;
		for (int level = 0; level < length; level++) 
		{
			//Here, it gets the index of the next letter/char of the word
			int index = value.charAt(level);
			//If the children at the actual level is null, we create a new TrieNode for it
			if (actual.children[index] == null) {
				actual.children[index] = new TrieNode(); 
			}
			actual = actual.children[index]; //Get to the next children for the next level
		} 
		//Once it is on the level it is looking for, it makes the actual node a leaf, which means it makes it true for endWord
		actual.endWord = true; 
	} 

	//Method that searches a String inside the current Trie
	public boolean search(String value) 
	{ 
		//To search for a given String, it starts looping until it gets to the desired level
		//If the path to get to the level has a null child in some place, then return false directly
		int length = value.length(); 
		TrieNode actual = root; 
		for (int level = 0; level < length; level++) 
		{ 
			//It gets the index of the next letter/char
			int index = value.charAt(level); 
			//If the children at i index is null, then false
			if (actual.children[index] == null) 
				return false; 
			//If not, it goes to the next node (children) to get the next level Node
			actual = actual.children[index]; 
		} 
		//Once it gets to the node on the level it needs, it checks if it's the end of a word
		//Then, it returns just endWord for the Node
		return actual.endWord;
	}

	//Private class for a Trie Node to be stored
	private class TrieNode 
	{ 
		//Constant representing the max amount of children a certain Node can have, this is ASCII chars
		private final int SIZE = 127; 

		//Fields of the Node
		private TrieNode[] children; //Array of children
		private boolean endWord; //Boolean that represents if the current Node is the end of a word or not (not just prefix)

		//Constructor of the Node
		public TrieNode(){ 
			children = new TrieNode[SIZE];
			endWord = false;
		}
	}
}
