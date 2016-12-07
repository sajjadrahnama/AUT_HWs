package main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Browser extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private WebPanel wb;
	private JComboBox<String> jco;
	private JButton b;
	private JLabel l;
	JScrollPane jsp;
	public Browser() {
		
		setSize(800,650);
		setResizable(false);
		setLocation(50,50);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		for(LookAndFeelInfo i: UIManager.getInstalledLookAndFeels()){
			if(i.getName().equals("Nimbus"))
				try {
					UIManager.setLookAndFeel(i.getClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {}
		}
		
		jco= new JComboBox<String>();
		jco.setSize(700, 30);
		jco.setLocation(10,40);
		jco.setEditable(true);
		jco.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER) go();
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		getContentPane().add(jco);
		
		l= new JLabel("Java Browser");
		l.setSize(100,30);
		l.setLocation(330,5);
		getContentPane().add(l);
		
		b=new JButton("GO");
		b.setSize(60,30);
		b.setLocation(720,40);
		getContentPane().add(b);
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				go();
				
			}
		});
		
		wb= new WebPanel(this);
		jsp= new JScrollPane(wb);
		jsp.setSize(wb.getSize());
		jsp.setLocation(wb.getLocation());
		getContentPane().add(jsp);
		
		setVisible(true);
	}
	void go(){
		String a=((JTextField)jco.getEditor().getEditorComponent()).getText();
		if (a!=null && a.length()>0 ){
			String f=null;
			try{
				f=a.substring(0,7);
			}catch(StringIndexOutOfBoundsException e){
				a="https://"+a;
			}
			if(!f.equalsIgnoreCase("http://")) a="http://"+a;
			boolean flag=true;
			for (int i =0 ; i<jco.getItemCount();i++) if (a.equals(jco.getItemAt(i))) flag=false;
			if(flag)jco.addItem(a);
			wb.dispatchEvent(new GoEvent(this, a));
		}
	}
	public static void main(String[] args) {
			new Browser();
		

	}
	public void settext(String url) {
		
		((JTextField)(jco.getEditor().getEditorComponent())).setText(url);
		
	}
	public void addtobox(String url) {
		jco.addItem(url);
		
	}

}
