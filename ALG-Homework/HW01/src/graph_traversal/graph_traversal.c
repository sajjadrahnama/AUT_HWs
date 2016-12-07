#include <stdio.h>
#include <stdlib.h>


int connectd_components(int **matrix, int *visited,int vertex, int length, int alpha){
	if(visited[vertex]!=0) return 0;
	visited[vertex] = alpha;
	for (int i = 0; i < length; ++i){
		if(matrix[vertex][i] != 0 && visited[i]==0 && i!=vertex){
			connectd_components(matrix,visited,i,length,alpha);
			visited[i] = alpha;
		}
	}
	return 1;
}

int graph_traversal(int **matrix, int *visited, int length){
	int component_num=1;
	for (int i = 0; i < length; ++i){
    	int new =connectd_components(matrix,visited,i,length,component_num);
    	if(new) component_num++;
    }
    return component_num;
}

int main(int argc, char *argv[]){
    int size = 0;
    
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
    int *visited = calloc(size,sizeof(int));
    int **matrix = malloc(sizeof(int*)*size);
    for (int i=0; i < size; i++) {
        matrix[i] = malloc(sizeof(int)*size);
    }
    for (int i = 0; i< size; i++) {
        for (int j = 0; j < size;j++) {
            fscanf(file,"%d",&matrix[i][j]);
        }
    }
    int component_num = graph_traversal(matrix,visited,size);
    // number of vertexs in each sub graph
    int * component_vertexs = calloc(component_num-1, sizeof(int));
    // number of max vertex in max sub graph
    int max_vertex = 0;
    // sub Graph name that has max vertexs
    int max_component = 0;
    printf("number of connectd_components :\t%d\n", component_num);
    for (int i = 1; i < component_num; ++i){
    	printf("component %d:\n",i );
    	for (int j = 0; j < size; ++j){
    		if(visited[j] == i) {
    			printf("\t\t%d\n",j);
    			component_vertexs[i]++;
    		}
    		if(component_vertexs[i] > max_vertex) {
    			max_component = i;
    			max_vertex = component_vertexs[i];
    		}
    	}
    }
    
    printf("sub graph %d with %d vertex is max\n", max_component,max_vertex);
    return 0;
}