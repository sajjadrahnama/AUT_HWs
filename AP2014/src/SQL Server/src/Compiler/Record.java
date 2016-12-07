package Compiler;

import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.Vector;

import CompileError.ParameterNotFoundException;
import IDE.Tok;
import IDE.Tools;
	
public class Record implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8320619151798443955L;
	private Vector<Parameter> parameters;
	public Record(){
		parameters=new Vector<Parameter>();
	}
	public Parameter getParameter(int i) throws Exception{
		try{
			return parameters.elementAt(i);
		}catch(ArrayIndexOutOfBoundsException e ){
			throw (new ParameterNotFoundException());
		}
	}
	public String toString()
	{
		String res = "" ;
		for(Parameter p : parameters)
			res += (p + "\t");
		return res ;
	}
	public Parameter getParameter(String name) throws Exception{
		for(Parameter s:parameters){
			if(s.getName().equals(name)) {
				return s;
			}
		}
		throw (new ParameterNotFoundException());
	}
	public boolean isParmeter(String name){
		for (Parameter s: parameters){
			if(s.getName().equals(name)) return true;
		}
		return false;
	}
	public void addParameter(Parameter A){
		parameters.add(A);
	}
	public boolean satisfyCondition(String w)  throws Exception{
		
		w=w.replace("(", " ( ");
		w=w.replace(")", " ) ");
		boolean v=true;
		w=w.trim();
		String P="";
		if(w.charAt(0)=='(') {
			P=Tools.parantezFinder(w).trim();
			w=Tools.charDelete(P.length(),w).trim();
			P=Tools.charDelete(1,P).trim();
			P=Tools.reverceCharDelete(1,P).trim();
			v=this.satisfyCondition(P);
		}
		else {
			P=w.split("([ ]+and)|([ ]+or)")[0];
			v=this.satisfyMiniCondition(P);
			w=Tools.charDelete(P.length(), w).trim();
		}
		if (w.length()>0 && Tok.split(w," ")[0].equals("or")){
			w=w.replaceFirst("or", "").trim();
			v=(v||this.satisfyCondition(w));
		}
		else if( w.length()>0 && Tok.split(w, " ")[0].equals("and")){
			w=w.replaceFirst("and", "").trim();
			v=(v && this.satisfyCondition(w));
		}
		return v;
	}
	private boolean satisfyMiniCondition(String s) throws Exception{
		if(s.equals(".")) return true;
		try{
			if(!Tools.isWhereP(s)) return false;
			s=s.replace("="," = ");
			s=s.replace(">"," > ");
			s=s.replace("<", " < ");
			String [] t=Tok.split(s, " ");
			Parameter a=null;
			int i=0;
			for(Parameter q:parameters){
				if(q.getName().equals(t[0])){
					a=q;
					i=1;
					break;
				}
			}
			if(i==0) throw (new ParameterNotFoundException());
			if(t[1].equals("=")){
				if(a.getValue().equals(t[2])) return true;
				else  return false;
			}
			else if(t[1].equals(">") && a.getType().equals("String")){
				if(a.getValue().compareTo(t[2])<0) return true;
				else return false;
			}
			else if(t[1].equals(">")){
				if(Float.parseFloat(a.getValue())>Float.parseFloat(t[2])) return true;
				else return false;
			}
			else if(t[1].equals("<") && a.getType().equals("String")){
				if(a.getValue().compareTo(t[2])>0) return true;
				else return false;
			}
			else if(t[1].equals("<")){
				if(Float.parseFloat(a.getValue())<Float.parseFloat(t[2])) return true;
				else return false;
			}
			else return false;
		}catch(NullPointerException e){ return false;}
	}
	public String update(String S)throws Exception{
		String q="Before : "+this.toString()+"\n";
		boolean flag = false;
		StringTokenizer w=new StringTokenizer(S,"*");
		while (w.hasMoreElements()){
			String p=w.nextToken();
			for(Parameter e:parameters){
				if(e.getName().equals(Tok.split(p," ")[0])) {
				flag=true;
					e.setValue(Tok.split(p, " ")[2]);
					break;
				}
			}
			if (flag){
				q+="After Update :"+ this.toString()+"\n";
				return q;
			}
		
		}
		return null;
	}
}
