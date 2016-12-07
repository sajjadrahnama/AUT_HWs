#include <stdio.h>
#include <stdlib.h>

int ** read_matrix(FILE * f, int size);
void print_matrix (int ** matrix, int size);
int * read_last_line(FILE* f, int size);
double determinant(int **matrix, int size, int free_matrix);
int ** remove_row_and_column(int ** matrix, int row, int coulmn, int size);
int ** replace_coulmn(int ** matrix, int *coulmn, int coulmn_number, int size);
double * cramer(int **matrix, int *answers, int size);

int ** read_matrix(FILE * f, int size){
	int ** res = malloc(sizeof(int*) * size);
	for (int i = 0; i < size; ++i)
		res[i] = malloc(sizeof(int)*size);
	for (int i = 0; i < size; ++i)
		for (int j = 0; j < size; ++j){
			fscanf(f,"%d",&res[i][j]);
		}
	return res;
}

void print_matrix (int ** matrix, int size){
	for (int i = 0; i < size; ++i){
		for (int j = 0; j < size; ++j){
			printf("%d ", matrix[i][j]);
		}
		printf("\n");
	}
}

int * read_last_line(FILE* f, int size){
	int * res = malloc(sizeof(int) * size);
	for (int i = 0; i < size; ++i)
		fscanf(f,"%d",&res[i]);
	return res;
}

double determinant(int **matrix, int size, int free_matrix){
	if(size < 1) return 0;
	if(size == 1) return matrix[0][0];
	if(size == 2) return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
	double res = 0, pow = 1;
	for (int i = 0; i < size; ++i){
		int **mat = remove_row_and_column(matrix, 0, i, size);
		res += (matrix[0][i] * pow ) * determinant(mat, size-1, 1);
		pow *= -1;
	}
	if(free_matrix) free(matrix);
	return res;
}

int ** remove_row_and_column(int ** matrix, int row, int coulmn, int size){
	int ** res = malloc(sizeof(int*) * (size-1));
	for (int i = 0; i < size; ++i)
		res[i] = malloc(sizeof(int)*(size-1));
	for (int i = 0; i < size; ++i){
		if(i == row) continue;
		else for (int j = 0; j < size; ++j){
			if(j == coulmn) continue;
			else if(i < row && j < coulmn) res[i][j] = matrix[i][j];
			else if(i < row && j > coulmn) res[i][j-1] = matrix[i][j];
			else if(i > row && j < coulmn) res[i-1][j] = matrix[i][j];
			else if(i > row && j > coulmn) res[i-1][j-1] = matrix[i][j];
		}
	}
	return res;		
}

int ** replace_coulmn(int ** matrix, int *coulmn, int coulmn_number, int size){
	int ** res = malloc(sizeof(int*) * size);
	for (int i = 0; i < size; ++i)
		res[i] = malloc(sizeof(int)* size);
	for (int i = 0; i < size; ++i){
		for (int j = 0; j < size; ++j){
			if(j == coulmn_number) res[i][j] = coulmn[i];
			else res[i][j] = matrix[i][j];
		}
	}
	return res;		
}

double * cramer(int **matrix, int *answers, int size){
	double determin = determinant(matrix, size, 0);
	if(determin == 0.0) return NULL;
	double * res = malloc( sizeof(double) * size);
	for (int i = 0; i < size; ++i){
		int ** m = replace_coulmn(matrix, answers, i, size);
		res[i] = determinant(m, size, 0) / determin;
		free(m);
	}
	return res;
}

int main(int argc,char ** args){
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
	int ** matrix = NULL, *answers = NULL;
	matrix = read_matrix(f,size);
	answers = read_last_line(f, size);
	double * solve = cramer(matrix, answers, size);
	for (int i = 0; i < size; ++i)
		printf("%.2lf\n", solve[i]);
	return 0;

}
