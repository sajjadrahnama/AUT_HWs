package Pakage1;

import java.util.StringTokenizer;
import java.util.Vector;

public class Sentence {
	private Vector<String> words=new Vector<String>();
	private int line;
	public Sentence(String sentence,int line){
		this.line=line;
		StringTokenizer st=new StringTokenizer(sentence,"!@#$%^&*()1234567890_+-=/.,?><|}{[] \t \\\"';\n");
		while (st.hasMoreElements()){
			words.add(st.nextToken().toUpperCase());
		}
		
	}
	public String getWord(int i){
		return words.elementAt(i);
	}
	public int line(){
		return line;
	}
	public int size(){
		return words.size();
	}
}
