package mainpackage;


import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Board extends JDialog {
	
	private static final long serialVersionUID = -3753217627892228997L;

	int rows,cols,mines;
	JButton reset,newgame,exit;
	Block[][] blocks;
	public Board(int rows,int cols,int mines) {
		setModal(true);
		enableEvents(AWTEvent.COMPONENT_EVENT_MASK);
		getContentPane().setBackground(Color.DARK_GRAY);
		this.rows=rows;
		this.cols=cols;
		this.mines=mines;
		
		this.reset=new JButton("ReStart");
		reset.setSize(110, 40);
		reset.setLocation(cols*20+60,40);
		this.add(reset);
		reset.setVisible(true);
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Board(Board.this.rows,Board.this.cols,Board.this.mines);
				Board.this.dispose();
			}			
		});
		this.exit=new JButton("Exit");
		exit.setSize(110, 40);
		exit.setLocation(cols*20+60,160);
		this.add(exit);
		exit.setVisible(true);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(Board.this,"خیلی ناراحت شدم که");
				System.exit(0);
			}
		});
		
		
		this.newgame=new JButton("New Game");
		newgame.setSize(110, 40);
		newgame.setLocation(cols*20+60,100);
		this.add(newgame);
		newgame.setVisible(true);
		newgame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Board.this.dispose();
			}

		});
		
		setSize(cols*20+16+100+100,rows*20+40+100);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		blocks=new Block[cols][rows];
		
		for(int x=0;x<cols;x++)
			for(int y=0;y<rows;y++) {
				blocks[x][y]=new Block(x,y,this);
				blocks[x][y].setLocation(x*20+50,y*20+50);
				blocks[x][y].setIcon(new ImageIcon("naterkide.jpg"));
				getContentPane().add(blocks[x][y]);
			}
	
		
		for(int i=0;i<mines;i++) {
			int x=(int)(Math.random()*cols);
			int y=(int)(Math.random()*rows);
			
			while(blocks[x][y].isMine()) {
				x=(int)(Math.random()*cols);
				y=(int)(Math.random()*rows);
			}
			
			blocks[x][y].setMine();	
		}
		
		for(int x=0;x<cols;x++)
			for(int y=0;y<rows;y++) 
				for(int i=0;i<3;i++)
					for(int j=0;j<3;j++){
						try{
							if(blocks[x-1+i][y-1+j].isMine() && !(i==1 && j==1)) blocks[x][y].setNearMineNumber(blocks[x][y].getNearMineNumber()+1);
						}catch(ArrayIndexOutOfBoundsException e){}
					}
		setVisible(true);
	}


	@Override
	protected void processComponentEvent(ComponentEvent e) {
		if(e.getID()==AWTEvent.RESERVED_ID_MAX+2){
			MyEvent ee=(MyEvent)e;
			Board a=(Board) ee.getSource();
			explode(a.blocks[ee.getX1()][ee.getY1()]);
		}
		if(e.getID()==AWTEvent.RESERVED_ID_MAX+1) {
			MyEvent ee=(MyEvent)e;
			Block c=(Block) ee.getSource();
			if(c.isMine()) end(0);
			else explode(c);
		}
	
		super.processComponentEvent(e);
	}

	private void explode(Block c) {
		if(c.isClicked()) return ;
		if(c.getNearMineNumber()>0) {
			c.setIcon(new ImageIcon((c.getNearMineNumber())+".jpg"));
			c.setClicked(true);
			c.setFinishFlag(true);

		}
		else{
			c.setClicked(true);
			c.setIcon(new ImageIcon("0"+".jpg"));
			for(int x=0;x<3;x++)
				for(int y=0;y<3;y++){
					try{
						if(!(x==1 && y==1)) {				
							this.dispatchEvent(new MyEvent(this,AWTEvent.RESERVED_ID_MAX+2,c.getx()-1+x,c.gety()-1+y));
						}
					}catch(ArrayIndexOutOfBoundsException e){}
				}
		}
		int u=0;
		c.setFinishFlag(true);
		for(int x=0;x<cols;x++)
			for(int y=0;y<rows;y++) {
				if(blocks[x][y].getFinishFlag()) u++;
		}
		if(u==rows*cols-mines) end(1);
		
	}

	private void end(int i) {
		if(i==0){
			for(int x=0;x<cols;x++)
				for(int y=0;y<rows;y++) {
					blocks[x][y].setClicked(true);
					if(blocks[x][y].isMine() && !blocks[x][y].isDfused()) blocks[x][y].setIcon(new ImageIcon("bombterekide.jpg"));
				}
			JOptionPane.showMessageDialog(this, "You lose");
		}
		else {
			JOptionPane.showMessageDialog(this, "You win");
		}
	}
	
	

}



class Block extends JButton implements ActionListener{
	
	
	private boolean finishflag=false;
	private boolean isDefused=false;
	private static final long serialVersionUID = 1L;
	private int nearMineNumber=0;
	private boolean mine;
	private int x,y;
	private JDialog parent;
	private boolean isClicked=false;
	public Block(int x,int y,JDialog parent) {
		setSize(20,20);
		mine=false;
		this.x=x;
		this.y=y;
		this.addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) Block.this.dispatchEvent(new MyEvent(Block.this, AWTEvent.RESERVED_ID_MAX+3, Block.this.x, Block.this.y));
            }
		});
		this.addActionListener(this);		
		this.parent=parent;
		
	}
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	public boolean getFinishFlag() {
		return this.finishflag;
	}
	public void setFinishFlag(boolean e) {
		this.finishflag=e;
	}
	public boolean isDfused(){
		return this.isDefused;
	}
	
	public boolean isMine() {
		return mine;
	}
	public void setMine() {
		mine=true;
	}
	public int getNearMineNumber() {
		return nearMineNumber;
	}
	public void setNearMineNumber(int nearMineNumber) {
		this.nearMineNumber = nearMineNumber;
	}
	public boolean isClicked() {
		return isClicked;
	}
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		parent.dispatchEvent(new MyEvent(this, AWTEvent.RESERVED_ID_MAX+1, x, y));
	}
	@Override
	protected void processComponentEvent(ComponentEvent a) {
		if(a.getID()==AWTEvent.RESERVED_ID_MAX+3){
			Block b=(Block)a.getSource();
			if(b.isDefused==false){
				b.isDefused=true;
				b.setClicked(true);
				b.setIcon(new ImageIcon("defused.jpg"));
			}
			else{
				b.setClicked(false);
				b.isDefused=false;
				System.out.println("ali");
				b.setIcon(new ImageIcon("naterkide.jpg"));
			}
		}
		else super.processComponentEvent(a);
	}
}

class MyEvent extends ComponentEvent {
	
	private static final long serialVersionUID = 1L;
	private int x,y;
	
	public MyEvent(Component src,int id,int x,int y) {
		super(src,id);
		
		this.x=x;
		this.y=y;
		
	}
	public int getX1() {
		return x;
	}
	public int getY1() {
		return y;
	}
}




