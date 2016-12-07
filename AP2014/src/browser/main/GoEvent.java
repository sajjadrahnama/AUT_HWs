package main;

import java.awt.Component;
import java.awt.event.ComponentEvent;

public class GoEvent extends ComponentEvent {

	private String url;
	public GoEvent(Component arg0,String url) {
		super(arg0, WebPanel.GO);
		this.url=url;
	}
	public String getUrl() {
		return url;
	}
	private static final long serialVersionUID = 1L;
	
}
