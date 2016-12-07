#include <stdio.h>
#include <stdlib.h>
#include <time.h>	

#define RAND_NUM_1 30
#define RAND_NUM_2 300
#define RAND_NUM_3 3000
#define RAND_NUM_4 30000

double rand_double(){
	return (double) rand()/RAND_MAX;
}
int main(int argc , char ** args){
	if(argc < 2){
		printf("wrong input\n");
		return 0;
	}
	double input = atof(args[1]);
	if(input > 1){
		printf("enter number p between 0 and 1\n");
		return 0;
	}
	srand(time(NULL));
	int observe = 0;
	for (int i = 0; i < RAND_NUM_1; ++i){
		if(rand_double() < input)	observe++;
	}
	printf("for %d number\t\t : %.2lf\n", RAND_NUM_1,(double)observe/RAND_NUM_1);
	observe=0;

	for (int i = 0; i < RAND_NUM_2; ++i){
		if(rand_double() < input)	observe++;	
	}
	printf("for %d number\t\t : %.2lf\n", RAND_NUM_2,(double)observe/RAND_NUM_2);
	observe=0;
	
	for (int i = 0; i < RAND_NUM_3; ++i){
		if(rand_double() < input)	observe++;
	}
	printf("for %d number\t\t : %.2lf\n", RAND_NUM_3,(double)observe/RAND_NUM_3);
	observe=0;	
	
	for (int i = 0; i < RAND_NUM_4; ++i){
		if(rand_double() < input)	observe++;
	}
	
	printf("for %d number\t : %.2lf\n", RAND_NUM_4,(double)observe/RAND_NUM_4);

}