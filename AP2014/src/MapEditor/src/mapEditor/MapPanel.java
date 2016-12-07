package mapEditor;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import event.GetMapToShow;
import event.MyEvent;
import event.SetMapEvent;
import event.SetMapToShow;
import event.getImageToShowEvent;

public class MapPanel extends JPanel implements MouseMotionListener{
	private int [][] mapToShow;
	private static final long serialVersionUID = 1L;
	private ImageIcon [] icons;
	private MapMaker parent; 
	private int imageToPaint=0;
	public MapPanel( MapMaker parent ,ToolPanel t) {
		addMouseMotionListener(this);
		setSize(820,620);
		setLocation(0,0);
		setVisible(true);
		setLayout(null);
		setOpaque(true);
		icons = new ImageIcon[8];
		createMapIcons(1);
		this.parent=parent;
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg) {
				int XPos = (arg.getX()-12)/40;
				int YPos = (arg.getY()-12)/40;
				MapPanel.this.parent.dispatchEvent(new SetMapEvent(MapPanel.this,XPos,YPos,imageToPaint));
				MapPanel.this.parent.dispatchEvent(new GetMapToShow(MapPanel.this));
				MapPanel.this.update(MapPanel.this.getGraphics());
				MapPanel.this.parent.dispatchEvent(new MyEvent(MapPanel.this,MyEvent.setFocus));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg) {
				
			}
		});
		setFocusable(true);
		addKeyListener(new KeyListener() {
			
			
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				MapPanel.this.moveMap(arg0.getKeyCode());
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void createMapIcons(int i) {
		icons[0]=new ImageIcon(i+"map0.jpg");
		icons[1]=new ImageIcon(i+"map1.jpg");
		icons[2]=new ImageIcon(i+"map2.jpg");
		icons[3]=new ImageIcon(i+"map3.jpg");
		icons[4]=new ImageIcon(i+"map4.jpg");
		icons[5]=new ImageIcon(i+"map5.jpg");
		icons[6]=new ImageIcon(i+"map6.jpg");
		icons[7]=new ImageIcon(i+"map7.jpg");
		
	}
	@Override
	public void paint(Graphics g) {
		parent.dispatchEvent(new GetMapToShow(this));
		for (int i=0;i<20;i++)
			for(int j=0;j<15;j++){
				g.drawImage(icons[0].getImage(), i*40+10, j*40+10, null);
			}
		for (int i=0;i<20;i++)
			for(int j=0;j<15;j++){
				g.drawImage(icons[mapToShow[i][j]].getImage(), i*40+10, j*40+10, null);
			}
		
	}
	private void myRepaint(){
		super.paint(this.getGraphics());
		for (int i=0;i<20;i++)
			for(int j=0;j<15;j++){
				this.getGraphics().drawImage(icons[0].getImage(), i*40+10, j*40+10, null);
			}
		for (int i=0;i<20;i++)
			for(int j=0;j<15;j++)
				this.getGraphics().drawImage(icons[mapToShow[i][j]].getImage(), i*40+10, j*40+10, null);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		while (MouseInfo.getPointerInfo().getLocation().getY()-parent.getY()-27<20 && MouseInfo.getPointerInfo().getLocation().getY()-parent.getY()-27>0){
			long l=System.currentTimeMillis();
			while(System.currentTimeMillis()-l<250){}
			parent.dispatchEvent(new MyEvent(this,MyEvent.ydec));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
		while (MouseInfo.getPointerInfo().getLocation().getY()-parent.getY()-parent.getHeight()+12<10 && MouseInfo.getPointerInfo().getLocation().getY()-parent.getY()-parent.getHeight()+12 >0){
			long l=System.currentTimeMillis();
			while(System.currentTimeMillis()-l<250){}
			parent.dispatchEvent(new MyEvent(this,MyEvent.yinc));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
		while (MouseInfo.getPointerInfo().getLocation().getX()-parent.getX()-3<10 && MouseInfo.getPointerInfo().getLocation().getX()-parent.getX()-3>0){
			long l=System.currentTimeMillis();
			while(System.currentTimeMillis()-l<250){}
			parent.dispatchEvent(new MyEvent(this,MyEvent.xdec));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
		while (parent.getX()+this.getWidth()- MouseInfo.getPointerInfo().getLocation().getX()>0 && parent.getX()+this.getWidth()- MouseInfo.getPointerInfo().getLocation().getX()<10){
			long l=System.currentTimeMillis();
			while(System.currentTimeMillis()-l<250){}
			parent.dispatchEvent(new MyEvent(this,MyEvent.xinc));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}
	private void moveMap(int code){
		if (code==KeyEvent.VK_UP){
			parent.dispatchEvent(new MyEvent(this,MyEvent.ydec));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
		if (code==KeyEvent.VK_DOWN){
			parent.dispatchEvent(new MyEvent(this,MyEvent.yinc));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
		if (code==KeyEvent.VK_LEFT){
			parent.dispatchEvent(new MyEvent(this,MyEvent.xdec));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
		if (code==KeyEvent.VK_RIGHT){
			parent.dispatchEvent(new MyEvent(this,MyEvent.xinc));
			parent.dispatchEvent(new GetMapToShow(this));
			update(this.getGraphics());
			
		
		}
	}
	@Override
	protected void processComponentEvent(ComponentEvent e) {
		if(e.getID()==MyEvent.getImagetoShow){
			getImageToShowEvent r=(getImageToShowEvent)e;
			imageToPaint=r.getImagetoshow();
		}
		else if(e.getID()==MyEvent.setmaptoshow){
			SetMapToShow ee= (SetMapToShow)e;
			mapToShow=ee.getMap();
		}
		else if(e.getID()==MyEvent.cs){
			createMapIcons(((MyEvent)e).getSes());
			myRepaint();
		}
		else if(e.getID()==MyEvent.lo){
			parent.dispatchEvent(e);
			parent.dispatchEvent(new GetMapToShow(this));
		}
		else if(e.getID()==MyEvent.sv){
			parent.dispatchEvent(e);
		}
		else if(e.getID()==MyEvent.up){
			parent.dispatchEvent(new GetMapToShow(this));
			this.myRepaint();
		}
		super.processComponentEvent(e);
	}
}
