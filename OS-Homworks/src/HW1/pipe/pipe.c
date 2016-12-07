/*
 * In The Name Of God
 * ========================================
 * [] File Name : pipe.c
 *
 * [] Creation Date : 16-10-2015
 *
 * [] Last Modified : Fri 16 Oct 2015 5:08:00 PM IRST
 *
 * [] Created By : Sajjad Rahnama (sajjad.rahnama7@gmail.com)
 * =======================================
*/

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include "func.h"
int main(int argc, const char* argv[]) {
    int c_pid;
    int * pfd = malloc(2*sizeof(int));
    int * pfd2 = malloc(2*sizeof(int));
    pipe(pfd);
    pipe(pfd2);
    //fork error
    if((c_pid=fork())== -1){
    	printf("%s\n","fork error" );
    	return 0;
    }
    // child node
    if(c_pid==0){
    	int n;
    	close(pfd[1]);
    	read(pfd[0], &n, sizeof(int));
    	close(pfd[0]);
    	close(pfd2[0]);
    	printf("%s\n","child : proccessing...." );
    	n=prime_func(n);
    	write(pfd2[1], &n, sizeof(int));
    	close(pfd2[1]);

    }
    // parent node
    else{
    	int n;
    	printf("%s","parent : enter number -->  ");
    	scanf("%d",&n);
    	close(pfd[0]);
    	write(pfd[1], &n, sizeof(int));
    	close(pfd[1]);
    	close(pfd2[1]);
    	read(pfd2[0], &n, sizeof(int));
    	close(pfd2[0]);
    	printf("%s%d\n","parent : the first prime is ",n);
    }
}