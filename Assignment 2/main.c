//
//  Assignment 2 ECSE-202
//  Written by Alexandre Tessier, 260742984
//  This program assumes that a names and IDs data file (location and name) is passed as the first argument,
//  that a marks data files is passed as the second argument, and that an ID number is passed as the third argument.
//  It then lists all the student records, followed by the sorted records (sorted by ID number).
//  Finally, it prints the record (ID, Last Name, First Name, Mark) associated with the inputted ID number.
//

#define NUMRECORDS 50
#define MAXNAMELENGTH 15
#include <stdio.h>
#include <stdlib.h>

//Define structure to hold student data
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};

//Swaps the content of the addresses pointed to by the pointers p1 and p2
void swap(struct StudentRecord *p1, struct StudentRecord *p2){
    struct StudentRecord temp;
    temp=*p1;
    *p1=*p2;
    *p2=temp;
}

//Sorts the data stored in a StudentRecord structure according to the ID numbers associated with every student, using a pivot and an index. The index is used to move all the values greater than the pivot to the right of the pivot, and the values smaller, to the left of it. The process is repeated until the list is sorted.
void quicksort(struct StudentRecord data[], int left, int right){
    
    int index=left;
    int i=left;
    int pivot=data[right].IDNums;
    
    if (left<right){
        
        for(i=left;i<right;i++){
            if(data[i].IDNums<=pivot){
                swap(&data[i],&data[index]);
                index++;}
        }
        swap(&data[index],&data[right]);
        int indx=index;
        quicksort(data, left, indx-1);
        quicksort(data, indx+1, right);
    }
}

//Searches an inputted ID number in a StudentRecord structure. Since the inputted structure is sorted, it compares the value of the inputted ID number to the value of the ID stored in the center (or approximately) of the array and if the value of the ID searched is greater than the value stored, it repeats the same process, this time comparing the ID searched to the ID stored in the middle of the next half of the array. If the value is smaller, it repeats the same process, this time comparing the searched ID to the previous half, etc.
//It returns the position of the ID number in the array or -1 if the ID number wasn't found
int binarysearch(int length, struct StudentRecord search[], int IDSearched){
    
    int t=0;
    while(t<length)
        {
            int i=(t+length-1)/2;
    
        if(search[i].IDNums==IDSearched){
            return i;}
        
        else if(search[i].IDNums<IDSearched)
           t=i+1;
        
        else if(search[i].IDNums>IDSearched)
            length=i;
    }
    return -1;
}

int main(int argc, char * argv[]) {

	struct StudentRecord SRecords[NUMRECORDS];
	int recordnum;

	//Read in Names and ID data
	FILE * NamesIDsDataFile;
	if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
		printf("Can't read from file %s\n", argv[1]);
		exit(1);
	}
	for (recordnum=0;recordnum<NUMRECORDS;recordnum++){
		fscanf (NamesIDsDataFile, "%s%s%d", &(SRecords[recordnum].FirstNames[0]),&(SRecords[recordnum].LastNames[0]),&(SRecords[recordnum].IDNums));
	}
	fclose(NamesIDsDataFile);

	//Read in marks data
	FILE * MarksDataFile;
	if((MarksDataFile = fopen(argv[2], "r")) == NULL){
		printf("Can't read from file %s\n", argv[2]);
		exit(1);
	}
	for (recordnum=0;recordnum<NUMRECORDS;recordnum++){
		fscanf (MarksDataFile, "%d", &(SRecords[recordnum].Marks));
	}
	fclose(MarksDataFile);

	//Print out data as read in
	for(recordnum=0;recordnum<NUMRECORDS;recordnum++){
		printf("%s %s %d %d\n",SRecords[recordnum].FirstNames,SRecords[recordnum].LastNames,SRecords[recordnum].IDNums, SRecords[recordnum].Marks);
	}
    
    //Stores the searched ID number as an int IDSearched
    int IDSearched=atoi(argv[3]);
    
    //Sorts the data
    quicksort(SRecords, 0, NUMRECORDS-1);

    //Prints the sorted data
    printf("\n\nRecords sorted by ID\n");
    for(recordnum=0;recordnum<NUMRECORDS;recordnum++){
      printf("%s %s %d %d\n",SRecords[recordnum].FirstNames,SRecords[recordnum].LastNames,SRecords[recordnum].IDNums, SRecords[recordnum].Marks);
    }
   
    //Finds the position of the searched student ID number in the array
    int position=binarysearch(NUMRECORDS,SRecords,IDSearched);
   
    //Prints the data associated with the searched student ID number
    if (position!=-1){
    printf("\n\nID: %d Student: %s,%s Mark: %d\n",SRecords[position].IDNums,SRecords[position].LastNames,SRecords[position].FirstNames,SRecords[position].Marks);
    }
    
    //Prints an error message if the inputted ID number does not exist
    else{
        printf("\n\nID: %d does not exist\n", IDSearched);
    }
    
	return EXIT_SUCCESS;
    
    
}
















