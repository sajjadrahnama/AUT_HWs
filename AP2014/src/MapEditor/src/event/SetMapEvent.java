package event;

import java.awt.Component;
import java.awt.event.ComponentEvent;

public class SetMapEvent extends ComponentEvent {
	private static final long serialVersionUID = 1L;
	private int xpos2,ypos2,newpaint;
	public SetMapEvent(Component arg0,int xpos2,int ypos2,int newpaint) {
		super(arg0,MyEvent.setmap);
		this.xpos2=xpos2;
		this.ypos2=ypos2;
		this.newpaint=newpaint;
	}
	public int getXpos2() {
		return xpos2;
	}
	public int getYpos2() {
		return ypos2;
	}
	public int getNewpaint() {
		return newpaint;
	}

}
