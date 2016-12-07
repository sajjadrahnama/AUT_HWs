package Compiler;

import java.io.Serializable;
import java.util.Vector;

import CompileError.TableDuplicateException;
import CompileError.TableNotFounException;

public class DataBase implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4191967657779808096L;
	private Vector<Table> tables;
	public DataBase(){
		tables=new Vector<Table>();
	}
	public void  addTable(Table S) throws Exception {
		for (Table q:tables){
			if(S.getName().equals(q.getName())) throw (new TableDuplicateException());
		}
		tables.add(S);
	}
	public Table getTable(String name) throws Exception{
		Table a=new Table("");
		for (Table s:tables){
			if(s.getName().equals(name)) {
				a=s;
				return a;
			}
		}
		throw (new  TableNotFounException());
	}
	public void updateTable(Table q) throws Exception{
		int i=0;
		for(int j=0;j<tables.size();j++){
			if(tables.elementAt(j).getName().equals(q.getName())) {
				tables.removeElementAt(j);
				tables.add(q);
				i=1;
			}
		}
		if(i==0) throw (new TableNotFounException());
	}
	public void dropTable (String name) throws Exception{
		int i=0;
		for (Table s: tables){
			if(s.getName().equals(name)){
				i=1;
			}
		}
		if(i==0) throw (new TableNotFounException());
		else tables.remove(this.getTable(name));
	}
	

	
	
	
	public int size(){
		return tables.size();
	}
}
