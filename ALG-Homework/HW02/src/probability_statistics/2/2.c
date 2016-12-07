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
int find_index(double * array,double a, int size){
	for (int i = 0; i < size; ++i){
		if(a <= array[i]) return i;
	}
	return -1;
}
void print_result(double * prob, int * observe, int size, int number){
	printf("for %d number\t\t :\n", number);
	printf("\tfor %.2f probablity :\t%.2lf\n",prob[0],(double)observe[0]/number );
	for (int i = 1; i < size; ++i){
		printf("\tfor %.2f probablity :\t%.2lf\n",prob[i]-prob[i-1],(double)observe[i]/number );
	}
}
int main(int argc , char ** args){
	if(argc < 2 ) {
		printf("%s\n", "wrong input!");
		return 0;
	}
	FILE* f = fopen(args[1],"r+");
	if(!f){
		printf("%s\n", "File Not found or can not be opened");
		return 0;
	}
	int size = 0;
	fscanf(f,"%d",&size);
	double *prob = malloc(sizeof(double)*size);
	for (int i = 0; i < size; ++i){
		fscanf(f, "%lf",&prob[i]);
		if( i > 0 ) prob[i] += prob[i - 1]; 
	}
	srand(time(NULL));
	int * observe = calloc(size, sizeof(int));
	if(prob[size-1] != 1){
		printf("wrong input probablity\n");
		return 0;
	}
	for (int i = 0; i < RAND_NUM_1; ++i)
		observe[find_index(prob, rand_double(),size)]++;
	
	print_result(prob, observe, size, RAND_NUM_1);
	observe = calloc(size, sizeof(int));


	for (int i = 0; i < RAND_NUM_2; ++i)
		observe[find_index(prob, rand_double(),size)]++;
	
	print_result(prob, observe, size, RAND_NUM_2);
	observe = calloc(size, sizeof(int));

	for (int i = 0; i < RAND_NUM_3; ++i)
		observe[find_index(prob, rand_double(),size)]++;
	
	print_result(prob, observe, size, RAND_NUM_3);
	observe = calloc(size, sizeof(int));
	

	for (int i = 0; i < RAND_NUM_4; ++i)
		observe[find_index(prob, rand_double(),size)]++;
	
	print_result(prob, observe, size, RAND_NUM_4);
	observe = calloc(size, sizeof(int));
}