//
// Created by sajjad on 1/16/16.
//

#include "migrate.h"


int sajjad_save(){
    struct proc *a;
    a= (struct proc *)malloc(sizeof (struct proc));
    savepcb(a);
    int fd=open("func",O_CREATE|O_RDWR);
    write(fd,a, sizeof(struct proc));
    close(fd);
    return 1;
}

int sajjad_load(){
    struct proc * a;
    a= (struct proc *)malloc(sizeof (struct proc));
    int fd=open("func",O_RDONLY);
    read(fd,a, sizeof(struct proc));
    close(fd);
    int pid=loadpcb(a);
    return pid;
}