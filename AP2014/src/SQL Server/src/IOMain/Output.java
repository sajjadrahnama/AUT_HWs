package IOMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Compiler.DataBase;

public class Output implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1203370774245954305L;
	private File f;
	String FileString="";
	public Output(String s){
		f=new File(s);
	}
	public void output(String p,boolean n){
		if(n){	
			FileString+=p;
			try {
				f.createNewFile();
				
			} catch (IOException e) {}
			try {
				FileWriter w=new FileWriter(f);
				w.write(FileString);
				w.close();
			} catch (FileNotFoundException e) {}catch(IOException ee){}
			FileString+=";\n";
		}
		else System.out.println(p);
	}
	public DataBase readObject(File dataBase) {
		try{
			FileInputStream e=new FileInputStream(dataBase);
			ObjectInputStream ee=new ObjectInputStream(e);
			DataBase a=(DataBase) ee.readObject();
			ee.close();
			return a;
		}catch(ClassNotFoundException e){}
		catch(IOException e){}
		return null;
	}
	public void writeObject(Object o,File dataBase){
		try{
			FileOutputStream e=new FileOutputStream(dataBase);
			ObjectOutputStream ee=new ObjectOutputStream(e);
			ee.writeObject(o);
			ee.close();
			
		}catch(IOException e){}
	}

}
