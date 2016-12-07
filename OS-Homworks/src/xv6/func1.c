#include "types.h"
#include "user.h"
#include "migrate.h"


int main(int argc, char const *argv[])
{

	int pid=fork();
	int i=0;
	if(pid==0){
		printf(1,"\nchild created.\n");
		for (; i < 20; ++i){
			printf(1,"child %d\n",i);
			if(i==11) sajjad_save();
		}
	}
	else{
		wait();
		printf(1,"child saved and killed\n");
		for (i = 0; i < 7; ++i) {
			printf(1,"parent %d\n",i);
		}
		int r=sajjad_load();
		wait();
		printf(1,"child terminated with pid = %d\n",r);

	}
	exit();
}