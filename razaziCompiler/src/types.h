

// Created By Sajjad
#include <stdlib.h>
#include <iostream>
#include <vector>
#include <string>
using namespace std;


typedef struct
{
	string name;
	string type;
	string value;

}idEntry;

typedef struct table_st * blockEntry;

struct table_st{
	blockEntry backward;
	vector<blockEntry> block_entries;
	vector<idEntry> id_entries;
}; 

typedef struct{

	vector<int> true_list;
	vector<int> false_list;
	vector<int> next_list;
	vector<int> ivalues;
	vector<float> fvalues;
	int  quad;
	int isBoolean;
	string place;
	string code;
	string type;
	string name;
	// struct val* ids;
	int begin,end;

}val;












