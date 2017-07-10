//
//  Assignment 5 ECSE-202 - Eval Class
//  Written by Alexandre Tessier, 260742984
//  This class contains the function used to evaluate mathematical expressions.
//

public class Eval {

	// The function is defined as static as it does not rely on an instance to be called. It takes strings as arguments (since the stack used was defined with strings).
	// As with any mathematical operation, this function requires 2 operands (a and b) and 1 operator.
	public static String evaluate(String a, String b, String operator ){
	
	float arg1 = Float.parseFloat(a); //The string a is converted to a float for the calculations to work
	float arg2 = Float.parseFloat(b); //The string b is converted to a float for the calculations to work
	
	float answ=0;        // A float answ will store the resulting value of the mathematical operation
	if(operator=="x")    // IF the given operator is x (note the "x" as it is a string), then multiply arg1 and arg2
		answ=arg2*arg1;
		
	if(operator=="/")    // If the given operator is /, then divide arg2 by arg1. Note that the order of the inputed a and b matters.
		answ=arg2/arg1;
		
	if(operator=="+")    // If the given operator is +, add arg2 and arg 1.
		answ=arg2+arg1;
		
	if(operator=="-")    // If the given operator is -, subtract arg1 from arg2. Note that the order of a and b matters.
		answ=arg2-arg1;
	
	return String.valueOf(answ); //The float answ is converted back to a string for it to be pushed later in a stack that only accepts strings.
	}
}
