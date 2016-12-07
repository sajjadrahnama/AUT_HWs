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

void quick_sort(int *list, int left, int right)
{
    if (left < right) {
        int i = left;
        int j = right;
        int min = i;
        i++;
        int pivot = list[min];
        while (left<j || i<right)
        {
            while (list[i]<pivot)
                i++;
            while (list[j]>pivot)
                j--;

            if (i <= j) {
                interchange(list, i, j);
                i++;
                j--;
            }
            else {
                interchange(list, j, min);
                if (left<j)
                    quick_sort(list, left, j);
                if (i<right)
                    quick_sort(list, i, right);
                return;
            }
        }

    }
}

int main(int argc, char *argv[]){
    int * array_source = malloc(sizeof(int) * 250000);
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

    quick_sort(array_source,0,length);

    end = clock();
    time_spent = (double)(end - begin)*1000 / CLOCKS_PER_SEC;

    for (int i = 0; i < length; ++i) {
//        printf("%d\n",array_source[i]);
    }

    printf("\nsort time %f milliseconds\n",time_spent);
    return 0;
}
