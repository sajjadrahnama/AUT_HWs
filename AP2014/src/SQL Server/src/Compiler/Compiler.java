package Compiler;

import java.io.Serializable;
import java.util.Vector;

import CompileError.ParameterNumberException;
import IDE.Tok;
import IDE.Tools;

public class Compiler implements Serializable{
	
	private static final long serialVersionUID = 2246607427482892853L;
	public DataBase d;
	boolean isFinished=false;
	
	public Compiler(){
		this.d=new DataBase();
	}
	public String compile (String s)throws Exception{
		switch(s.charAt(0)){
		
			case 'e': isFinished=true;break;
			case 'C': return create(s);
			case 'P': return drop(s);
			case 'I':  return insert(s);
			case 'D': return delete(s);
			case 'U': return update(s);
			case 'S': return select(s);
		}
		return "خیلی ناراحت شدم که";
	}
	
	private String select(String s) throws Exception {
		s=Tools.charDelete(2,s);
		if(s.charAt(0)=='.'){
			s=Tools.charDelete(2,s);
			return (d.getTable(s.trim()).toString());
		}
		else {
			String res="";
			Vector<String> a=new Vector<String>();
			while (s.charAt(0)!='!'){
				res+=(Tok.split(s, "*")[0]+",");
				a.add(Tok.split(s, "*")[0]);
				s=Tools.charDelete(Tok.split(s, "*")[0].length()+1, s);
			}
			res=Tools.reverceCharDelete(1, res);
			res+="\n";
			s=Tools.charDelete(2, s);
			String name =Tok.split(s, "*")[0];
			s=Tools.charDelete(name.length()+1,s);
			String condition;
			if(Tok.split(s, "*")[0].length()>0) {
				s=Tools.charDelete(2,s);
				condition=Tok.split(s, "*")[0];
			}
			else condition=".";
			Table b=d.getTable(name);
			for(Record d: b.records){
				if(d.satisfyCondition(condition)){	
					for(String e: a){
						res+=(d.getParameter(e).getValue()+",");
					}
					res=Tools.reverceCharDelete(1, res);
					res+="\n";
				}
			}
			return res;
		}
	}
	public boolean isFinished(){
		return isFinished;
	}
	private String update(String s) throws Exception{
		String res="";
		s=Tools.charDelete(2, s);
		String name=Tok.split(s, "*")[0];
		s=Tools.charDelete(name.length()+1, s);
		String set=Tok.split(s, "!")[0];
		s=Tools.charDelete(set.length(), s);
		set=set.replace("=", " = ");
		Table a=d.getTable(name);
		if(s.length()<1){
			for(Record f :a.records){
				String t=f.update(set);
				if(t!=null) res+=(t+"\n");
			}
		}
		else{
			
			s=Tools.charDelete(2, s);
			s=Tok.split(s, "*")[0];
			for(Record f :a.records){
				if(f.satisfyCondition(s)) {
					String t=f.update(set);
					if(t!=null) res+=(t+"\n");
				}
			}
		}
		d.updateTable(a);
		return res;
	}
	private String create(String s) throws Exception{
		
		s=Tools.charDelete(2, s);
		String [] T=Tok.split(s, "*");
		String name =T[0];
		Table b=new Table(name);
		for (int i=1;i<T.length;i++){
			Parameter o;
			String[] t=Tok.split(T[i], " ");
			if(t.length==3) o=new Parameter(t[1], t[0], t[2],0);
			else o=new Parameter(t[1],t[0]);
			b.addParameter(o);
		}
	d.addTable(b);
	return "Table "+name+" has been created successfully!";
		
	}
	private String drop (String s) throws Exception {
		s=Tools.charDelete(2, s);
		String p=Tok.split(s, "*")[0];
		d.dropTable(p);
		return p +" has been droped !";
	}
	private String insert(String s)throws Exception {
		s=Tools.charDelete(2, s);
		String name =Tok.split(s, "*")[0];
		s=Tools.charDelete(name.length()+1, s);
		Table b=d.getTable(name);
		Record r=new Record();
		if(!(s.charAt(0)=='!')){
			String []t=Tok.split(s,"*");
			if(t.length!=b.Psize()) throw (new ParameterNumberException());
			for(int i=0;i<b.Psize();i++){
				Parameter p=b.getParameter(i);
				p.setValue(t[i]);
				r.addParameter(p);
			}
			b.addRecord(r);
			d.updateTable(b);
			return b.toString();
		}
		else{
			Table bb=d.getTable(name);
			Record rr=new Record();
			s=Tools.charDelete(2, s);
			String [] t=Tok.split(s,"*");
			Vector<String> v1=new Vector<String>();
			Vector<String> v2=new Vector<String>();
			boolean j=false;
			for(int i=0;i<t.length;i++){
				if(t[i].equals("!")){
					j=true;
					continue;
				}
				if(j) v2.add(t[i]);
				else v1.add(t[i]);
			}
			for(int i=0;i<v1.size();i++){
				
				bb.getParameter(v1.elementAt(i));
			}
			for(int i=0;i<bb.Psize();i++){
				if(Tools.StringIndexFinder(v1,bb.getParameter(i).getName())>=0) {
					int index=Tools.StringIndexFinder(v1,bb.getParameter(i).getName());
					Parameter p=bb.getParameter(i);
					p.setValue(v2.elementAt(index));
					//System.out.println(v2.elementAt(index));
					rr.addParameter(p);
				}
				else {
					Parameter p=bb.getParameter(i);
					p.setValue(null);
					
					rr.addParameter(p);
				}
			}
			
			bb.addRecord(rr);
			
			d.updateTable(bb);
			return bb.toString();
		}
	}
	private String delete(String s) throws Exception{
		s=Tools.charDelete(2, s);
		String name=Tok.split(s, "*")[0];
		s=Tools.charDelete(name.length()+1, s);
		Table b=d.getTable(name);
		if(s.charAt(0)=='.'){
			b.deleteRecords();
			d.updateTable(b);
			return "All Records of " +name +" deleted successfuly";
		}
		else{
			String condition =Tok.split(s, "*")[0];
			int i=b.deleteRecord(condition);
			return i+" Record deleted successfuly";
		}
		
	}
	public DataBase getDataBase(){
		return d;
	}
	public void setDataBase(DataBase a){
		this.d=a;
	}
	public static void main(String [] args) throws Exception{
	//	Recognizor i=new Recognizor();
	//	Compiler c =new Compiler();
	//c.compile(i.recognize("Create Table student (name String 20, id String 7,grade int 20,Dep String );"));
	//	c.compile(i.recognize("Create Table student1 (name1 String 20, id1 String 7,grade1 int 20,Dep1 String );"));

//		c.compile(i.recognize("Insert into student1 values(ahmad,9031053,10,CEIT);"));
//		c.compile(i.recognize("Insert into student values(sajjad,9231053,1,CEIT);"));
//		c.compile(i.recognize("Insert into student values(ahmad,9031053,10,CEIT);"));
//		c.compile(i.recognize("Insert into student1 values(sajjad,9231053,1,CEIT);"));
//		c.compile((i.recognize("Update from student set name=asghar Where name=sajjad and grade=1;")));
//		System.out.println(c.d.getTable("student").records.elementAt(0).getParameter("name").getValue());
	//	System.out.println(i.recognize("Select name, ID, APGrade from student where ID=2;;"));
	
	}
	
	
	
}
