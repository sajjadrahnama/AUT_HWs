package Pakage1;

import java.util.Vector;

public class Set<Type> extends Vector <Type> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int i,j,k,l;
	public void ad(Type A){
		if (!this.contains(A)) this.add(A);
	}
	public boolean isMember(Type A){
		if (this.contains(A)) return true;
		else return false;
	}
	public void display() {
		System.out.print("{ ");
		if(this.size()>0) System.out.print(this.elementAt(0));
		for (i=1;i<this.size();i++){
			System.out.print(" , " + this.elementAt(i));
		}
		System.out.println(" }");
	}
	public int setSize(){
		return this.size();
	}
	public void intersection(Set<Type> A){
		for (i=0;i<this.size();i++){
			if (!A.contains(this.elementAt(i)))this.remove(i);
		}
	}
	public void union(Set<Type> A){
		
		for (i=0;i<A.size();i++){
			if(!this.contains(A.elementAt(i))) this.add(A.elementAt(i));
		}
	}
	public void mines(Set<Type> A){
		for(i=0;i<this.size();i++){
			if(A.isMember(this.elementAt(i))) this.remove(i);
		}
	}
	public static void main(String[] args) {
		System.out.print("a".compareTo("b"));
	}

}
