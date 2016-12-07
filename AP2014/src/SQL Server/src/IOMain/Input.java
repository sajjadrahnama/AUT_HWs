package IOMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Vector;

public class Input implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2571713423540318595L;
	int FileLines=0;
	int nowLine=0;
	private File f;
	public Vector<String> querys=new Vector<String>();;
	public Input(String Fi) {
		f=new File (Fi);
		read(f);
	}
	private String getLine(){
		BufferedReader a=new BufferedReader(new InputStreamReader(System.in));
		try {
			return a.readLine();
		} catch (IOException e) {
			return "";
		}
	}
	private void read(File t){
		try {
			if (!t.isFile() || !t.canRead()) throw (new FileNotFoundException());
			BufferedReader ee=new BufferedReader(new FileReader(t));
			int i=0;
			while(i==0){
				querys.addElement(ee.readLine());
				if(querys.elementAt(querys.size()-1)==null){
					querys.remove(querys.size()-1);
					i=1;
				}
			}
			ee.close();
		}
		catch (FileNotFoundException e) {
			FileLines=0;
			nowLine=0;
			System.out.println("Starts witout File");
		}
		catch(IOException e1){}
		FileLines=querys.size()+1;
		nowLine=querys.size();
	}
	public String resPath(){
		return f.getPath();
	}
	public String getCom(){
		FileLines--;
		if(nowLine>0){
			this.nowLine --;
			String r=querys.elementAt(0);
			querys.remove(0);
			return r;
		}
		else {
			return this.getLine();
		}
	}
	public boolean readingfromfile(){
		if(FileLines>0) return true;
		else return false;
	}
}
