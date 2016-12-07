package event;

import java.awt.Component;
import java.awt.event.ComponentEvent;

public class getImageToShowEvent extends ComponentEvent {

	private static final long serialVersionUID = 1L;
	private int imagetoshow;
	public getImageToShowEvent(Component arg0,int imagetoshow) {
		super(arg0, MyEvent.getImagetoShow);
		this.imagetoshow=imagetoshow;
	
	}
	public int getImagetoshow() {
		return imagetoshow;
	}

}
