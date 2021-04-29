//Abby Petersen
//CS 461
//Homework 3B

//package necessary for running on my IDE - change or remove line to run
package MissionariesAndCannibals;

import java.util.*; 


//Node class for binary tree construction
class Node {

	Node left;
	Node right;
	int data;
	
	Node(){
		right = null;
		left = null;
	
	}
}

//data structure: binary tree
//search algorith:  BFS Search on Binary Tree with a level order traversal
//MissionariesAndCannibals class - object is created in main
//contructor initializes all necessary variables and constructs binary tree
//traversal function calls all other functions to perfrom search on binary tree
public class MissionariesAndCannibals {

Node root;

	
private
		boolean solutionFound;
		boolean isEmpty;
		ArrayList<String> solution;
		Queue<Node> nodes;
		Queue<Integer> ql;
		//placeholder variables used to determine legal moves
		//m1 & c1 variables - used to represent number on missionaries and cannibals on the side that the current state is travelling to -
		int m1;
		int c1;
		int level;
		int c;
		int m;
		int b;
		int key;
		
		
	//initial state - c = 3, m = 3, b = 1
	//final state - c = 0, m = 0, b = 0
	//b = 1 inidicates boat is on the left side (starting side)
	//b = 0 indiciates boat is on the right side (ending side)
	//state characteristics are updated in tree traversal function
	public MissionariesAndCannibals() {
	  
		solutionFound = false;
		isEmpty = false;
		solution = new ArrayList<String>();
		c1 = 0;
		m1 = 0;
		c = 0;
		m = 0;
		b = 0;
		key = 0;
	}
  
  
  //tests all fesible actions from a particular node and finds all legal actions
  //if action is legal - adds action so solution (list of actions to reach solution)
  //update state characteristics and how many ms and cs are on each side
  //generate successor node for next state using updated characteristics
  public void expandNode(Node n, int key) {
	
	  //arraylists to store all possible moves
	  ArrayList<Integer> cMoves = new ArrayList<Integer>();
	  ArrayList<Integer> mMoves = new ArrayList<Integer>();
	  int possMovesListSize = 5;
	  
	  mMoves.add(2);
	  mMoves.add(1);
	  mMoves.add(1);
	  mMoves.add(0);
	  mMoves.add(0);
	  
	  cMoves.add(0);
	  cMoves.add(0);
	  cMoves.add(1);
	  cMoves.add(1);
	  cMoves.add(2);
	  
	  
	  //define c1 & m1 variables to determine the number of cannibals and missionaries on the other side of the river
	  c1 = 3 - c;
	  m1 = 3 - m;
	 
	  
	  //check for legal move - first check that action is feasible, then check if it's legal
	  //if legal, create node
	  //if more legal moves - create nodes
	  //exit when there are no more legal moves
		  for(int i = 0; i < possMovesListSize; i++) {
				 if((cMoves.get(i) <= c) && (mMoves.get(i)) <= m) {
					 
					 System.out.println("Action is feasible.");
					 
					 //checks to see if node has a successor
					 if(((cMoves.get(i) + c1) <= (mMoves.get(i) + m1)) && ((c - cMoves.get(i)) <= (m - mMoves.get(i)))) {
						 
						 System.out.println("Action is legal.");
						 
						 //for successor node - add previous action to solution - except for root
						 String actionPt1 = Integer.toString(cMoves.get(i));
						 String actionPt2 = Integer.toString(mMoves.get(i));
						 String actionPt3 = Integer.toString(b);
						 String action = (actionPt1 + actionPt2 + actionPt3);
						 solution.add(action);
						 
						 
						 c = c - (cMoves.get(i));
						 m = m - (mMoves.get(i)); 
						 b = 1 - b;
						
						 
						//find next open spot and generate and add node to tree and search list
						Node temp = new Node();
						temp.data = key;
						temp.left = null;
						temp.right = null;
							
						Node x = root;
						Node y = null;
						 
						 while (x != null) { 
						        y = x; 
						        if (key <= x.data) 
						            x = x.left; 
						        else
						            x = x.right; 
						 }
						 if (y == null) {
							 y = temp; 
						 }
						       
						 else if(key <= y.data) {
							 y.left = temp;
						 }
						        
						  else {
							  y.right = temp;
						  }
						
						//add new node to list, increase level, increase key value
						nodes.add(y);
						level++;
						ql.add(level);
						key++;      	 
				}
			 }
		  }
	 }

  
  	//removes front node from arrayList of nodes and returns it
  	public Node removeFrontNode() {
  		Node n = nodes.poll();
  		return n;
  	}
  
 
  	public void createQueue(int level, Node root) {
  		nodes = new LinkedList<Node>();
		ql = new LinkedList<Integer>();
		root = new Node();
  		nodes.add(root);
  		ql.add(level);
  	}
  	
