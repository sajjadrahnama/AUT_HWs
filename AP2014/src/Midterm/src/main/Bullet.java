package main;

public class Bullet extends Thread{
	private int x;
	private int y;
	private int deg;
	boolean chap;
	private boolean alive;
	public Bullet(int x , int y, int deg,boolean chap,GamePanel parnet) {
		this.chap=chap;	
		this.x=x;
			this.y=y;
			this.deg=deg;
			alive =true;
	}
	public void move (){
		if(chap) x-=(int)(Math.cos(Math.toRadians(deg))*8);
		else x+=(int)(Math.cos(Math.toRadians(deg))*8);
		y+=(int)(Math.sin(Math.toRadians(deg))*8);
		
	}
	@Override
	public void run() {
		while (alive){
			move();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
			//parnet.repaint();
		}
	}
	public int getXx() {
		return x;
	}
	public int getYy() {
		return y;
	}
	public void setXx(int x) {
		this.x = x;
	}
	public void setYy(int y) {
		this.y = y;
	}
	public void setAlive(boolean b){
		alive =b;
	}
}

