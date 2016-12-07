package event;

import java.awt.Component;
import java.awt.event.ComponentEvent;

import main.Bomb;

public class addBombEvent extends ComponentEvent {

	private Bomb b;
	private static final long serialVersionUID = 1L;
	public addBombEvent(Component arg0,Bomb b) {
		super(arg0, MyEvent.addBomb);
		this.b=b;
	}
	public Bomb getBomb(){
		return b;
	}

}
