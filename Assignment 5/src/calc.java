//
//  Assignment 5 ECSE-202 - calc
//  Written by Alexandre Tessier, 260742984
//  This program converts an inputed  mathematical expression expressed in infix notation to postfix notation and then evaluates it.
//  It can recognize the following operators: - + / x
//

public class calc {

	public static void main(String[] args) {
			
			int argc=args.length; //Stores the length of the inputed string
			System.out.println(argc);
			
			Stack operators = new Stack();   //Initializes a stack to hold the operators
			Queue input = new Queue();		 //Initializes a queue to hold the input
			Queue output = new Queue();      //Initializes a stack to hold the output
			Stack calculation = new Stack(); //Initializes a stack used in the evaluation of the equation in postfix notation
			
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
			
			//This loop scans the entries in the output queue and, at the same time, evaluates the mathematical expression by using the postfix notation
			for(int i=1;i<=argc;i++){ 
				if(output.top()=="x"||output.top()=="/"||output.top()=="+"||output.top()=="-"){         //If the entry scanned is an operator, then the loop calls the evaluate function
					String answ= Eval.evaluate(calculation.pop(), calculation.pop(),output.dequeue());  //The evaluate function evaluates part of the mathematical expression by taking the scanned operator and two numbers stored as strings in the calculation stack
					calculation.push(answ);																//The value returned by the evaluate function is then pushed into the calculation stack to be used in the next operations.
				}
				else{		//Is the scanned entry is not an operator (it is thus a number/operand), then it is added to the calculation stack to be used later in calculations.
					calculation.push(output.dequeue());
				}
			}
			
			//This loops prints the original mathematical expression in infix notation
			for(int i=0;i<argc;i++){
				System.out.print(args[i] + " ");
			}
			
			//The answer to the expression (the last value stored in the calculation stack) is then printed
			System.out.println(" = " + calculation.pop());
	}
}