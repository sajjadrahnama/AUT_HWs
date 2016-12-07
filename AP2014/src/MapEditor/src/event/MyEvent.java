package event;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.ComponentEvent;

public class MyEvent extends ComponentEvent {

	
	private static final long serialVersionUID = 1L;
	public static final int cs = AWTEvent.RESERVED_ID_MAX+4;
	public static final int up = AWTEvent.RESERVED_ID_MAX+3;
	public static final int sv= AWTEvent.RESERVED_ID_MAX+1;
	public static final int lo= AWTEvent.RESERVED_ID_MAX+2;
	public static final int xinc = AWTEvent.RESERVED_ID_MAX+5;
	public static final int xdec = AWTEvent.RESERVED_ID_MAX+6;
	public static final int yinc = AWTEvent.RESERVED_ID_MAX+7;
	public static final int ydec = AWTEvent.RESERVED_ID_MAX+8;
	public static final int setmap= AWTEvent.RESERVED_ID_MAX+9;
	public static final int getmap= AWTEvent.RESERVED_ID_MAX+10;
	public static final int setmaptoshow= AWTEvent.RESERVED_ID_MAX+11;
	public static final int getImagetoShow= AWTEvent.RESERVED_ID_MAX+12;
	public static final int setFocus= AWTEvent.RESERVED_ID_MAX+13;
	
	private int ses=1;
	public MyEvent(Component src, int ID) {
		super(src, ID);
	}
	public MyEvent(Component src, int ID,int ses) {
		super(src, ID);
		this.ses=ses;
	}
	public int getSes() {
		return ses;
	}

}
