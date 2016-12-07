package Pakage1;

import java.util.Vector;

public class MyVector<T> extends Vector<T> implements Sortable<T> {
	private static final long serialVersionUID = 1L;
	@Override
	public void sortedInsert(T element) {
		int i;
		for (i=0;i<size();i++){
			if(((Comparable<T>) element).compareTo(get(i))<0){
				this.add(i,element);
				i=-1;
				break;
			}
		}
		if(i!=-1) add(element);
	}
}
