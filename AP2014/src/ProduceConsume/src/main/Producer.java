package main;

public class Producer extends Thread{
	Shared s;
	int p;
	public Producer(Shared s,int p) {
		this.s=s;
		this.p=p;
	}
	@Override
	public void run() {
		for(int i = 0;i<10;i++){
			s.setInt(p);
		}
	}
	
}
