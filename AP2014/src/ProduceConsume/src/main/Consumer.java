package main;

public class Consumer extends Thread{

	private Shared parent;
	private int sum;
	public Consumer(Shared parent) {
		this.parent=parent;
		sum=0;
	}
	@Override
	public void run() {
		for(int i =0 ;i<10 ; i++){
			sum+=parent.getInt();
		}
		System.out.println(sum);
	}

}
