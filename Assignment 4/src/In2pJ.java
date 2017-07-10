//
//  Assignment 4 ECSE-202 - In2pJ
//  Written by Alexandre Tessier, 260742984
//  This program converts an inputed  mathematical expression expressed in infix notation to postfix notation.
//  It can recognize the following operators: - + / x
//

public class In2pJ {

	public static void main(String[] args) {
			
			int argc=args.length; //Stores the length of the inputed string
			
			Stack operators = new Stack();  //Initializes a stack to hold the operators
			Queue input = new Queue();		//Initializes a queue to hold the input
			Queue output = new Queue();     //Initializes a stack to hold the output
			
			operators.push("0");  //Initialize the stack with a value of 0
			
			//This loop adds the inputed arguments to the input queue.
			for(int i=0;i<argc;i++){
				input.enqueue(args[i]);
			}
			
			String token;    //Used to store a value from the input queue
			int oppcount=0;	 //Used to count the number of operators in the operators stack
			
			//This loop goes through the input queue and adds the inputed elements to the output queue in the correct order associated with the postfix notation.
			for (int i=0;i<argc;i++){
				
				switch(token=input.dequeue()){
		        
		        case "x":
		        	
		            //If there is a + or a - at the top of the operator stack, add the x operator to the stack as x has a higher precedence than - or +
		            if(operators.top()=="+" || operators.top()=="-" || operators.top()=="0"){
		                    operators.push("x");
		                    oppcount+=1;
		            }
		            //If there is a x or a / at the top of the operator stack, add the operator at the top of the stack to the output queue and add the x operator to the stack. This is done because the x operator's precedence is equal to the top operator's precedence.
		                else {
		                    output.enqueue(operators.pop());
		                    operators.push("x");
		                }
		            break;
		            
		        case "/": //The logic behind the if/else if/else is similar to the one applied in the previous case (x)
		            
		        	if(operators.top()=="+" || operators.top()=="-"|| operators.top()=="0"){
		                    operators.push("/");
		                    oppcount+=1;
		            }
		                else {
		                    output.enqueue(operators.pop());
		                    operators.push("/");
		                }
		            break;
		            
		        case "+":
		        	
		            //If there is nothing in the operators stack, the value for the + operator is added to the stack
		            if(operators.top()=="0"){
		                operators.push("+");
		                oppcount+=1;
		            }
		            //If there is something in the operators stack, then all the operators contained in the stack are added to the output queue and then the + operator is added to the operators stack. This is done because the + operator's precedence is less or equal to the top operator's precedence.
		            else {
		                for (; oppcount>0;oppcount--)
		                    output.enqueue(operators.pop());
		                operators.push("+");
		                oppcount+=1;
		            }
		            break;
	
		        case "-": //The logic behind the if/else if/else is similar to the one applied in the previous case (+)
		          
		            if(operators.top()=="0"){
		                operators.push("-");
		                oppcount+=1;
		            }
		            else {
		                for (; oppcount>0;oppcount--)
		                    output.enqueue(operators.pop());
		                operators.push("-");
		                oppcount+=1;
		            }
		            break;
		            
		        default: //If the token contains a number, it is added to the output queue
		            output.enqueue(token);
		            break;
				}
			}
	
			//Adds to the output queue the operators remaining in the operators stack
			for (; oppcount>0;oppcount--)
				output.enqueue(operators.pop());		
			
			//This loop reads the elements in the output queue, 1 by 1, and prints them.
			System.out.print("Postfix: ");    
			for (int i=1;i<=argc;i++)
				System.out.print(output.dequeue() + " ");
			System.out.println();
	}
}