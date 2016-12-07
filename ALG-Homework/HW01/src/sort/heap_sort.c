//
// Created by Sajjad on 2/5/2016 AD.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>


void interchange(int *list, int i, int j)
{
    int temp = list[i];
    list[i] = list[j];
    list[j] = temp;
}

void shift_up(int *list, int index){
    while(index > 0 && list[index] > list[(index-1)/2]){
        interchange(list, index, (index-1)/2);
        index = (index-1)/2;
    }
}

void build_heap(int *list, int *dest, int length){
    for (int i = 0; i < length; ++i) {
        dest[i] = list[i];
        shift_up(dest,i);
    }
}
void heapify(int *list , int length){
    int i = 0;
    while(2*i+1 < length){
        if(2*i+2 < length && list[2*i+2] > list[2*i+1] && list[2*i+2] > list[i]){
            interchange(list,i,2*i+2);
            i = 2*i+2;
        }
        else if(list[2*i+1] > list[i]){
            interchange(list, i , 2*i+1);
            i = 2*i+1;
        }
        else break;
    }
}
void heap_sort(int *list,int *dest, int length)
{
    build_heap(list,dest,length);
    for (int i = length - 1; i > 0; --i) {
        interchange(dest,0,i);
        heapify(dest,i);
    }
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

    heap_sort(array_source,array_dest,length);

    end = clock();
    time_spent = (double)(end - begin)*1000 / CLOCKS_PER_SEC;

    for (int i = 0; i < length; ++i) {
//        printf("%d\n",array_dest[i]);
    }

    printf("\nsort time %f milliseconds\n",time_spent);
    return 0;
}
