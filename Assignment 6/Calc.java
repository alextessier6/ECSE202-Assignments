//
//  Assignment 6 ECSE-202 - Calc
//  Written by Alexandre Tessier, 260742984
//  This program, based around the graphical interface of a calculator, converts an inputed  mathematical expression expressed in infix notation to postfix notation and then evaluates it.
//  It can recognize the following operators: - + / x, as well as the unary minus
//

import java.awt.event.ActionEvent;
import javax.swing.*;
import acm.gui.TableLayout;
import acm.program.*;

public class Calc extends Program {
	
	JTextField outputbox = new JTextField(); //Initializes the output display (which displays the mathematical expression) of the calculator
	JTextField inputbox = new JTextField();  //Initializes the input display (which displays the number to be added to the expression and the answer) of the calculator
	
	String outputstring=""; //Initializes the string to be used with the text field outputbox
	String inputstring="";  //Initializes the string to be used with the text field inputbox
	
	Stack operators = new Stack();   //Initializes a stack to hold the operators
	Queue input = new Queue();		 //Initializes a queue to hold the input
	Queue output = new Queue();      //Initializes a stack to hold the output
	Stack calculation = new Stack(); //Initializes a stack used in the evaluation of the equation in postfix notation
	
	int queuecount=0; //The int queuecount is used to store the number of elements  (operands and operators) added to the input queue
	
	//Initializes the calculator
	public void init() {
		setLayout(new TableLayout(6,4)); //The desired layout of the calculator is a table with 6 rows and 4 columns
		addButtons(); 					 //Calls the addButtons method which adds the buttons in the proper order and the proper layout
		addActionListeners();			 //Adds listeners to the added buttons
		}
	
	//Adds the buttons to the calculator
	public void addButtons() {
		String constraint1 = "width=80" + " height=25"; //Sets a standard size for most of the buttons
		String constraint2 = "width=60" + " height=25"; //Sets a smaller size for the C, /, x, -, +, = buttons purely for esthetics reasons
		
		//The buttons are then added, either with the first or second size constraints
		add (outputbox, "gridwidth=3");      //The output text field has a width of 3 sections of the table (width of 3 buttons)
		add(new JButton("C"), constraint2);
		add(inputbox, "gridwidth=3");		 //The output text field has a width of 3 sections of the table (width of 3 buttons)
		add(new JButton("/"), constraint2);		
		add(new JButton("7"), constraint1);	
		add(new JButton("8"), constraint1);	
		add(new JButton("9"), constraint1);	
		add(new JButton("x"), constraint2);		
		add(new JButton("4"), constraint1);	
		add(new JButton("5"), constraint1);	
		add(new JButton("6"), constraint1);	
		add(new JButton("-"), constraint2);	
		add(new JButton("1"), constraint1);	
		add(new JButton("2"), constraint1);	
		add(new JButton("3"), constraint1);	
		add(new JButton("+"), constraint2);	
		add(new JButton("0"), constraint1);	
		add(new JButton("."), constraint1);	
		add(new JButton("+/-"), constraint1);	
		add(new JButton("="), constraint2);	
		}
		
