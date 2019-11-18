//Class that represents a Search Tree using Binary tree implementation
public class BinarySearchTree implements Storage {

	//Fields of the class
	private BinaryNode root;

	//Constructor of the class
	public BinarySearchTree() {
		root = null;
	}

	//Method that inserts a new String word into the tree
	public void insert(String value) {
		//First, it creates the Node for the current value
		BinaryNode newNode = new BinaryNode(value);
		//Now, if the root is null, it just makes it the root
		if(root == null){
			root = newNode;
		}
		//If not, it starts looping from the root to go deep in the tree
		else{
			//it loops in order to add it to the correct place
			BinaryNode actual = root;
			while(true)
			{
				//Test for the left of the node
				if(actual.data.compareTo(newNode.data) > 0)
				{
					if(actual.left == null) {
						actual.left = newNode;
						break;
					}
					else
						actual = actual.left;
				}
				else{
					if(actual.right == null) {
						actual.right = newNode;
						break;
					}
					else
						actual = actual.right;
				}
			}
		}
	}
	//Method that looks for a given word inside the tree
	public boolean search(String value) {
		//It starts from the root. If null, then false
		BinaryNode actual = root;
		while(true) {
			//If the actual one is null, it means the word doesn't exists, so false
			if(actual == null)
				return false;
			//If not, it checks if the node is the one it is looking for, or if it needs to go to left or right
			else{
				//If the actual one has the value it is looking for, then true
				if(actual.data.equals(value))
					return true;
				//If not, if the actual one is less than the value, it goes to the right child to keep searching
				else if(actual.data.compareTo(value) < 0)
					actual = actual.right;
				//Else, if the actual one is greater than the value, it goes to the left child to keep searching
				else
					actual = actual.left;
			}
		}
	}
	//Private class for a Binary Node to be stored
	private class BinaryNode
	{
		private String data;
		private BinaryNode right; 
		private BinaryNode left;

		public BinaryNode(String data) {
			this.data = data;
			this.right = null;
			this.left = null;
		}
	}
}
