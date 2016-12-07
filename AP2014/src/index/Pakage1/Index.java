package Pakage1;

import java.util.Scanner;

public class Index {
	static Scanner g = new Scanner(System.in);
	public static String getLine(){
		String A;
		A=g.nextLine();
		return A;
	}
	private MyVector <Tuple> tuples =new MyVector <Tuple>();
	public void addWord(String word,int line){
		int i,flag=0;
		for(i=0;i<tuples.size();i++){
			if(word.equals(tuples.elementAt(i).getWord())) {
				tuples.elementAt(i).add(line);
				flag=1;
				break;
			}
		}
		if(flag==0)tuples.sortedInsert(new Tuple(word,line));
	}
	public Tuple getTuple (int i){
		return tuples.elementAt(i);
	}
	public int size(){
		return tuples.size();
	}
	public void display(Index I){
		int i,j;
		for (i=0;i<I.size();i++){
			System.out.printf("%-20s ",I.getTuple(i).getWord());
			System.out.print(I.getTuple(i).getNode(0));
			for(j=1;j<I.getTuple(i).nodesSize();j++){
				System.out.printf(",%s",I.getTuple(i).getNode(j));
			}
			if(i!=I.size()-1) System.out.print("\n");
		}
	}
	public static void main (String args[]){
		Index I=new Index();
		int line=1,i=0;
		boolean EOP=false;
		String p ="";
		while (!EOP){
			String sent=getLine();
			Sentence S=new Sentence (sent,line);
			while(i<S.size() && !p.equals("FINISHED")){
				p=S.getWord(i);
				if (p.equals("FINISHED")) EOP=true;
				else I.addWord(p,line);
				i++;
			}
			i=0;
			line++;
		}
		I.display(I);
	}
}

