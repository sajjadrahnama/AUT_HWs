package main;

import javax.swing.*;

public class Game extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//private JPanel rightPanel;
	private GamePanel leftPanel;
	private int d;
	private String name;
	public Game(int d,String name) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(650,600);
		setLocation(100, 100);
		setLayout(null);
		this.d=d;
		this.name=name;
		createPanels();
		
		
		
		
		setVisible(true);
	}
	private void createPanels() {
//		rightPanel=new JPanel();
//		rightPanel.setSize(150,520);
//		rightPanel.setLocation(800-150-20,25);
//		rightPanel.setBorder(BorderFactory.createEtchedBorder());
//		add(rightPanel);
		
		leftPanel=new GamePanel(d,name,this);
		leftPanel.setBorder(BorderFactory.createEtchedBorder());
		add(leftPanel);
		(new Thread(leftPanel)).start();
		
	}
}
