//
//  Assignment 4 ECSE-202 - Stack Class
//  Written by Alexandre Tessier, 260742984
//  This class contains the push, pop and top functions used to implement stacks as linked lists.
//

public class Stack {
	
	//Integer containing the total number of elements in the stack, used for the pop function.
	private int total=0;
	
	//Initializes the first node of the stack
	Node first = new Node();
	
	//Push function used to add element to a stack.
	public void push(String data){	
		Node previous = first; //The elements of the first node are moved to the "previous" node, ie in second place
		first = new Node();	   //Initializes a new first node
		first.data = data;     //Stores the data passed to the push function in the first node of the stack
		first.next = previous; //The first node now points to the second node
		total++;			   //The total is increased by one as one element was added to the stack
	}

	//Pop function used to remove element from a stack.
	public String pop(){
		if(total==0)     //If there is nothing in the stack, it returns 0 as there is nothing to pop
			return "0";
		else{
			String elem=first.data; //If there is something in the stack, the element at the top of the stack is copied to "elem"
			first=first.next; 		//The element at the top of the stack is changed for the element which was previously in second place (the previous "next" element)
			total--;				//Decreases the total number of elements in the stack by 1
			return elem; 			//Returns the element at the stop of the stack
		}
	}
	
	//Top function to return the value of the element at the top of the stack.
	public String top(){
		String element=first.data; //Copies the element at the top of the stack to "element"
		return element;		       //Returns the element at the top of the stack
	}
	
}


	
	

