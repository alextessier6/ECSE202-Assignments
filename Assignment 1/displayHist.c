//
//  The displayHist function creates a histogram representing the number of occurence of unique characters
//  Written by Alexandre Tessier, 260742984
//  displayHist
//  Copyright © 2016 Alexandre Tessier. All rights reserved.
//

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
