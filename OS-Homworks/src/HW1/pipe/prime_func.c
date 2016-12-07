/*
 * In The Name Of God
 * ========================================
 * [] File Name : prime_func.c
 *
 * [] Creation Date : 16-10-2015
 *
 * [] Last Modified : Fri 16 Oct 2015 6:14:00 PM IRST
 *
 * [] Created By : Sajjad Rahnama (sajjad.rahnama7@gmail.com)
 * =======================================
*/
 
int prime_func(int a){
	for(int i = 2 ; i <= a ; i++)
		if(a%i == 0 ) return i;
	return a;
}