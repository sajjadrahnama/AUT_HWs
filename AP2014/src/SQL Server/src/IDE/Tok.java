package IDE;

import java.io.Serializable;
import java.util.StringTokenizer;

public class Tok implements Serializable {
	private static final long serialVersionUID = 4371096495121995647L;

	public static String[] split(String S,String tokenizor){
		String [] b;
		StringTokenizer a=new StringTokenizer(S,tokenizor);
		b=new String[a.countTokens()];
		int i=0;
		while (a.hasMoreElements()){
			b[i]=a.nextToken();
			i++;
		}
		return b;
	}
}