	//Method which sets the actions to be performed when each button is pressed
	public void actionPerformed(ActionEvent e){
		String action = e.getActionCommand();
		
		//If the user clicks on the C button (clear), then the elements in the two text fields are deleted
		if (action.equals("C")){
		
			inputstring="";                   //Sets the input string to empty
			outputstring="";				  //Sets the output string to empty
			outputbox.setText(outputstring);  //Refreshes the output display of the calculator, which now displays nothing
			inputbox.setText(outputstring);	  //Refreshes the input display of the calculator, which now displays nothing
		
			for (;queuecount>0;queuecount--)  //If the clear button is pressed, the queue containing the inputed elements need to be cleared as well. queuecount is used since it stores the number of elements in the queue.
				input.dequeue();
		}
		
		//If the user clicks on +/-, the a - is added in front of the number to be inputed, or, if there is already a - present, then it is removed
		if (action.equals("+/-")){
			
			//If the output string ends with "=", meaning that the expression was calculated and that a number was given, then both input and output text fields are cleared when the button is pressed.
			//This is done because if the +/- button is pressed after an expression is calculated, it's because a new expression is about to be given by the user
			if(outputstring.endsWith("=")){ 
				inputstring="";						//Sets the input string to empty
				outputstring="";					//Sets the output string to empty
				outputbox.setText(outputstring);	//Refreshes the output display of the calculator, which now displays nothing
				inputbox.setText(outputstring);		//Refreshes the input display of the calculator, which now displays nothing
				for (;queuecount>0;queuecount--)    //The queue containing the inputed elements need tt be cleared in order to be able to calculate another expression
					input.dequeue();
			}

			if(inputstring.startsWith("-"))    //If a - is present in front of the inputed number, then it is removed
				inputstring = inputstring.substring(1); //The 1st character of the string (-) is removed
		
			else //If there is no - in front of the inputed number, then a - is added in front of the input string (the number)
				inputstring = "-" + inputstring;
		
			inputbox.setText(inputstring); //Refreshes the input display of the calculator for it to display the updated input string
		}
		
		//If the user clicks on =, then the mathematical expression is calculated and displayed in the input text field
		if (action.equals("=")){
			
			outputstring=outputstring.concat(inputstring); //Adds the last number inputed by the user to the mathematical expression
			outputstring=outputstring.concat(action);      //Adds the = to the end of the mathematical expression
			input.enqueue(inputstring);					   //Adds the last number inputed to the input queue used for the calculations
			queuecount=queuecount+1;					   //Increases the count of elements in the input queue by 1 as a number was enqueued
			calculate();								   //Calls the calculate method which calculates the answer to the mathematical expression
			inputstring=calculation.pop();				   //The answer, stored in the the calculation stack is put in the inputstring
			
			//The next block of lines is used to make sure that the answer is displayed with a precision of 6 decimal places 
			String[] answout=inputstring.split("\\.");     //The string is split in 2, using "." as a delimiter, the two parts are stored in an array of strings
			if(answout[1].length()>6)					   //If the length of the second part of the string (decimal part of the answer) is less than 6, nothing needs to be done			
				answout[1]=answout[1].substring(0,6);	   //If the length of the decimal part of the answer is more than 6, then only the first 6 elements are taken
			inputstring=answout[0]+"."+answout[1];         //Reconstructs the answer 
			
			outputbox.setText(outputstring);			   //Refreshes the output display of the calculator, which now displays the complete expression
			inputbox.setText(inputstring);				   //Refreshes the input display of the calculator, which now displays the answer
		}
		
		//If the user clicks on an operator, then the input display is cleared and the operand/operator are added to the math expression displayed in the output text field
		if (action.equals("+")||action.equals("-")||action.equals("/")||action.equals("x")){
			outputstring=outputstring.concat(inputstring); //Adds the number in the input text field to the mathematical expression
			outputstring=outputstring.concat(action);      //Adds the operator to the end of the mathematical expression
			input.enqueue(inputstring);					   //Adds the number inputed to the input queue used for the calculations
			input.enqueue(action);						   //Adds the operator to the input queue
			queuecount=queuecount+2;					   //Increases the count of elements in the input queue by 2 as a number and an operator were enqueued
			inputstring="";								   //Sets the input string to empty
			outputbox.setText(outputstring);  			   //Refreshes the output display of the calculator
			inputbox.setText(inputstring);				   //Refreshes the input display of the calculator
		}
		
		//If the user clicks on a number or on ".", then the number appears in the input text field
		if (action.equals("0")||action.equals("1")||action.equals("2")||action.equals("3")||action.equals("4")||action.equals("5")||action.equals("6")||action.equals("7")||action.equals("8")||action.equals("9")||action.equals(".")) {
	  
			//Same idea as with the clear button. This loop empties the input queue/text fields when a new expression is about to be inputed
			if(outputstring.endsWith("=")){
				inputstring="";
				outputstring="";
				outputbox.setText(outputstring);
				inputbox.setText(outputstring);
				for (;queuecount>0;queuecount--)
					input.dequeue();
			}
					
			inputstring = inputstring.concat(action);	//Adds the number (or ".") to the input string
			inputbox.setText(inputstring);				//Refreshes the input display, which now shows the inputed number (or ".")
			}
	}
	
	public void calculate() {
		
		int argc=queuecount; //Stores the number of elements in the input queue
		
		operators.push("0");  //Initialize the stack with a value of 0
		
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
	}	
}

		



