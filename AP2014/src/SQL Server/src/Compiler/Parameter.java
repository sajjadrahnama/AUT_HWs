package Compiler;

import java.io.Serializable;

import CompileError.MaxValueException;
import CompileError.ParameterTypeMissmatch;

public class Parameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type,name,value,maxV;
	private boolean isLimited=false;
	public Parameter(String type,String name,String value) throws Exception{
		this(type, name);
		this.value=value;
		if(type.equals("int")){
			try{
				Integer.parseInt(value);
			}catch(NumberFormatException e){
				throw (new ParameterTypeMissmatch());
			}
		}
		else if(type.equals("float")){
			try{
				Float.parseFloat(value);
			}catch(NumberFormatException e){
				throw (new ParameterTypeMissmatch());
			}
		}
	}
	public String toString()
	{
		String res = "" ;
		res +=  value  ;
		return res ;
	}
	public Parameter(String type,String name) {
		this.type=type;
		this.name=name;
	}
	public Parameter(String type,String name,String maxV,int a) throws Exception {///int a baraye motefavet boodan type ha ba oon yeki constractor
		
		this(type, name);
		isLimited=true;
		this.maxV=maxV;
		if(type.equals("int")){
			try{
				Integer.parseInt(maxV);
			}catch(NumberFormatException e){
				throw (new ParameterTypeMissmatch());
			}
		}
		else if(type.equals("float")){
			try{
				Float.parseFloat(maxV);
			}catch(NumberFormatException e){
				throw (new ParameterTypeMissmatch());
			}
		}
		
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public String getMaxV() {
		if (this.isLimited) return maxV;
		else return "";
	}
	public boolean isLimited() {
		return isLimited;
	}
	public void setValue(String v) throws Exception{
		if(v==null){ 
			this.value=null;
			return;
		}
		if(type.equals("int")){
			try{
				Integer.parseInt(v);
				if(this.isLimited){
					if(Integer.parseInt(v)> Integer.parseInt(maxV)) throw (new MaxValueException());
				}
				this.value=v;
			}catch(NumberFormatException e){
				throw (new ParameterTypeMissmatch());
			}
		}
		else if(type.equals("float")){
			try{
				Float.parseFloat(v);
				if(this.isLimited){
					if(Float.parseFloat(v)> Float.parseFloat(maxV)) throw (new MaxValueException());
				}
				this.value=v;
			}catch(NumberFormatException e){
				throw (new ParameterTypeMissmatch());
			}
		}
		else {
			if(this.isLimited){
				if(v.length()>Integer.parseInt(maxV)) throw (new MaxValueException());
			}
			this.value=v;
		}
	}
}
