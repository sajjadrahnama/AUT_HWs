package main;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class WebPanel extends JEditorPane {
	
	private static final long serialVersionUID = 1L;
	public static final int GO = AWTEvent.RESERVED_ID_MAX+1;;
	private Browser parent;
	public WebPanel(Browser parent) {
		this.parent=parent;
		setBackground(Color.white);
		try {
			setPage("http://www.google.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setEditable(false);
		setBorder(BorderFactory.createEtchedBorder(Color.blue, Color.gray));
		setSize(770,530);
		setLocation(10,80);
		setForeground(Color.red);
		addHyperlinkListener(new HyperlinkListener() {
			
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				// TODO Auto-generated method stub
				if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					go(e.getURL().toString());
					WebPanel.this.parent.settext(e.getURL().toString());
					WebPanel.this.parent.addtobox(e.getURL().toString());
				}
			}
		});
	}
	@Override
	protected void processComponentEvent(ComponentEvent arg0) {
		if(arg0.getID()==GO){
			go(((GoEvent)arg0).getUrl());
			WebPanel.this.parent.settext(((GoEvent)arg0).getUrl());
			
		}
		super.processComponentEvent(arg0);
	}
	private void go(String url2) {
		try {
			setPage(url2);
		} catch (IOException e) {e.printStackTrace();}
		
	}
}
