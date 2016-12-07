package calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inputt {
	private BufferedReader b= new BufferedReader(new InputStreamReader(System.in));
	public String charDelete(int a,String s){
		if(s.length()<a) return s;
		String ss="";
		for (int i=0;i<s.length();i++){
			if(i>a-1) ss=ss+s.charAt(i);
		}
		return ss;
	}
	public String readCom(){
		try {
			return b.readLine();
		} catch (IOException e) {}
		return null;
	}
	public String [] calcCom(String S){
		String [] res=new String [2];
		res[0]="";
		if(S.charAt(0)=='(') {
			res[0]="(";
			res[1]="2";
			return res;
		}
		else if(S.charAt(0)==')') {
			res[0]=")";
			res[1]="3";
			return res;
		}
		else if(S.charAt(0)=='+') {
			res[0]="+";
			res[1]="1";
			return res;
		}
		else if(S.charAt(0)=='*') {
			res[0]="*";
			res[1]="1";
			return res;
		}
		else if(S.charAt(0)=='-') {
			res[0]="-";
			res[1]="1";
			return res;
		}
		else if(S.charAt(0)>47 && S.charAt(0)<58){
			while (S.length()>0 && S.charAt(0)>47 && S.charAt(0)<58){
				res[0]+=S.charAt(0);
				S=charDelete(1, S);
			}	
			res[1]="0";
			return res;
		}
		else {
			res[1]="f";
			return res;
		}
	}
	boolean parantez(String a){
		int i=0,j=0;
		while ( i<a.length()){
			if(a.charAt(i)==')') j--;
			if(a.charAt(i)=='(') j++;
			i++;
		}
		if(j==0) return true;
		else return false;
	}
	public static void main (String[] b){
	}
}
