package IOMain;

import java.io.File;

import CompileError.CompileError;
import Compiler.Compiler;
import Compiler.DataBase;
import IDE.Recognizor;
import SyntaxError.SyntaxError;

public class Main {

	public static void main(String[] args) throws Exception {
		Input input=new Input(args[0]);
		File dataBase=new File("DataBase");
		Output o=new Output(args[0]+"res");
		Recognizor i=new Recognizor();
		Compiler C=new Compiler();
		
		if(dataBase.isFile()) {
			DataBase e= (DataBase)o.readObject(dataBase);
			C.setDataBase(e);
		}
		while (!C.isFinished()){
			try {
				o.output(C.compile(i.recognize(input.getCom())),input.readingfromfile());
			} catch (CompileError e) {o.output(e.massage(),input.readingfromfile());}
			catch (SyntaxError ee) {o.output(ee.massage(),input.readingfromfile());}
			catch (Exception e) {}
		}
		o.writeObject(C.getDataBase(), dataBase);
	}

}
