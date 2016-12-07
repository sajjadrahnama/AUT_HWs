package Pakage1;

import java.util.Vector;

public class Tuple implements Comparable<Tuple> {
	public Tuple(String word,int line) {
		this.nodes.add(new Node(line));
		this.word=word;
	}
	private String word;
	private Vector <Node> nodes=new Vector <Node>(); 
	public void add(int line){
		int i,j=0;
		for (i=0;i<nodes.size();i++){
			if(nodes.elementAt(i).lineEquals(line)) {
				nodes.elementAt(i).add();
				j=1;
				break;
			}
		}
		if(j==0) nodes.add(new Node(line));
	}
	public boolean wordEquals(String word){
		if(this.word.equals(word)) return true;
		else return false;
	}
	@Override
	public int compareTo(Tuple o) {
		return word.compareTo(o.word);
	}
	public String getWord() {
		return word;
	}
	public Node getNode(int i) {
		return nodes.elementAt(i);
	}
	public int nodesSize(){
		return nodes.size();
	}	
}

