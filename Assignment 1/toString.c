//
//  The toString function concatenates inputted argvs into a single string containaing individual characters.
//  Written by Alexandre Tessier, 260742984
//  toString
//  Copyright © 2016 Alexandre Tessier. All rights reserved.
//

//The append function concatenates the inputted elements (the "in" string) to a "out" string.

void append (char out[], char in[]){
    int i=0;
    while (out[i] != '\0')
        i++;
    int j=0;
    while (in[j] != '\0'){ //Concatenates the elements until the end of the "in" string, identified by the null character
        out[i]=in[j];
        i++;
        j++;
    }
    out[i]=in[j]; //Copies the null from the "in" string to the "out" string, thus identifying the end of the "out" string
}

//The toString function, with the help of the append function, concatenates the inputted argvs into a single string containaing the individual characters. It creates a buffer containing the characters of the inputted arguments, without any white spaces.

void toString(int argc, char *argv[], char buffer[]){
    buffer[0]='\0';
    int i=1; //The name of the program, included in argv[0], is to be ommited
    while(i<argc){ //Concatenates the argvs to the buffer as long as i is smaller than argc, the number of arguments inputted
        append (buffer,argv[i]);
        i++;
    }}




