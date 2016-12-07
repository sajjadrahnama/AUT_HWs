
typedef struct comput_row_args{
	int * row1,* row2,* res;
	int cols,row_num;
}compute_row_args;

int ** read_and_compute_matrix_concurrent(const char * m_path1,const char * m_path2, int rows, int cols);
int ** read_and_compute_matrix_sequential(const char * m_path1,const char * m_path2, int rows, int cols);
void * compute_row(void * args);
void print_matrix(int ** matrix, int rows, int cols);
void generate_matrix(const char * path,int rows, int cols);
void output(int ** mat,const char * path,int rows , int cols);
