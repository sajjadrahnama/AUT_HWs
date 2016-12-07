package event;

import java.awt.Component;
import java.awt.event.ComponentEvent;

public class SetMapToShow extends ComponentEvent{

	private static final long serialVersionUID = 1L;
	private int [][] map;
	public SetMapToShow(Component arg0,int [][] m) {
		super(arg0,MyEvent.setmaptoshow);
		this.map=m;
	}
	public int[][] getMap(){
		return map;
	}
	

}
