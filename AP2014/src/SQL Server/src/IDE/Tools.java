package IDE;

import java.io.Serializable;
import java.util.Vector;

import SyntaxError.ParantezException;
import SyntaxError.WhereException;

public class Tools implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2179188282809432278L;
	private static NameChecker q=new NameChecker();
	public static boolean PaC(String S){	//Parameter format in Create Table//
		String [] A=Tok.split(S," ");
		if(A.length>3) return false;
		else if(!q.isCorrect(A[0])) return false;
		else if(A[1].equals("int")||A[1].equals("float")){
			if(A.length<3) return true;
			else if(q.isNum(A[2])) return true;
			else return false;
		}
		else if(A[1].equals("String") ) {
			if(A.length<3) return true;
			else if(q.isInt(A[2])) return true;
			else return false;
		}
		else return false;
	}
	public static boolean isUpdateP(String S){
		if(!S.contains("=")) return false;
		if(!q.isCorrect(Tok.split(S,"=")[0].trim())) return false;
		String P=Tok.split(S,"=")[1];
		if (Tools.isString(P)) return true;
		else return false;
		
	}
	public static boolean isWhereP(String S){
		if(!S.contains("=") && !S.contains(">") && !S.contains("<")) return false;
		if(!q.isCorrect(Tok.split(S,"=><")[0].trim())) return false;
		String P=Tok.split(S,"=><")[1];
		if (Tools.isString(P)) return true;
		else return false;
		
	}
	public static boolean isString (String S){
		if(Tok.split(S," ").length==1) return true;
		else if(S.trim().charAt(0)=='"'){
			String p=S.replaceFirst("\"","");
			if(!p.contains("\"")) return false;
			else if(Tok.split(p,"\"").length>1) return false ;
			else return true;
		}
		else return false;
	}
	public static boolean isWhere(String S) throws WhereException, ParantezException{
		if(S.matches(".*and[ ]*[)].*")) return false;
		if(S.matches(".*or[ ]*[)].*")) return false;
		
		try{
			if (!S.contains("(") && !S.contains(")")){
				while (S.length()>0){
					String h=S.split("and|or")[0].trim();
					if(!Tools.isWhereP(h)) return false;
					else{
						S=S.replaceFirst("[ ]*"+h+"[ ]*","").trim();
						S=S.replaceFirst("or|and","");
					}
				}
				return true;
			}
			else{
				while (S.length()>0){
					String h="";
					if(S.trim().charAt(0)=='(') {
						String hh=Tools.parantezFinder(S);
						int i=1;
						for(i=1;i<hh.length()-1;i++) h=h+hh.charAt(i);
						if(!isWhere(h)) return false;
						S=S.replaceFirst("\\(","");
						S=S.replaceFirst("\\)","");
						S=S.replaceFirst(h+"([ ]+or|and[ ]*)*","").trim();
					}
					else {
						h=S.split("((or|and)[ ]+)|((or|and)\\()")[0].trim();
						if(!isWhere(h)) return false;
						S=S.replaceFirst(h+"([ ]+or|and[ ]*)*","").trim();
					}
				}
			}
			return true;
		}catch(ArrayIndexOutOfBoundsException e){return false;}catch (StringIndexOutOfBoundsException ee) {
			return false;
		}
	}
	public static String parantezFinder(String S) throws ParantezException{
		S=S.trim();
		if(S.trim().charAt(0)!='('){
			String p=Tok.split(S,"(")[0].trim();
			S=S.replaceFirst(p,"").trim();
		}
		String q="(";
		int i=1,j=1;
		while (i>0){
			try{	
				if(S.charAt(j)=='(') i++;
				if(S.charAt(j)==')') i--;
				q=q+S.charAt(j);
				j++;
			}catch(StringIndexOutOfBoundsException e){
				throw (new ParantezException());
			}
		}
		return q;
	}
	public static String charDelete(int a,String s){
		if(s.length()<a) return s;
		String ss="";
		for (int i=0;i<s.length();i++){
			if(i>a-1) ss=ss+s.charAt(i);
		}
		return ss;
	}
	public static String reverceCharDelete(int a,String s){
		StringBuilder b=new StringBuilder(s);
		s=b.reverse().toString();
		if(s.length()<a) return s;
		String ss="";
		for (int i=0;i<s.length();i++){
			if(i>a-1) ss=ss+s.charAt(i);
		}
		b=new StringBuilder(ss);
		return b.reverse().toString();
	}
	public static int StringIndexFinder(Vector<String>s,String ss){
		for(int i=0;i<s.size();i++){
			if(s.elementAt(i).equals(ss)) return i;
		}
		return -1;
	}
	
	public static void main(String [] args){
		System.out.println(Tools.charDelete(3,"al"));
	}
}
