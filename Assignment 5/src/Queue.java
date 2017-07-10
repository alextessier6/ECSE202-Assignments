//
//  Assignment 5 ECSE-202 - Queue Class
//  Written by Alexandre Tessier, 260742984
//  This class contains the enqueue and dequeue functions used to implement queues as linked lists.
//

public class Queue {

	//Integer containing the total number of elements in the queue, used for the dequeue function.
	int total=0;
	
	//As we need to track both ends of the queue, a rear node and a first node are initialized
	Node rear = new Node(); 
	Node first = new Node();

	//Enqueue function used to add element to a queue.
	public void enqueue(String data){
		Node lastrear = rear;  //The element in the rear is moved to "lastrear", ie the before-last element in the queue
		rear = new Node();     //A new node for the rear is created
		rear.data=data;		   //Stores the data passed to the enqueue function in the "rear" node (last element) of the queue
		rear.next=null;		   //Since rear is the last element, it cannot point to another element, rear.next is thus set to null
		if(total==0)		   //If there is nothing in the queue, the element in rear is promoted to the front of the queue (first)
			first=rear;
		else				   //If there are elements in the queue, the before-last element (lastrear) is changed so it points to the last element (rear)
			lastrear.next=rear;
		total++; 			   //Increases the total number of elements in the queue by 1
	}

	//Dequeue function used to remove element from a queue.
	public String dequeue(){
		if(total==0) 	////If there is nothing in the queue, it returns 0 as there is nothing to dequeue
			return "0";
		else{
			String elem = first.data; //If there is something in the queue, the element at the front of the queue is copied to "elem"
			first=first.next;         //The element at the front of the queue is changed for the element which was previously in second place (the previous "next" element)
			total--;				  //Decreases the total number of elements in the queue by 1
			return elem;
		}	
	}
	
	//Top function to return the value of the element at the front of the queue.
	public String top(){
		String element=first.data; //Copies the element at the front of the queue to "element"
		return element;            //Returns the element at the front of the queue
	}
			
}