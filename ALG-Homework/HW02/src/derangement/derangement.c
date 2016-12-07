#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int parish(int n){
	if(n <= 1) return 0;
	if (n == 2) return 1;
	return n * parish(n-1) + pow(-1,n);
}
int min(int a, int b){
	return a < b ? a : b;
}
int fact(int n){
	return n < 2 ? 1 : n * fact(n-1);
}
void simplificate_fraction(int * numerator, int * denominator){
	int min_num = min (*numerator, *denominator);
	for (int i = min_num; i > 1; i--){
		if( (*numerator % i) ==0 && (*denominator % i)==0) {
			*numerator = (*numerator) / i;
			*denominator = (*denominator) / i;
			return;
		}
	}
}
int main(int argc,char ** args){
	if(argc < 2 ) {
		printf("%s\n", "wrong input!");
		return 0;
	}
	int input = atoi(args[1]);
	int denominator = fact(input);
	int numerator = parish(input);	
	simplificate_fraction(&numerator, &denominator);
	printf("%d / %d \n",numerator , denominator);
	return 0;

}
