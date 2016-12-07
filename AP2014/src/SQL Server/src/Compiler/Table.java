package Compiler;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Vector;

import CompileError.ParameterNotFoundException;
import CompileError.PrameterDuplicateException;

public class Table implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6649822670559962801L;
	private Vector<Parameter> parameters;
	Vector <Record> records;
	private String name ;
	
	public Table (String name ){
		this.name =name ;
		parameters=new Vector<Parameter>();
		records = new Vector<Record>();
	}
	public Parameter getParameter(String name) throws Exception{
		for(Parameter s: parameters){
			if(s.getName().equals(name)) 
			{
				Parameter tmp = new Parameter(s.getType(), s.getName()) ;
				return tmp;
			}
		}
		throw (new ParameterNotFoundException());
	}
	public String getName(){
		return this.name;
	}
	public void addParameter(Parameter s) throws Exception{
		int o=0;
		for(Parameter F:parameters){
			if(F.getName().equals(s.getName())) o=1;
		}
		if(o==0) parameters.add(s);
		else throw (new PrameterDuplicateException());
	}
	public Parameter getParameter(int i) throws Exception{
		try{
			Parameter tmp = new Parameter(parameters.elementAt(i).getType(), parameters.elementAt(i).getName()) ;
			return tmp;
		}catch(ArrayIndexOutOfBoundsException e ){
			throw (new ParameterNotFoundException());
		}
	}
	public void addRecord(Record A){
		records.add(A);
	}
	public int Psize(){
		return parameters.size();
	}
	public int Rsize(){
		return records.size();
	}
	public void deleteRecords(){
		this.records=new Vector<Record>();
	}
	
	public int deleteRecord(String condition)throws Exception {
		Vector<Integer> a=new Vector<Integer>();
		for(int j=0;j<records.size();j++){
			if(records.elementAt(j).satisfyCondition(condition)) {
				a.add(j);
			}
		}
		Integer [] aa=new Integer[a.size()];
		a.toArray(aa);
		Arrays.sort(aa);
		for(int j=aa.length-1;j>=0;j--){
			records.removeElementAt(aa[j]);
		}
		return a.size();
	}
	@Override
	public String toString (){
		String res="Table\t"+name;
		for (Record s: records){
			res+=("\n"+s);
		}
		res+="\n----------------------------------------";
		return res;
	}
	
}
