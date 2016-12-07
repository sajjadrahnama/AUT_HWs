#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define MAX_NUMBERS  1000000

int main(int argc,char ** args){
	if(argc < 2 ) {
		printf("%s\n", "wrong input!");
		return 0;
	}
	int * numbers = calloc(MAX_NUMBERS, sizeof(int));
	FILE* f = fopen(args[1],"r+");
	if(!f){
		printf("%s\n", "File Not found or can not be opened");
		return 0;
	}
	long long sum=0;
	long long squre_sum=0;
	double average = 0, variance = 0, standardـdeviation = 0;
	int size = 0;
	fscanf(f,"%d",&numbers[size]);
	sum += numbers[size];
	squre_sum += numbers[size]*numbers[size];
	size ++;
	while(!feof(f)){
		fscanf(f,"%d",&numbers[size]);
		sum += numbers[size];
		squre_sum += numbers[size]*numbers[size];
		size++;
	}
	average = (double) sum / size;
	variance = (double) (squre_sum / size) - average;
	standardـdeviation = sqrt(variance);

	printf("Average:\t\t%.2f\n",average);
	printf("Variance:\t\t%.2f\n",variance);
	printf("Standard Deviation:\t%.2f\n",standardـdeviation);
	return 0;

}
