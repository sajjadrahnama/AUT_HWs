package mainpackage;
 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MasterForm extends JFrame implements ActionListener{
	

	private static final long serialVersionUID = 9199117147216487478L;
	
	MyTextField f1,f2,f3;
	JLabel l1,l2,l3;
	JButton ok,exit;
	Board a;
	

	public MasterForm(String arg0, int w, int h) {
		super(arg0);
		Dimension d = getToolkit().getScreenSize();
		setSize(w,h);
		setLocation((d.width-w)/2,(d.height-h)/2);
		setLayout(null);
		
		l1=new JLabel("Rows :");
		l1.setSize(80,40);
		l1.setLocation(100,40);
		getContentPane().add(l1);
		
		l2=new JLabel("Columns :");
		l2.setSize(80,40);
		l2.setLocation(100,100);
		getContentPane().add(l2);
		
		l3=new JLabel("Mines :");
		l3.setSize(80,40);
		l3.setLocation(100,160);
		getContentPane().add(l3);
		
		
		
		f1=new MyTextField(l1){
			@Override
			protected void processEvent(AWTEvent arg0) {
				if(arg0.getID()==KeyEvent.KEY_RELEASED || arg0.getID()==KeyEvent.KEY_TYPED)
					if(((KeyEvent)arg0).getKeyCode()==KeyEvent.VK_ENTER) ok.doClick();
				super.processEvent(arg0);
			}
		};
		f1.setSize(80,40);
		f1.setLocation(240,40);
		
		f2=new MyTextField(l2){
			private static final long serialVersionUID = 1L;

			@Override
			protected void processEvent(AWTEvent arg0) {
				if(arg0.getID()==KeyEvent.KEY_RELEASED || arg0.getID()==KeyEvent.KEY_TYPED)
					if(((KeyEvent)arg0).getKeyCode()==KeyEvent.VK_ENTER) ok.doClick();
				super.processEvent(arg0);
			}
		};
		f2.setSize(80,40);
		f2.setLocation(240,100);
		
		f3=new MyTextField(l3){
			private static final long serialVersionUID = 1L;

			@Override
			protected void processEvent(AWTEvent arg0) {
				if( arg0.getID()==KeyEvent.KEY_RELEASED || arg0.getID()==KeyEvent.KEY_TYPED)
					if(((KeyEvent)arg0).getKeyCode()==KeyEvent.VK_ENTER) ok.doClick();
				super.processEvent(arg0);
			}
		};
		f3.setSize(80,40);
		f3.setLocation(240,160);
		
		getContentPane().add(f1);
		getContentPane().add(f2);
		getContentPane().add(f3);
		
		
		ok=new JButton("OK");
		ok.setSize(80,40);
		ok.setLocation(80,240);
		getContentPane().add(ok);
		ok.addActionListener(this);
		
		exit=new JButton("Exit");
		exit.setSize(80,40);
		exit.setLocation(200,240);
		getContentPane().add(exit);
		exit.addActionListener(this);
		
		getContentPane().setBackground(Color.GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
	}

	
	@Override
	public void print(Graphics g) {
		super.print(g);
	}


	public static void main(String[] arg){
		new MasterForm("Mine Sweeper", 400, 400);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b=(JButton)e.getSource();
		if(b==exit)
			System.exit(0);
		else {
			if( f1.getText().length()<1) {
				f1.selectAll();
				exit.transferFocus();
			}
			else if(f1.getText().length()<1) {
				f2.selectAll();
				f1.transferFocus();
			}
			else if(f1.getText().length()<1) {
				f3.selectAll();
				f2.transferFocus();
			}
			else if( Integer.parseInt(f1.getText())>20) {
				f1.selectAll();
				exit.transferFocus();
			}
			else if(Integer.parseInt(f2.getText())>30) {
				f2.selectAll();
				f1.transferFocus();
			}
			else if(Integer.parseInt(f1.getText())*
					Integer.parseInt(f2.getText())*.4<Integer.parseInt(f3.getText())) {
				f3.selectAll();
				f2.transferFocus();
			} else {
				a=new Board(Integer.parseInt(f1.getText()), Integer.parseInt(f2.getText()), Integer.parseInt(f3.getText()));
			}
		}
			
		
	}





}
	
class MyTextField extends JTextField implements FocusListener{
	
	private static final long serialVersionUID = 6929914907193249135L;
	private JLabel lable;
	
	public MyTextField(JLabel lable){
		this.lable=lable;
		this.addFocusListener(this);
	}

	@Override
	protected void processEvent(AWTEvent arg0) {
		
		if(arg0.getID()==KeyEvent.KEY_PRESSED || arg0.getID()==KeyEvent.KEY_RELEASED || arg0.getID()==KeyEvent.KEY_TYPED) {
			
			KeyEvent k=(KeyEvent)arg0;
			char c=k.getKeyChar();

			if(c>='0' && c<='9' || c=='\b' ) 
				if((this.getText().length()<1) && c=='0')
					k.consume();
				else super.processEvent(arg0);
			else 
				k.consume();
			
		} else {
			super.processEvent(arg0);
		}
		
		
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		lable.setForeground(Color.red);
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		lable.setForeground(Color.black);
		
	}

	
	
	
	
}