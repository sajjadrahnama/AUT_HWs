package main;

import event.addBombEvent;


public class Airplane extends Thread{
	private int x,y;
	private GamePanel parent;
	private Bomb bomb1,bomb2,bomb3;
	private  boolean b1=true,b2=true,b3=true;
	private int speed;
	private boolean alive;
	public Airplane(int y,int x,GamePanel parent,int speed) {
		this.alive = true;
		bomb1= new Bomb(105+(int)(Math.random()*400), y, parent,speed);
		bomb2= new Bomb(105+(int)(Math.random()*400), y, parent,speed);
		bomb3= new Bomb(105+(int)(Math.random()*400), y, parent,speed);
		this.speed=speed;
		this.parent=parent;
		this.y=y;
		this.x=x;
	}
	private void move(){
		x++;
		if(x>810) {
			bomb1= new Bomb(105+(int)(Math.random()*400), y, parent,speed);
			bomb2= new Bomb(105+(int)(Math.random()*400), y, parent,speed);
			bomb3= new Bomb(105+(int)(Math.random()*400), y, parent,speed);
			b1=true;
			b2=true;
			b3=true;
			bomb2.setStopflag(true);
			x=-200;
		}
	}
	@Override
	public void run() {
		while (alive){
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {}
			if(bomb2.getXx()==x && b2) {
				parent.dispatchEvent(new addBombEvent(parent,bomb2));
				b2=false;
			}
			if(bomb1.getXx()==x && b1) {
				b1=false;
				parent.dispatchEvent(new addBombEvent(parent,bomb1));
			}
			if(bomb3.getXx()==x && b3) {
				b3=false;
				parent.dispatchEvent(new addBombEvent(parent,bomb3));
			}
			move();
			//parent.repaint();
		}
	}
	public int getXx(){
		return x;
	}
	public int getYy(){
		return y;
	}
	public void setalive(boolean b) {
		alive = b;
		
	}
}
