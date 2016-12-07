
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include "func.h"

void * compute_row(void * arguments){
	compute_row_args * args= (compute_row_args *)arguments;
	int * row1 = args->row1;
	int * row2 = args->row2;
	int *res = args->res;
	int cols = args->cols;
	int row_num = args->row_num;
	for (int i = 0; i < cols; i++){
		res[i]=row1[i]+row2[i];
	}
	printf("%s%d%s\n","row ",row_num," computed succesfully");
	free(row1);
	free(row2);
	pthread_exit(0);
}

int ** read_and_compute_matrix_concurrent(const char * m_path1,const char * m_path2, int rows, int cols){
	void *(*compute_function)(void * args);
	compute_function= &compute_row;
	int ** res = malloc(sizeof(int*) * rows);
	pthread_t * threads = malloc(sizeof(pthread_t*)* rows);
	
	FILE * f1= fopen(m_path1,"r+");
	FILE * f2= fopen(m_path2,"r+");
	
	if (!f1 || !f2) return NULL;
	for (int i = 0; i < rows; i++){
		int * temp1 = malloc(sizeof(int) * cols);
		int * temp2 = malloc(sizeof(int) * cols);
		for (int j = 0; j < cols; j++){
			fscanf( f1, "%d ", &temp1[j]);
			fscanf( f2, "%d ", &temp2[j]);
		}
		res[i]= (int *)malloc(sizeof(int)*cols);
		compute_row_args * arguments =(compute_row_args *)malloc(sizeof(compute_row_args));
		arguments->row1=temp1;
		arguments->row2=temp2;
		arguments->res=res[i];
		arguments->cols=cols;
		arguments->row_num=i;

		int r=pthread_create( &threads[i], NULL, compute_function, (void *)arguments);
	    if(r){
	        fprintf(stderr,"Error - pthread_create() return code: %d\n",r);
	        exit(EXIT_FAILURE);
	    }
	}
	for (int i = 0; i < rows; i++){
		pthread_join( threads[i], NULL);
	}
	fclose(f1);
	fclose(f2);
	return res;
}
int ** read_and_compute_matrix_sequential(const char * m_path1,const char * m_path2, int rows, int cols){
	int ** res = malloc(sizeof(int*) * rows);
	FILE * f1= fopen(m_path1,"r+");
	FILE * f2= fopen(m_path2,"r+");
	
	if (!f1 || !f2) return NULL;
	for (int i = 0; i < rows; i++){
		int  temp1,temp2;
		res[i]= (int *)malloc(sizeof(int)*cols);
		for (int j = 0; j < cols; j++){
			fscanf( f1, "%d ", &temp1);
			fscanf( f2, "%d ", &temp2);
			res[i][j]=temp1+temp2;
		}
	}
	
	fclose(f1);
	fclose(f2);
	return res;
}

void print_matrix(int ** matrix, int rows, int cols){
	for (int i = 0; i < rows; i++){
		for (int j = 0; j < cols; j++){
			printf("%d\t", matrix[i][j]);
		}
		printf("\n");
	}
	for (int i = 0; i < cols; i++) printf("-");
}

void generate_matrix(const char * path, int rows, int cols){
	FILE * f= fopen(path,"w+");
	if (!f) return;
	for (int i = 0; i < rows; i++){
		for (int j = 0; j < cols; j++){
			fprintf( f, "%d ", rand()%100 + 1);
		}
		fprintf( f, "\r\n");
	}
	fclose(f);
}

void output(int ** mat,const char * path , int rows , int cols){
	FILE * f = fopen(path,"w+");
	for (int i = 0; i < rows; i++){
		for (int j = 0; j < cols; j++){
			fprintf(f,"%d\t",mat[i][j] );
		}
		fprintf(f,"\n");
	}
}


