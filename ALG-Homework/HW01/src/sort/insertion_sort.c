//
// Created by Sajjad on 2/5/2016 AD.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>


void insert(int i, int *array, int length){
    if(length == 0){
        array[0] = i;
        return;
    }
    while(length > 0 && i < array[length - 1]){
        array[length] = array[length - 1];
        length--;
    }
    array[length] = i;
}

int main(int argc, char *argv[]){
    int * array_source = malloc(sizeof(int) * 250000);
    int * array_dest = malloc(sizeof(int) * 250000);
    int length = 0 , array_cell=0;

    if(argc < 2){
        printf("wrong input!\n");
        return 0;
    }


    FILE * file = fopen(argv[1],"r");
    if(!file){
        printf("file not found!\n");
        return 0;
    }
    fscanf(file,"%d",&array_cell);
    while (!feof(file)){
        array_source[length] = array_cell;
        length++;
        fscanf(file,"%d",&array_cell);

    }

    clock_t begin, end;
    double time_spent;
    begin = clock();

    for (int i = 0;i < length; ++i) {
        insert(array_source[i],array_dest,i);
    }

    end = clock();
    time_spent = (double)(end - begin)*1000 / CLOCKS_PER_SEC;

    for (int i = 0; i < length; ++i) {
//        printf("%d\n",array_dest[i]);
    }

    printf("\nsort time %f milliseconds\n",time_spent);
    return 0;

}
