//
//  The doHist function creates a string, hist, which contains the number of occurences of each inputted letter.
//  Written by Alexandre Tessier, 260742984
//  doHist
//  Copyright © 2016 Alexandre Tessier. All rights reserved.
//

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
