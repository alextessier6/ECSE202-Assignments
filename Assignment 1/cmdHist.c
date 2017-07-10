//
//  This program analyzes the occurence of characters inputted and displays the results as a histogram
//  Written by Alexandre Tessier, 260742984
//  cmdHist
//  Copyright © 2016 Alexandre Tessier. All rights reserved.
//

#include <stdio.h>

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

//The doHist function creates a string, hist, which contains the number of occurences of each inputted letter. For each letter, the number of occurences is stored in the car hist[x], where "x" corresponds to the ASCII value of the letter. For instance, if the letter a is inputted 4 times, hist[97] would be equal to 4.

int doHist(char buffer[], char hist[]){
    int ascii[100]; //Creates a string of integer to hold the ASCII value of each of the inputted characters (up to 100 characters)
    int y=0;
    while (y<256){ //Reset the counters for hist[0] up to hist[256] (256 different ASCII characters)
        hist[y]=0;
        y++;
    }
    int c=0;
    while(buffer[c]!='\0'){ //Converts the characters contained in the buffer into the appropriate ASCII value, stored in ascii[]
        ascii[c]=buffer[c]-0;
        c++;
    }

    int g=0;
    while(g<c){
        hist[ascii[g]]++; //Counts the occurence of each letter and stores the value into the appropriate hist[], based on the value of the ASCII character
        g++;
    }
    return 0;
    }

//The displayHist function creates a bar graph/histogram to visually represent the number of occurence of the unique characters found. The length of each bar is proportional to the number of occurence.

void displayHist(char hist[], int distinct_chars){
    int max=0;
    int t=0;
    distinct_chars=0;
    while(t<256){ //This loops goes through all the values stored in hist[] and copies the value associated with hist[t] to max if hist[t] is larger than max. The largest value of hist[] is thus associated with max.
            if(max<hist[t])
                max=hist[t];
            else
                t++;
            }
    int q=0;
    while(q<256){
        if(hist[q]!=0){ //This loop counts the amount of unique characters stored in hist[].
            distinct_chars++;
            q++;
        }
        else{
            q++;
        }
    }
    
    int MAXSCALE=25; //Defines the max length of a bar as 25
    
    int z=0;
    printf("%d distinct characters found\n", distinct_chars); //Prints the amount of distinct characters found
    while(z<256){
        if(hist[z]!=0){
            int barlength=(int)(((double)hist[z])/((double)max)*((double)MAXSCALE)); //Calculates the length of each bar using floating point numbers instead of integers to allow for fractional quantities
            printf("%c [%d] %.*s\n",z, hist[z], barlength,"*******************************"); //Prints the character associated to the ASCII value "z" followed by the number of occurence in brackets and a series of asterisks (maximum 25) proportional to the number of occurence.
            z++;
        }
        else{
            z++;
        }}
}

//main (cmdHist) analyzes the occurence of characters inputted and displays the results as a histogram

int main(int argc, char *argv[]){
    
    char buffer[100]; //Maximum length of the inputted text string is 100
    toString(argc,argv,buffer);
    
    char hist[256]; //There are 256 different ASCII characters
    doHist(buffer, hist);
    
    
    int distinct_chars; //Used to store the number of unique characters
    displayHist(hist, distinct_chars);
    
    return 0;
}

