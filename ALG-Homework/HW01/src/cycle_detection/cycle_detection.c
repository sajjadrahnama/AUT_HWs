#include <stdio.h>
#include <stdlib.h>

#define MAX_NODE_NUM    20000
//use visited_for_loop to kepp the loop initialize with -1
int dfs(int **matrix, int * visited_for_loop, int vertex, int length){
    int j = 0;
    while(visited_for_loop[j] > -1) j++;
    visited_for_loop[j] = vertex;
    for (int i = 0; i < length; ++i){
    	if(matrix[vertex][i]){
            int j = 0;
            while(visited_for_loop[j] > -1){
                if(visited_for_loop[j] == i){
                    int k = j,l=0;
                    //move the loop to the begining of visited_for_loop array
                    while(visited_for_loop[k] > -1){
                        visited_for_loop[l] = visited_for_loop[k];
                        l++;
                        k++;
                    }
                    while(visited_for_loop[l] > -1){
                        visited_for_loop[l] = -1;
                        l++;
                    }
                    return 1;
                }
                j++;
            }
        }
    }
    int t = 0;
    while(visited_for_loop[t] > -1) t++;
    for (int i = 0; i < length; ++i)
        if(matrix[vertex][i]){
            if(dfs(matrix,visited_for_loop,i,length)) {
                return 1;
            }
            // cleaning the part of visitde_for_loop array made by unsuccessfull serach for loop in each dfs
            int k =t;
            while(visited_for_loop[k] > -1) {
                visited_for_loop[k] = -1;
                k++;
            }
        }
    return 0;
}
int cycle_detection(int **matrix, int * visited_for_loop, int length){
    for (int i = 0; i < length; ++i){
        if(dfs(matrix,visited_for_loop,i,length)) return 1;
        else{
            // each time dfs for a vertex as root didnt find loop again initialize  visited_for loop with -1
            int j = 0;
            while(visited_for_loop[j] > -1){
                visited_for_loop[j] = -1;
                j++;
            }
        }
    }
    return 0;
}
int main(int argc, char *argv[]){
    int size = 0;
    int * visited_for_loop = calloc(MAX_NODE_NUM, sizeof(int));
    for (int i = 0; i < MAX_NODE_NUM; ++i)visited_for_loop[i]=-1;

    if(argc < 2){
        printf("wrong input!\n");
        return 0;
    }
    
    
    FILE * file = fopen(argv[1],"r");
    if(!file){
        printf("file not found!\n");
        return 0;
    }
    fscanf(file,"%d",&size);
    int **matrix = malloc(sizeof(int*)*size);
    for (int i=0; i < size; i++) {
        matrix[i] = malloc(sizeof(int)*size);
    }
    for (int i = 0; i< size; i++) {
        for (int j = 0; j < size;j++) {
            fscanf(file,"%d",&matrix[i][j]);
        }
    }
    
    int res = cycle_detection(matrix,visited_for_loop,size);
    
    if(res)printf("has cycle\n");
    else printf("NO cycle\n");
    int j = 0;
    while(visited_for_loop[j] > -1){
        printf("%d\n",visited_for_loop[j] );
        j++;
    }
    return 0;
}