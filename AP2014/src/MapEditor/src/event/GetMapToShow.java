package event;

import java.awt.Component;
import java.awt.event.ComponentEvent;

public class GetMapToShow extends ComponentEvent {
	private static final long serialVersionUID = 1L;

	public GetMapToShow(Component arg0) {
		super(arg0, MyEvent.getmap);
	}

}
