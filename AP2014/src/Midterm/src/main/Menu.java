
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Menu extends JFrame{

	private static final long serialVersionUID = 1L;
	private JRadioButton [] radio;
	private ButtonGroup radio1;
	private JLabel dificulty,NameLable;
	private JTextField Name;
	private JButton play,exit;
	public Menu() {
		for (LookAndFeelInfo a : UIManager.getInstalledLookAndFeels())
			if ("Nimbus".equals(a.getName()))
				try {
					UIManager.setLookAndFeel(a.getClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
				}
		setLayout(null);
		
		radio=new JRadioButton [3];
		String arg=null;
		radio1=new ButtonGroup();
		for (int i=0;i<3;i++) {
			switch (i) {
			case 0:
				arg="Easy";
				break;
			case 1: 
				arg="Medium";
				break;
			case 2: 
				arg="Hard";
				break;
			default:
				break;
			}
			radio[i]=createRadio(arg,radio[i],20+(i*70),140);
		}
		radio[1].setSelected(true);
		for(int i =0 ; i<3;i++){
			radio[i].addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					dificulty.setForeground(Color.black);
					
				}
				@Override
				public void focusGained(FocusEvent arg0) {
					dificulty.setForeground(Color.red);
					
				}
			});
		}
		
		setSize(400,250);
		setLocation(50,50);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		dificulty=new JLabel("Difficulty");
		dificulty.setSize(80,20);
		dificulty.setLocation(70,110);
		dificulty.setFont(new Font("Elephant", 20, 15));
		add(dificulty);
		
		NameLable=new JLabel("Name");
		NameLable.setSize(50,30);
		NameLable.setLocation(270,105);
		NameLable.setFont(new Font("Elephant", 20, 15));
		add(NameLable);
		
		Name=new JTextField();
		Name.setSize(100,30);
		Name.setLocation(250,140);
		add(Name);
		
		
		Name.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				NameLable.setForeground(Color.black);
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				NameLable.setForeground(Color.red);
				
			}
		});
		
		exit=new JButton("EXIT");
		exit.setSize(60,30);
		exit.setLocation(90,60);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(Menu.this,"خیلی ناراحت شدم که");
				System.exit(0);
				
			}
		});
		add(exit);
		play=new JButton("Start Game");
		play.setSize(120,30);
		play.setLocation(200,60);
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(Name.getText().equals("")) {
					JOptionPane.showMessageDialog(Menu.this,"Enter Your Name Please");
				}
				
				else if(!radio[0].isSelected()&& !radio[1].isSelected() && !radio[2].isSelected()) {
					 JOptionPane.showMessageDialog(Menu.this,"Select Difficulty");
				 }
				else if(radio[0].isSelected())  new Game(0,Name.getText());
				else if(radio[1].isSelected())  new Game(1,Name.getText());
				else if(radio[2].isSelected())  new Game(2,Name.getText());
				else;
			}
		});
		add(play);
		
		setVisible(true);
		
	}
	private JRadioButton createRadio(String lable,JRadioButton r, int i, int j) {
		r=new JRadioButton(lable);
		r.setSize(70, 20);
		r.setLocation(i, j);
		add(r);
		radio1.add(r);
		return r;
	}
	public static void main(String[] args) {
		new Menu();

	}

}
