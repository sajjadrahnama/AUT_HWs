package main;


public class Shared {
	private int index;
	private boolean isEmpty;
	private int [] shared;
	public Shared() {
		isEmpty=true;
		shared=new int [4];
		index=0;
	}
	public synchronized int getInt(){
		while(isEmpty){
			try {
				notify();
				wait(5);
			} catch (InterruptedException e) {}
		}
		int i=shared[index];
		for(int j =index;j<3;j++) shared[j]=shared[j+1];
		index--;
		if(index==0) isEmpty=true;
		notify();
		return i;
	}
	public synchronized void setInt(int i){
		while(index==3){
			try {
				notify();
				wait(5);
			} catch (InterruptedException e) {}
		}
		shared[index+1]=i;
		index++;
		isEmpty=false;
	}
	public static void main (String [] args){
		Shared s=new Shared();
		Producer p1=new Producer(s, 1);
		Producer p2=new Producer(s, 2);
		Producer p3=new Producer(s, 3);
		Producer p4=new Producer(s, 4);
		Consumer c1=new Consumer(s);
		Consumer c2=new Consumer(s);
		Consumer c3=new Consumer(s);
		Consumer c4=new Consumer(s);
		p1.start();
		p2.start();
		c1.start();
		c2.start();
		p3.start();
		c3.start();
		p4.start();
		c4.start();
		
	}
}
