//
//  main.c
//  quicksort
//
//  Created by Alexandre Tessier on 2016-10-12.
//  Copyright © 2016 Alexandre Tessier. All rights reserved.
//

#include <stdio.h>

void swap(int *p1, int *p2){
    
    int temp;
    
    temp=*p1;
    *p1=*p2;
    *p2=temp;
}

//boundaries = from left (0) to right (length-1)

void quicksort(int data[], int left, int right){
   
    int index=left;
    int i;
    int pivot=right;
    
    for(i=left;i<right;i++)
    {
        if(data[i]<=pivot)
        {
            swap(&data[i],&data[index]);
            index++;
        }
    }
    
    swap(&data[index],&data[right]);
    
        while (left<right)
        {
            quicksort(data, pivot+1, left);
            quicksort(data, right, pivot-1);
        }
    }
    
    
    swap(&data[pivot],&data[left]);
    
    
    
    /*int left=[i-1];
     int right=[i+1];
     
     for (i=0;i<=numrecords;i++)
     {
     if (records[pivot]<records[i])
     swap(****);
     
     if (records[pivot]>records[i])
     swap(****);
     }*/
    
}


int main(int argc, const char * argv[]) {
    // insert code here...
    printf("Hello, World!\n");
    return 0;
}
