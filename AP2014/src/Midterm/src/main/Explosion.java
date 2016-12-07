package main;


public class Explosion {
	int x , y;
	int show;
	public Explosion(int x , int y) {
		this.x=x;
		this.y=y;
		show=60;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void decShow(){
		show--;
	}
	public int getShow(){
		return show;
	}
		
}
