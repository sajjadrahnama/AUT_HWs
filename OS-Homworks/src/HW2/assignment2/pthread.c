

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "func.h"

int main(int argc, char const *argv[]){
	if (argc < 4 ) {
		printf("%s\n%s\n", "3 arguments needed","Read the readme file");
		return 0;
	}
	int row = atoi(argv[1]);
	int col = atoi(argv[2]);
	int multi = atoi(argv[3]);
	/* message */
	printf("%s\n", "generating matrix...");
	generate_matrix("matrix1.txt",row ,col );
	generate_matrix("matrix2.txt",row ,col );

	/* message */
	printf("%s\n", "matrix generated successfully.");
	int ** mat;
	if(multi) mat=read_and_compute_matrix_concurrent("matrix1.txt","matrix2.txt",row,col);
	else mat=read_and_compute_matrix_sequential("matrix1.txt","matrix2.txt",row,col);
	print_matrix(mat,row,col);	
	output(mat,"output.txt",row,col);

	
}