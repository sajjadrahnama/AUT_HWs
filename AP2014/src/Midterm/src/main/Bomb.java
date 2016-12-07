package main;



public class Bomb extends Thread{
	int x , y;
	private boolean stopflag=true;
	private long speed;
	public Bomb(int x, int y,GamePanel parent,int speed) {
		this.speed=speed;
		this.x=x;
		this.y=y;
	}
	@Override
	public void run() {
		while (stopflag){
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {}
			move();
		}
		
	}
	public void setStopflag(boolean s) {
		this.stopflag = s;
	}
	private void move() {
		y++;
	}
	public int getXx() {
		return x;
	}
	public int getYy() {
		return y;
	}
	
}
