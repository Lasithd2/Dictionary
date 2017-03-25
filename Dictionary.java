import java.io.*; //Imports the package which has classes supporting file input and output
import java.util.*; //Imports the package in which the Scanner class is in
import javax.swing.*;//Imports the package with classes used to create GUI widgets(buttons,labels,ect)
import java.awt.*;//Imports the package which has GUI capabilities
import java.awt.event.*; //Imports the package in which ActionListener and Actionevent  are in

//creating a class BinaryTree to implement Binary search tree operations
//inheriting from the JFrame class the GUI components because Dictionary class can't be given multiple inheritance for Binary Search Tree and JFrame
class BinaryTree extends JFrame{
	 Scanner x; //x is the scanner type variable used to read the input file
	 static Node root; //variable used to implement the node class operations in the Dictionary class
	 static BinaryTree y = new BinaryTree(); //creating the BinaryTree class object inorder to support in carryingout Binary search tree operations


//Declaring a method to add a node to the Binary Tree when 2 paramaeters of type String are given
public void addNode(String word,String meaning){
		Node newNode = new Node(word,meaning);
		if(root==null){ //considering the situation where a node is added to an empty tree
			root=newNode; //The new node is made the root if the tree is initially empty.
		}else{
			Node focusNode = root; //Declaring another node and starting its operations from the root

			Node parent;
			while(true){
//compareTo method is used to compare 2 String variables and return an integer value so that the node can be added to the correct postion of the tree
				parent = focusNode;
				if(word.compareTo(focusNode.word)<0){ //if a negative value is returned from the compareTo method the node is added as a node in the left subtree

					focusNode = focusNode.leftChild;//Makin the node focusNode is made the left child

					if(focusNode==null){
						parent.leftChild = newNode;//if the left child has no children placing the new node in the left of it.
						return; //exit the method
					}
				}else{ //if the integer value is returned is greater than 1 the node is added as a child of the right subtree

					focusNode=focusNode.rightChild;
					if(focusNode==null){
						parent.rightChild = newNode;//if the right child has no children placing the new node in the right of it.
						return;//exit the method

					}
				}
			}
		}
	}
//Declaring a method to find the meaning, when a word is given
  public String findNode(String word){
		Node focusNode =root; //setting the root of the tree to variable focusNode of data type Node
		while(word.compareTo(focusNode.word)!=0){ //comparing and searching until the required node is found
			if(word.compareTo(focusNode.word)<0){
				 focusNode =focusNode.leftChild;//if the compareTo method returns a negative value searching is done in the left subtree

			}else{
				 focusNode = focusNode.rightChild;//if the compareTo method returns a positive value searching is done in the right subtree
			}
		}
		return focusNode.meaning; //returns the meaning of the word
	}

//Declaring the method to find similar words for a given word
public String similarWords(String word){
	String z= y.findNode(word); //getting the meaning of the word into a variable inorder to get similar words using the same meaning
	String n=" "; //empty string used to concatanate simiar words

//exception handling if the input file is unavaialble
	try{
			x = new Scanner(new File("Dictionary.txt")); //reading the file using Scanner variable x
		}
		catch(Exception e){
			System.out.println("could not find file"); //returning an error message if Dictionary.txt does not exist in the  folder
		}

while(x.hasNextLine()){ //reading until the end of the text file

		       String a = x.next(); //getting the 1st word of each line into a string variable(word)

			   String b= x.nextLine(); //getting the rest of  each line to another String varible(meaning)

			   if(b.equals(z) && (a.compareTo(word)!=0)){ //finding words having the same meaning and not duplicating the given word as a similar word


				   n+=a+"\n"; //concatanating similar words to the empty string and retruning each similar word in the next line
			   }
		   }

		   return n; //returnng  the set of similar words.
}
//Creating the method to delete a node
public boolean delete(String word){
	Node focusNode = root; //starting the search of the node before deletion, starting at the root
	Node parent = root;
	boolean isAtLeftChild = true;
	while(word.compareTo(focusNode.word)!=0){//Continue searching until the node with  the given word is found
		parent = focusNode;

		if(word.compareTo(focusNode.word)<0){ //if the compareTo function returns negative value search is done in the left subtree
			isAtLeftChild = true;
			focusNode = focusNode.leftChild;
	}else{
		isAtLeftChild = false;//situation where the node with the given word is in the right subtree
		focusNode =focusNode.rightChild;
	}
	if(focusNode == null)//if node with such a word is not in the Tree
	return false;
}
if(focusNode.leftChild == null && focusNode.rightChild == null){//if the node does not have children
	if(focusNode ==root){//if the node with the word to be deleted is the root,set the root to null
		root =null;
	}else if(isAtLeftChild){//if the node is a left child set it to null
		parent.leftChild = null;
	} else {
		parent.rightChild = null;//if the node is a right child set it to null
	}
}
//if no right child
		else if(focusNode.rightChild ==null){
			if(focusNode == root)
			root =focusNode.leftChild;//the root is made the left  child
			else if(isAtLeftChild)
			parent.leftChild = focusNode.leftChild;
			else parent.rightChild = focusNode.leftChild;
		}
		//situation when there is no left child
		else if(focusNode.leftChild == null){
			if(focusNode == root)
			root = focusNode.rightChild;//the root is made the right  child
			else if(isAtLeftChild)
			parent.leftChild = focusNode.rightChild;
			else
			parent.rightChild = focusNode.leftChild;
		}
		//when 2 children are involved
		else{
			Node replacement = getReplacement(focusNode);//declaring the node to replace the other node
			if(focusNode == root)
			root = replacement;//replacing the root with the root's right child
			else if(isAtLeftChild)
			parent.leftChild = replacement;//if its a left child the parent's left child is replaced
			else
			parent.rightChild = replacement;//if its a right child the parent's right child is replaced
			replacement.leftChild=focusNode.leftChild;
		}
		return true;//because the return type of the method is boolean
	}
	//creating a method to replace the nodes
	public Node getReplacement(Node replacedNode){
		Node replacementParent = replacedNode;
		Node replacement = replacedNode;
		Node focusNode = replacedNode.rightChild;
		while(focusNode != null){//if the focus node is not null followng replacements are undergone
			replacementParent =replacement;
			replacement =focusNode;
			focusNode = focusNode.leftChild;//assigning the focus node it's left child.
		}
		if(replacement != replacedNode.rightChild){//if the replacement is not a right child moving
			replacementParent.leftChild =replacement.rightChild;//moving the replacement into the parent's left child slot
			replacement.rightChild = replacedNode.rightChild;//moving replaced's node to the replacement node's right child
		}
		return replacement;
	}

//opening the input text files which contains the words and meanings using the Scanner type variable x
public void openFile(){
	try{
		x = new Scanner(new File("Dictionary.txt"));
	}
	catch(Exception e){
		System.out.println("could not find file"); //Handling exceptions if the specific file does not exist
	}
}

//getting the words and meanings in the file
public  void readFile(){

	while(x.hasNextLine()){

		       String a = x.next(); //getting the 1st word of each line into a string variable a(the word)
			   String b= x.nextLine(); //getting the rest of  each line to another String varible b(the meaning)
			   y.addNode(a,b); //adding each pair of words and meanings as  nodes of the binary search tree y

		   }
	   }


public void closeFile(){
	x.close(); //closing the text file after all the words and meanings have been added to the binary search tree
}
}