  	//construct and Traverse tree using BFS search to search for a soliution
  	public void TreeTraversalSearch() {
  		
  		c = 3;
  		m = 3;
  		b = 1;
  		int key = 0;
  		level = 0;
  		createQueue(level, root);
  		Node node = null;
	
  		//while solution is not yet found, continue traversing through tree
  		//stops when solution is found or if there is no valid move at any step - indicating that there is no solution
  		while(solutionFound == false || isEmpty == false) {
  			
  			if(ql.size() == 0) {
  				isEmpty = true;
  			}
  			
  			//remove front node and expand if it isn't the solution node
  			node = removeFrontNode();
  			
  			//goal test
  			if((c == 0) && (m == 0) && (b == 0)) {
  				solutionFound = true;
  			}
  			
  			//if goal is not reached - expand node
  			expandNode(node,key);
			
  		}
	
  		if(solutionFound == true) {
  			displaySolution();
  		}
  		
  		else if(isEmpty == true) {
  			System.out.println("failure");
  		}
}

	  
	//strings that represent each valid move of solution are added to arraylist of strings once validated
 	//displayFunction function - uses strings to diaply each action of a solution in a readable form
	//this function only works if the input actions (that contain 2 entities crossing in the boat) -
	//are represented with cannibals (c) followed by missionaries (m) - in that order
	public void displaySolution() {
		int x;
		
		
		for(int i =0; i < solution.size(); i++) {
			x = 0;
			if(solution.get(i).charAt(x) == '2') {
				if (solution.get(i).charAt(x+1) == '0') {
					if(solution.get(i).charAt(x+2) == '0') {
						System.out.println("2 Cannibals moved from the right side to the left side.");
					}
					else if(solution.get(i).charAt(x+2) == '1') {
						System.out.println("2 Cannibals moved from the left side to the right side.");
					}
				}	
			}
			else if(solution.get(i).charAt(x) == '1'){
				if(solution.get(i).charAt(x+1) == '0'){
					if(solution.get(i).charAt(x+2) == '0') {
						System.out.println("1 Cannibal moved from the right side to the left side.");
					}
					else if(solution.get(i).charAt(x+2) == '1') {
						System.out.println("1 Cannibal moved from the left side to the right side.");
					}
				}
				else if(solution.get(i).charAt(x+1) == '1'){
					if(solution.get(i).charAt(x+2) == '0') {
						System.out.println("1 Cannibal and 1 Missionary moved from the right side to the left side.");
					}
					else if(solution.get(i).charAt(x+2) == '1') {
						System.out.println("1 Cannibal and 1 Missionary moved from the left side to the right side.");
					}
				}
			}
			else if(solution.get(i).charAt(x) == '0') {
				if(solution.get(i).charAt(x+1) == '1') {
					if(solution.get(i).charAt(x+2) == '0') {
						System.out.println("1 Missionary moved from the right side to the left side.");
					}
					else if(solution.get(i).charAt(x+2) == '1') {
						System.out.println("1 Missionary moved from the left side to the right side.");
					}
				}
				else if(solution.get(i).charAt(x+1) == '2') {
					if(solution.get(i).charAt(x+2) == '0') {
						System.out.println("2 Missionaries moved from the right side to the left side.");
					}
					else if(solution.get(i).charAt(x+2) == '1') {
						System.out.println("2 Missionaries moved from the left side to the right side.");
					}
				}
			}
		}
	}		    
				
	
	//main driver - create class object that initializes all necessary vairables
	//call TreeTraversalSearch function - that constructs binary search treeperforms search on tree by calling all other functions used in search
	//all steps of solution are printed when a solution is found
	//if a solution is not found - a message stating that there was no solution found is displayed
	public static void main(String[] args) {
		
		MissionariesAndCannibals StateSpaceDiagram = new MissionariesAndCannibals();
		StateSpaceDiagram.TreeTraversalSearch();
		
	}

}

