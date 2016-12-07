#include "param.h"
#include "types.h"
#include "stat.h"
#include "user.h"
#include "fcntl.h"
#include "fs.h"
#include "fcntl.h"
#include "syscall.h"
#include "traps.h"
#include "memlayout.h"
#include "mmu.h"
#include "proc.h"
#include "x86.h"

int main(int argc, char const *argv[])
{
	struct proc * a;
	a= (struct proc *)malloc(sizeof (struct proc));
	printf(1,"%d\n",loadpcb(a));
	exit();
}