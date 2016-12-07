package IDE;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameChecker	implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8562956658880077005L;
	public boolean isCorrect(String S){
		if(S.equals("Where"))return false;
		Pattern exp=Pattern.compile("\\d{3,}");
		Matcher a=exp.matcher(S);
		if(!S.matches("[a-zA-Z][a-zA-Z0-9]*"))return false ;
		else if (a.find()) return false ;
		else return true;
	//	else 
	}
	public boolean isNum(String S){
		try{
			Float.parseFloat(S);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	public boolean isInt(String S){
		try{
			Integer.parseInt(S);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
}
