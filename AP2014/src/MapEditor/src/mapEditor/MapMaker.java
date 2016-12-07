package mapEditor;

import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import event.MyEvent;
import event.SetMapEvent;
import event.SetMapToShow;

public class MapMaker extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private int YPos = 130;
	private int XPos = 135;
	public ToolPanel toolPanel;
	private MapPanel mapPanel;
	private int[][] map;
	private ManagePanel m;

	public MapMaker() {
		for (LookAndFeelInfo inf: UIManager.getInstalledLookAndFeels()){
			if("Nimbus".equals(inf.getName()))
				try {
					UIManager.setLookAndFeel(inf.getClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
		}
		setResizable(false);
		map = new int[300][300];
		map = loadMap("def");
		toolPanel = new ToolPanel(this);
		mapPanel = new MapPanel(this, toolPanel);
		m = new ManagePanel(this);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1026, 647);
		setLocation(50, 50);
		
		
		getContentPane().add(mapPanel);
		getContentPane().add(toolPanel);
		getContentPane().add(m);
		setVisible(true);

	}
	private int[][] loadMap(String file) {
		
		int[][] m = new int[300][300];
		File a = new File(file);
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(a));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Map Not Found");
			return map;
		}

		for (int i = 0; i < 300; i++) {
			String s = null;
			try {
				s = b.readLine();
			} catch (IOException e) {
			}
			for (int j = 0; j < 300; j++)
				m[i][j] = s.charAt(j)-48;
		}
		try {
			b.close();
		} catch (IOException e) {
		}
		return m;
	}
	private void saveMap(String file) {
		File f = new File(file);
		BufferedWriter b = null;
		try {
			f.createNewFile();
			b = new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
		}
		for (int i = 0; i < 300; i++) {
			for (int j = 0; j < 300; j++) {
				try {
					b.write(map[i][j] + 48);
				} catch (IOException e) {
				}
			}
			try {
				b.write('\n');
			} catch (IOException e) {
			}
		}
		try {
			b.close();
		} catch (IOException e) {
		}
		JOptionPane.showMessageDialog(this,"Map Saved");
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	private int[][] getMapToShow() {
		int[][] res = new int[40][30];
		for (int i = 0; i < 40; i++)
			for (int j = 0; j < 30; j++) {
				res[i][j] = map[this.XPos + i][this.YPos + j];
			}
		return res;
	}
	public static void main(String[] args) {
		new MapMaker();

	}
	private void XPosIncrease(int i) {
		XPos += i;
		if (XPos > 259)
			XPos = 259;

	}
	private void YPosIncrease(int i) {
		YPos += i;
		if (YPos > 269)
			YPos = 269;
	}
	private void XPosDecrease(int i) {
		XPos -= i;
		if (XPos < 0)
			XPos = 0;
	}
	private void YPosDecrease(int i) {
		YPos -= i;
		if (YPos < 0)
			YPos = 0;
	}
	private void setMap(int xPos2, int yPos2, int newPaint) {
		this.map[this.XPos + xPos2][this.YPos + yPos2] = newPaint;

	}
	@Override
	protected void processComponentEvent(ComponentEvent arg0) {
		if(arg0.getID()==MyEvent.getImagetoShow) mapPanel.dispatchEvent(arg0);
		else if(arg0.getID()==MyEvent.xinc) XPosIncrease(1);
		else if(arg0.getID()==MyEvent.xdec) XPosDecrease(1);
		else if(arg0.getID()==MyEvent.yinc) YPosIncrease(1);
		else if(arg0.getID()==MyEvent.ydec) YPosDecrease(1);
		else if(arg0.getID()==MyEvent.setmap) {
			SetMapEvent e=(SetMapEvent)arg0;
			setMap(e.getXpos2(),e.getYpos2(), e.getNewpaint());
		}
		else if(arg0.getID()==MyEvent.getmap){
			mapPanel.dispatchEvent(new SetMapToShow(this,getMapToShow()));
		}
		else if(arg0.getID()==MyEvent.cs){
			mapPanel.dispatchEvent(arg0);
		}
		else if(arg0.getID()==MyEvent.lo){
			String file=JOptionPane.showInputDialog("Enter Map Name");
			if(file!=null && file.length()>0) {
				map=loadMap(file);
				mapPanel.dispatchEvent(new MyEvent(this,MyEvent.up));
			}
		}
		else if(arg0.getID()==MyEvent.sv){
			String file=JOptionPane.showInputDialog("Enter Map Name");
			if (file==null) ;
			else if(file.length()==0) JOptionPane.showMessageDialog(this, "Invalid Name!");
			else saveMap(file);
		}
		else if(arg0.getID()==MyEvent.setFocus){
			m.dispatchEvent(arg0);
		}
		else super.processComponentEvent(arg0);
	}

}