//creating a class for nodes to implement node operarions.
class Node{
	String word;
	String meaning;

	Node leftChild;
	Node rightChild;

	Node(String word,String meaning){// creating the node constructor
		this.word=word;
		this.meaning=meaning;
	}

}

//creating the Dictionary class which mainly consists of GUI components inherited by the superclass BinaryTree.
class Dictionary extends BinaryTree  {
	//creating the private memebers of the GUI widgets
		private JTextField input;
		private JButton search,synonyms,add,delete;
		private JLabel meaning,insert,similar;
		private JTextArea synonym,mean;
		private JPanel panel;

		final static Dictionary a=new Dictionary(); //creating the dictionary object to perform dictionary operations



	//Declaring the constructor of the Dictionary class.
		Dictionary(){
			JFrame frame = new JFrame("Dictionary"); //creating the main window and providing it a title "Dictionary"
			JPanel panel = new JPanel(); //creating panel which has all GUI components

			//creating the GUI widgets such as Buttons,Labels,Textfileds and TextAreas.
			final JButton  search = new JButton("Search");
			final JButton synonyms = new JButton("Synonyms"); //The word inside " " is displayed in the widget
			final JButton add  = new JButton("Add");
			final JButton delete = new JButton("Delete");
			final JButton clear = new JButton("clear");
			final JTextField input = new JTextField(null);


			JLabel meaning = new JLabel("meaning");
			JLabel similar = new JLabel("Similar words");
			JLabel insert = new JLabel("Insert word");
			final JTextArea mean = new JTextArea(20,20);
			final JTextArea synonym = new JTextArea(20,20);

			//Adding GUI widgets to the Jpanel
			panel.add(search);
			panel.add(synonyms);
			panel.add(add);
			panel.add(delete);
			panel.add(clear);
			panel.add(mean);
			panel.add(meaning);
			panel.add(synonym);
			panel.add(input);
			panel.add(similar);
			panel.add(insert);

			panel.setBackground(Color.cyan); //Setting the background colour of the panel

			//Locating of GUI components in the Jpanel and giving them dimensions
			input.setBounds(30,40,300,25); //i.e(horizontal cordinate,Vertical cordinate,width,height).
			search.setBounds(30,100,75,25);
			synonyms.setBounds(30,150,95,25);
			add.setBounds(30,200,75,25);
			delete.setBounds(30,250,75,25);
			clear.setBounds(30,300,75,25);
			mean.setBounds(150,100,250,200);
			synonym.setBounds(450,100,250,200);
			meaning.setBounds(150,60,100,50);
			similar.setBounds(450,60,100,60);
			insert.setBounds(30,0,100,60);

			//Getting the displayed words to the next line if it exceeds the width of the textArea
			mean.setLineWrap(true);
			synonym.setLineWrap(true);

			panel.setLayout(null);
			frame.add(panel); // Adding the panel to the frame
			frame.setVisible(true);//Making the frame visible
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);//used in exiting the GUI when
			frame.setSize(750,400); //Giving the size for the frame in pixels(i.e (width,height)).

//Adding ActonListeners to each button.
		search.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {

				try{

					if(e.getSource().equals(search)){ //if the search button is clicked the following actions will take place (equals() method is used)

						String word = input.getText(); //getting the input from the textField into a string
						String meaning = a.findNode(word); //searching the String in the Dictionary(Using the Binary Search Tree)
			            mean.setText(meaning);} //Displaying the meaning of the word in the mean TextArea
					   }catch(NullPointerException m){//Handling the exception if the particular word is not in the dictionary
						   mean.setText("The word you are searching is unavailable");

					   }
        }});

        add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(e.getSource().equals(add)){//if the add button is clicked the following actions will take place
					String word = input.getText();//getting the input for the new word from textField into a string
					String means=mean.getText();//getting the input for the meaning from textArea into a string
					a.addNode(word,means); //Adding the word and meaning in to the Dictionary(using Binary Search Tree)
					mean.setText("The word has been successfully added to the dictionary");//Displaying a message in TextArea if the wod and meaning was successfully added to the Dictionary
					input.setText("");//Clearing the Text of TextField after the word and meaning is added to the dictionary

							   }

        }});

        delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try{
					if(e.getSource().equals(delete)){//if the delete button is clicked the following actions will occur
					String word = input.getText();//getting the input for the new word from textField into a string
					if(a.findNode(word)==null){//Handling a situation if the word is already not available in the Dictionary
						mean.setText("The word is not already in the dictionary");
						}
						else{

					    	a.delete(word); //If the word is available, deleting it and displaying the message of successful deletion
					        mean.setText("The word was deleted successfully");
					        synonym.setText(" "); // clearing any text in the synonyms textArea
					        input.setText(" ");
							}

					        input.setText("");}//clearing the text in the input text field
							   }catch(NullPointerException m){//Affter the deletion this message is displayed if the word has been deleted
								   mean.setText("The word is already unavailable");

							   }

        }});

        clear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if(e.getSource().equals(clear)){//if the clear button is clicked
				input.setText("");//clearing the text in the input textField
				mean.setText("");//clearing the text in the mean textArea
				synonym.setText("");//clearing the text in the synonym textArea
			}
        }});
        synonyms.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try{

					if(e.getSource().equals(synonyms)){//if the  synonyms button is clicked
					String word = input.getText();//getting the input from the textField into a string
					String similar=a.similarWords(word);//searching for similar words in the Dictionary
					if(a.similarWords(input.getText())==" "){
						synonym.setText("no similar words");//Error message if no similar words for a word available in the Dictionary
						}
						else{

							synonym.setText(similar);//If similar words exist dislpaying in the synonym textArea
							}}}catch(NullPointerException m){//Handling if the synonyms of a unavailable/wrong word is searched
								synonym.setText("Please enter a word in the Dictionary! ");

							}
        }});
}

public static  void main(String[] args){


	a.openFile(); //Opening the Dictionary.txt file using Dictionary object a
	a.readFile(); //Reading the Dictionary.txt file using Dictionary object a
	a.closeFile(); //closing the Dictionary.txt file using Dictionary object a

		}
}


