//
//  Assignment 3 ECSE-202
//  Written by Alexandre Tessier, 260742984
//  This program converts an inputted  mathematical expression expressed in infix notation to postfix notation.
//  It can recognize the following operators: - + / x
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MULT -1 //Defines the value used to represent a multiplication sign (x) as -1
#define DIV -2  //Defines the value used to represent a division sign (/) as -2
#define ADD -3  //Defines the value used to represent a plus sign (+) as -3
#define SUB -4  //Defines the value used to represent a minus sign (-) as -4
// These declarations can be done since no negative number will be processed by the infix to postfix algorithm

#define INIVAL -6 //Defines the initial value used for the stack

// The following struct s_listnode and functions push, pop, enqueue and dequeue adapted from:
// Daniel Weller, and Sharat Chikkerur. 6.087 Practical Programming in C. January IAP 2010. Massachusetts Institute of Technology: MIT OpenCourseWare, https://ocw.mit.edu. License: Creative Commons BY-NC-SA.

//Structure representing a node in the implemented stack and queues.
struct s_listnode
{
    int element;
    struct s_listnode * pnext;
};

//Push function used to add element to a stack.
void push(struct s_listnode **stack_buffer, int elem)
{
    struct s_listnode *new_node =(struct s_listnode *)malloc(sizeof(struct s_listnode));
    new_node->pnext=*stack_buffer;
    new_node->element=elem;
    *stack_buffer=new_node;
}

//Pop function used to remove element from a stack.
int pop(struct s_listnode **stack_buffer)
{
    if (*stack_buffer){
        struct s_listnode *pelem =*stack_buffer;
        int elem=(*stack_buffer)->element;
        *stack_buffer=pelem->pnext;
        free(pelem);
        return elem;
        }
    else
        return 0;
}

//Enqueue function used to add element to a stack. Note the arguments for the tracking of the rear (prear) and the front (queue_buffer) of the queue, which are required in order to have multiple queues.
void enqueue(struct s_listnode **prear, struct s_listnode **queue_buffer, int elem)
{
    struct s_listnode *new_node=(struct s_listnode*)malloc(sizeof(struct s_listnode));
    new_node->element=elem;
    new_node->pnext=NULL;
    if(*prear)
        (*prear)->pnext=new_node;
    else
        *queue_buffer=new_node;
    *prear=new_node;
}

//Dequeue function used to remove element from a stack. Note the arguments for the tracking of the rear (prear) and the front (queue_buffer) of the queue, which are required in order to have multiple queues.
int dequeue(struct s_listnode **prear, struct s_listnode **queue_buffer)
{
    if ((*queue_buffer)){
        struct s_listnode *pelem=*queue_buffer;
        int elem=(*queue_buffer)->element;
        *queue_buffer=pelem->pnext;
        if (pelem==*prear)
            prear=NULL;
        free(pelem);
        return elem;
    }
        else
        return 0;
}

int main(int argc, char * argv[]){
    
struct s_listnode *operators=NULL;   //Will be used for the stack, which will contain the operators
struct s_listnode *input=NULL;       //Will be used for the input queue
struct s_listnode *rear_input=NULL;  //Will be used to track the rear of the input queue
struct s_listnode *output=NULL;      //Will be used for the output queue
struct s_listnode *rear_output=NULL; //Will be used to track the rear of the output queue
    
push(&operators,INIVAL); //Adds an initial value to the stack in order to solve a problem related to using operators->element when the stack is empty
    
//This loop reads the inputted argvs, 1 by 1. If a number is read, the number is added to the input queue. If an operator is read, then the corresponding integer value for that operator is added to the input queue. For instance if argv[2]==+, then ADD (value of -3) will be added to the input queue.
for (int i=1;i<argc;i++){
    if(strcmp(argv[i],"x")==0)
        enqueue(&rear_input,&input,MULT);
    else if(strcmp(argv[i],"/")==0)
        enqueue(&rear_input,&input,DIV);
    else if(strcmp(argv[i],"+")==0)
        enqueue(&rear_input,&input,ADD);
    else if(strcmp(argv[i],"-")==0)
        enqueue(&rear_input,&input,SUB);
    else
        enqueue(&rear_input,&input,atoi(argv[i]));
    }

int token;       //Used to store a value from the input queue
int oppcount=0;  //Used to count the number of operators in the operators stack
    
//This loop goes through the input queue and adds the inputted elements to the output queue in the correct order associated with the postfix notation
for (int i=1;i<argc;i++){

    switch(token=dequeue(&rear_input,&input)){
           
        case MULT:
            //If there is nothing in the operators stack (except the inival which is never used), the value for the x operator is added to the stack
            if (operators->element==INIVAL){
                    push(&operators, MULT);
                    oppcount+=1;
            }
            //If there is a + or a - at the top of the operator stack, add the x operator to the stack as x has a higher precedence than - or +
            else if(operators->element==ADD || operators->element==SUB){
                    push(&operators, MULT);
                    oppcount+=1;
            }
            //If there is a x or a / at the top of the operator stack, add the operator at the top of the stack to the output queue and add the x operator to the stack. This is done because the x operator's precedence is equal to the top operator's precedence.
                else {
                    enqueue(&rear_output,&output,pop(&operators));
                    push(&operators, MULT);
                }
            break;
            
        case DIV: //The logic behind the if/else if/else is similar to the one applied in the previous case (mult)
            
            if (operators->element==INIVAL){
                    push(&operators, DIV);
                    oppcount+=1;
            }
            else if(operators->element==ADD || operators->element==SUB){
                    push(&operators, DIV);
                    oppcount+=1;
            }
                else {
                    enqueue(&rear_output,&output,pop(&operators));
                    push(&operators, DIV);
                }
            break;
            
        case ADD:
            //If there is nothing in the operators stack (except the inival which is never used), the value for the + operator is added to the stack
            if(operators->element==INIVAL){
                push(&operators,ADD);
                oppcount+=1;
            }
            //If there is something in the operators stack, then all the operators contained in the stack are added to the output queue and then the + operator is added to the operators stack. This is done because the + operator's precedence is less or equal to the top operator's precedence.
            else {
                for (; oppcount>0;oppcount--)
                    enqueue(&rear_output,&output,pop(&operators));
                push(&operators,ADD);
                oppcount+=1;
            }
            break;

        case SUB: //The logic behind the if/else if/else is similar to the one applied in the previous case (add)
          
            if(operators->element==INIVAL){
                push(&operators,SUB);
                oppcount+=1;
            }
            else {
                for (; oppcount>0;oppcount--)
                    enqueue(&rear_output,&output,pop(&operators));
                push(&operators,SUB);
                oppcount+=1;
            }
            break;
            
        default: //If the token contains a number, it is added to the output queue
            enqueue(&rear_output,&output,token);
            break;
    }
}
    
//Adds to the output queue the operators remaining in the operators stack
for (; oppcount>0;oppcount--)
    enqueue(&rear_output,&output,pop(&operators));

printf("postfix: ");

//This loop reads the elements in the output queue, 1 by 1. If a number is read, then the number is printed. If the signed integer value coresponding for an operator is read, then the associated operator is printed. (ex: If buffer=-3 (value for ADD), then + is printed)
for (int i=1;i<argc;i++){
    
    int buffer=dequeue(&rear_output,&output);
    
    if(buffer==MULT)
        printf("x ");
    else if(buffer==DIV)
        printf("/ ");
    else if(buffer==ADD)
        printf("+ ");
    else if(buffer==SUB)
        printf("- ");
        else
            printf("%d ",buffer);
}
    
printf("\n");
    
return 0;
}
