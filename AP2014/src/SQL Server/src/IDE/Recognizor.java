package IDE;

import java.io.Serializable;
import java.util.StringTokenizer;

import SyntaxError.CreateException;
import SyntaxError.FirstWordException;
import SyntaxError.InsertException;
import SyntaxError.NameException;
import SyntaxError.ParameterException;
import SyntaxError.ParantezException;
import SyntaxError.SelectException;
import SyntaxError.SemiException;
import SyntaxError.SyntaxError;
import SyntaxError.UpdateException;
import SyntaxError.WhereException;

public class Recognizor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5139184018295060425L;
	private NameChecker N=new NameChecker();
	public String recognize(String Q) throws Exception{
		Q=Q.replaceAll("((i?)create)","Create").replaceAll("((i?)table)","Table").replace("(", " ( ")
		.replace(")", " ) ").replaceAll("((i?)drop)","Drop").replaceAll("((i?)insert)","Insert")
		.replaceAll("((i?)update)","Update").replaceAll("((i?)delete)","Delete")
		.replaceAll("((i?)select)","Select").replaceAll("((i?)into)","into")
		.replaceAll("((i?)parameter)","parameter").replaceAll("((i?)where)","Where").replaceAll("((i?)values)","values").replaceAll("((i?)string)","String").replace("*"," * ");
		try{	
			if(Tok.split(Q," ")[0].equalsIgnoreCase("Create")) return this.creatTable(Q);
			else if(Tok.split(Q," ")[0].equalsIgnoreCase("Drop")) return this.dropTable(Q);
			else if(Tok.split(Q," ")[0].equalsIgnoreCase("Insert")) return this.insert(Q);
			else if(Tok.split(Q," ")[0].equalsIgnoreCase("Update")) return this.update(Q);
			else if(Tok.split(Q," ")[0].equalsIgnoreCase("Select")) return this.select(Q);
			else if(Tok.split(Q," ")[0].equalsIgnoreCase("Delete")) return this.delete(Q);
			else if(Q.equalsIgnoreCase("exit")) return "e";
			else throw (new FirstWordException());
		}catch(ArrayIndexOutOfBoundsException e){
			throw (new FirstWordException());
		}catch(java.util.regex.PatternSyntaxException ee){throw (new SyntaxError());}
	}
	private String delete(String q) throws Exception{
		String R="D*";
		try{	
			if(!q.matches("Delete[ ]+from[ ]+.*") ) throw (new FirstWordException()); 
			q=q.replaceFirst("Delete[ ]+from[ ]+","").trim();
			String p=Tok.split(q," ")[0].trim();
			if(p.contains(";")){
				if(!q.matches(p+"(;*[ ]*)*")) throw (new SemiException());
				if(!N.isCorrect(Tok.split(p, ";")[0])) throw (new NameException());
				R+=((Tok.split(p, ";")[0]+"*").trim());
				R+=(".*");
				return R;
			}
			if(!N.isCorrect(p)) throw (new NameException());
			R+=(p.trim()+"*");
			q=q.replaceFirst(p, "").trim();
			p=Tok.split(q, " ")[0].trim();
			if(p.equals("Where")){
				q=q.replaceFirst("Where", "");
				p=Tok.split(q,";")[0].trim();
				try{	
					if(!Tok.split(q, ";")[1].trim().matches("(;*[ ]*)*")) throw (new SemiException());
				}catch(ArrayIndexOutOfBoundsException e){}
				if(p.equals("")) throw (new WhereException());
				R+=(p+"*");
				if(!Tools.isWhere(p)) throw (new WhereException());
				return R;
			}
			else {
				if(!q.matches("(;+[ ]*)*")) throw (new SemiException());
				R+=(".*");
			}
		}catch(ArrayIndexOutOfBoundsException e){throw (new SemiException());}
		return R;
	}
	private String select(String q) throws Exception{
		String R="S*";
		if (q.matches("Select[ ]*\\*[ ]*from.*")){
			q=q.replaceFirst("Select[ ]*\\*[ ]*from","").trim();
			try{
				String p=Tok.split(q," ;")[0];
				if(!N.isCorrect(p))throw (new NameException());
				R+=(".*"+p);
				q=q.replaceFirst(p,"").trim();
				if(!q.matches(";([ ]*;*)*")) throw (new SemiException());
			}catch(ArrayIndexOutOfBoundsException e){ throw (new SemiException());}
			return R;
		}
		q=q.replaceFirst("Select","").trim();
		StringTokenizer w=new StringTokenizer(q,",");
		int i=0;
		while (w.hasMoreElements() && i==0){
			String p=w.nextToken().trim();
			if(N.isCorrect(p)) {
				R+=(p+"*");
				q=q.replaceFirst(p+"[ ]*,","").trim();
			}
			else i=1;
		}
		String p=q.split("from")[0].trim();
		if(!N.isCorrect(p)) throw (new NameException());
		R+=(p+"*");
		q=q.replaceFirst(p, "").trim();
		if(!q.matches("from.*")) throw (new SelectException());
		q=q.replaceFirst("from","").trim();
		if(Tok.split(q," ")[0].contains(";")){
			String P=Tok.split(q, ";")[0];
			if(!N.isCorrect(P)) throw (new NameException());
			q=q.replaceFirst(P,"").trim();
			R+=("!*"+P+"*");
			if(!q.matches(";([ ]*;*)*")) throw (new SemiException());
			return R;
		}
		if(!N.isCorrect(Tok.split(q," ")[0].trim())) throw (new NameException());
		R+=("!*"+Tok.split(q," ")[0].trim()+"*");
		q=q.replaceFirst(Tok.split(q," ")[0].trim(), "").trim();
		q=q.replace("("," ( ");
		q=q.replace(")"," ) ");
		if(Tok.split(q," ")[0].equals("Where") || Tok.split(q, " ").equals("where")){
			q=q.replaceFirst(Tok.split(q," ")[0], "").trim();
			p=Tok.split(q,";")[0];
			if(!Tools.isWhere(p)) throw (new WhereException());
			R+=("!*"+p);
			if(!q.matches("[^;]*;+([ ]*;*)*")) throw (new SemiException());
			return R;
		}
		if(!q.trim().matches(";+([ ]*;*)*")) throw (new SemiException());
		return R;
	}
	private String update(String q) throws UpdateException, NameException, WhereException, ParantezException, SemiException {
		String R="U*";
		try{	
			String P; 
			if(!q.matches("Update([ ]+)from[ ]+.*")) throw (new UpdateException());
			P=q.replaceFirst("Update([ ]+)from[ ]+","");
			if (!(N.isCorrect(Tok.split(P," ")[0]))) throw (new NameException());
			R+=(Tok.split(P," ")[0]+"*");
			P=P.replaceFirst("[a-zA-Z0-9]*[ ]+","");
			if(!Tok.split(P.trim()," ")[0].equals("set")) throw (new UpdateException());
			P=P.replaceFirst("set[ ]+","");
			StringTokenizer w=new StringTokenizer(P);
			int i=0;
			while (w.hasMoreElements() & i==0){
				String p=w.nextToken(",");
				if( !w.hasMoreElements() && !p.contains(";")) throw (new SemiException());
				if(!Tools.isUpdateP(p)) {
					if(P.matches(".*[ ]+Where[ ]+.*")){
						p=p.split("Where")[0].trim();
						if(!Tools.isUpdateP(p)) throw (new UpdateException());
						P=P.replaceFirst("[ ]*"+p+"[ ]+","").trim();
						R+=(p+"*");
						P=P.replaceFirst("Where[ ]*","");
						if(!P.contains(";")) throw (new SemiException());
						String o=Tok.split(P, ";")[0].trim();
						if(!Tools.isWhere(o)) throw (new WhereException());
						R+=("!*"+Tok.split(P, ";")[0]+"*");
						try{
							if(!Tok.split(P, ";")[1].matches("([ ]*;*)*")) throw (new SemiException());
						}catch (ArrayIndexOutOfBoundsException e){}
						
					}
					else if (p.contains(";")){
						try{	
							String u=Tok.split(p, ";")[1].trim();
							if(!u.matches("([ ]*;*)*")) throw (new SemiException());
							else p=Tok.split(p, ";")[0].trim();
						}catch(ArrayIndexOutOfBoundsException e){p=Tok.split(p,";")[0].trim();}
					if(!Tools.isUpdateP(p)) throw (new UpdateException());
					R+=(p+"*");
					}
					else throw (new SemiException());
				}
				else {
					P=P.replaceFirst(p+"[ ]*,","");
					R+=(p+"*");
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){throw (new SemiException());}
		return R;
	}
	private String insert(String q) throws InsertException, NameException, ParantezException, ParameterException, SemiException {
		String R="I*";
		try{	
			if(q.contains("))")|| q.contains("((") || q.contains("()") || q.contains(")(")) throw (new ParantezException());
			String P;
			String [] c=Tok.split(q," "); 
			if(!c[1].equals("into")) throw (new InsertException());
			if(!N.isCorrect(c[2])) throw (new NameException());
			R+=(c[2]+"*");
			if(Tok.split(c[3],"(")[0].equals("parameter")){
				R+=("!*");
				if(c[3].contains("("));
				else if(!(c[4].charAt(0)=='(')) throw (new ParantezException()) ;
				P=Tok.split(q,"(")[1];
				if(!P.contains(")")) throw (new ParantezException());
				else P=Tok.split(P,")")[0];
				StringTokenizer w=new StringTokenizer(P);
				int i=0;
				while(w.hasMoreElements()){
					i++;
					P=w.nextToken(",").trim();
					if(!N.isCorrect(P)) throw (new NameException());
					R+=(P+"*");
				}
				R+=("!*");
				try{
					P=Tok.split(q,")")[1];
			
					if(!Tok.split(P,"(")[0].trim().equals("values")) throw (new InsertException());
				}catch (ArrayIndexOutOfBoundsException e){
					throw (new InsertException());
				}
				P=Tok.split(q,"(")[2];
				if(!P.contains(")")) throw (new ParantezException());
				else P=Tok.split(P,")")[0];
				int j=0;
				StringTokenizer ww=new StringTokenizer(P);
				while(ww.hasMoreElements()){
					j++;
					P=ww.nextToken(",").trim();
					R+=(P+"*");
				}
				if(j!=i) throw (new InsertException());
				P=Tok.split(q,")")[2];
				if(!P.matches("[ ]*;{1,}[ ]*")) throw (new SemiException());
				return R;
			}
			else if(Tok.split(c[3],"(")[0].equals("values")){
				if(c[3].contains("("));
				else if(!(c[4].charAt(0)=='(')) throw (new ParantezException()) ;
				P=Tok.split(q,"(")[1];
				if(!P.contains(")")) throw (new ParantezException());
				else P=Tok.split(P,")")[0];
				StringTokenizer w=new StringTokenizer(P);
				while(w.hasMoreElements()){
					P=w.nextToken(",").trim();
					R+=(P+"*");
				}
				P=Tok.split(q,")")[1];
				if(!P.matches("[ ]*;{1,}[ ]*")) throw (new SemiException());
				return R;
			}
			else throw (new InsertException());
		}catch(ArrayIndexOutOfBoundsException e){
			throw (new InsertException());
		}
	}
	private String dropTable(String q) throws SemiException, NameException {
		String R="P*";
		try{
			String [] c=Tok.split(q," ");
			if(N.isCorrect(Tok.split(c[1],";")[0].trim())){
				R+=(Tok.split(c[1],";")[0].trim()+"*");
				if(c[1].contains(";")){
					if(c[1].split(Tok.split(c[1],";")[0])[1].matches("[;]+") );
					else throw (new SemiException());
				}
				else if(c[2].matches(";+"));
				else throw (new SemiException());
			}
			else throw(new NameException());
		}catch(ArrayIndexOutOfBoundsException e){
			throw (new SemiException());
		}
		return R;
	}
	private String creatTable(String q) throws Exception {
		String R="C*";
		if (q.contains(",)")||q.contains("(,") || q.matches(".*,{2,}.*")) throw (new SyntaxError());
		if(q.contains("((")|| q.contains("))")) throw (new ParantezException());
		String P;
		try{	
			String [] c=Tok.split(q," ");
			if(!c[1].trim().equals("Table")) throw (new CreateException());
			else if(!c[2].contains("(")){
				if(c[3].trim().charAt(0)=='(' ){
					if (!N.isCorrect(c[2].trim())) throw (new NameException());
					R+=(c[2]+"*");
					P=Tok.split(q,"()")[1];
				}
				else throw (new ParantezException());
			}
			else if(!N.isCorrect(Tok.split(c[2], "(")[0])) throw (new NameException());
			else {
				R+=(Tok.split(c[2], "(")[0]+"*");
				P=Tok.split(q,"()")[1];
			}
			StringTokenizer w=new StringTokenizer(P);
			while(w.hasMoreElements()){
				P=w.nextToken(",").trim();
				try{
					if(!Tools.PaC(P)) throw (new ParameterException());
					R+=(P+"*");
				}catch(ArrayIndexOutOfBoundsException e){
					throw (new ParameterException());
				}
			}
			if(Tok.split(q,")")[1].trim().matches("[;]+")) ;//return q;
			else throw (new SemiException());
		}catch(ArrayIndexOutOfBoundsException e){
			throw (new SemiException());
		}
		return R;
	}
}
