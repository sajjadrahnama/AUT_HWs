package mapEditor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import event.MyEvent;

public class ManagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MapMaker parent;
	private JButton save ,load,spring,summer,fall,winter,back,exit;
	public ManagePanel(MapMaker parent) {
		this.parent=parent;
		
		back = new JButton("Back");
		back.setSize(90,30);
		back.setLocation(5,60);
		add(back);
		
		exit= new JButton("Exit");
		exit.setSize(90,30);
		exit.setLocation(95,60);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		}); 
		add(exit);
		
		
		save= new JButton("Save map");
		save.setSize(90,30);
		save.setLocation(5,25);
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagePanel.this.parent.dispatchEvent(new MyEvent(ManagePanel.this,MyEvent.sv));
			}
		});
		add(save);
		
		load= new JButton("Load map");
		load.setSize(90,30);
		load.setLocation(95,25);
		load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagePanel.this.parent.dispatchEvent(new MyEvent(ManagePanel.this,MyEvent.lo));
			}
		});
		add(load);
		
		spring= new JButton("Spring");
		spring.setSize(90,50);
		spring.setLocation(10,120);
		spring.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagePanel.this.parent.dispatchEvent(new MyEvent(ManagePanel.this,MyEvent.cs,1));
				
			}
		} );
		add(spring);
		
		summer= new JButton("Summer");
		summer.setSize(90,50);
		summer.setLocation(100,120);
		summer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagePanel.this.parent.dispatchEvent(new MyEvent(ManagePanel.this,MyEvent.cs,2));
				
			}
		});
		add(summer);
		
		
		fall= new JButton("Fall");
		fall.setSize(90,50);
		fall.setLocation(10,180);
		fall.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagePanel.this.parent.dispatchEvent(new MyEvent(ManagePanel.this,MyEvent.cs,3));
				
			}
		});
		add(fall);
		
		winter= new JButton("Winter");
		winter.setSize(90,50);
		winter.setLocation(100,180);
		winter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ManagePanel.this.parent.dispatchEvent(new MyEvent(ManagePanel.this,MyEvent.cs,4));
				
			}
		});
		add(winter);
		

		
		setSize(200,250);
		setLayout(null);
		setLocation(820,370);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
		setVisible(true);
	}
	@Override
	protected void processComponentEvent(ComponentEvent arg0) {
		if(arg0.getID()== MyEvent.setFocus){
			winter.transferFocus();
		}
		super.processComponentEvent(arg0);
	}
}
