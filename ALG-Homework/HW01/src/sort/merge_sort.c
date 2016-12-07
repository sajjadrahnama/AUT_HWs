//
// Created by Sajjad on 2/5/2016 AD.
//

#include <stdio.h>
#include <stdlib.h>
#include <time.h>




void merge(int *source, int left, int right, int mid){
    int * dest = malloc(sizeof(int)*(right-left+1));
    int i = left, j = mid + 1, k = 0;
    while(i <= mid && j<=right){
        if(source[i] < source[j]) dest[k++] = source[i++];
        else dest[k++] = source[j++];
    }
    while (i <= mid) dest[k++] = source[i++];
    while (j <= right) dest[k++] = source[j++];
    for (int i = left; i <=right ; ++i) {
        source[i] = dest[i-left];
    }
    free(dest);
}

void merge_sort(int *list, int left,int right)
{
    if(left<right){
        int mid = (left + right)/2;
        merge_sort(list, left, mid);
        merge_sort(list, mid + 1, right);
        merge(list, left, right, mid);
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

    merge_sort(array_source,0,length);

    end = clock();
    time_spent = (double)(end - begin)*1000 / CLOCKS_PER_SEC;

    for (int i = 0; i < length; ++i) {
//        printf("%d\n",array_source[i]);
    }
    printf("\nsort time %f milliseconds\n",time_spent);
    return 0;
}
