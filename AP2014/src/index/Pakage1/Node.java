package Pakage1;

public class Node {
	public boolean lineEquals(int line) {
		if (this.lineNumber == line)
			return true;
		else return false;
	}
	private int lineNumber;
	private int repeats;
	public Node (int Line){
		lineNumber=Line;
		repeats=1;
	}
	public String toString(){
		if (repeats!=1)return lineNumber +"("+repeats+")";
		else return lineNumber+"";
	}
	public void add(){
		repeats++;
	}
}
	
