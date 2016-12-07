
package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import event.MyEvent;
import event.addBombEvent;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private Vector<Airplane> airs;
	private Vector<Bomb> boms;
	private int rightTankdeg, leftTankdeg;
	private int[][] map;
	private int[][] mapEasy = { { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 },
			{ 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1 } };
	private int[][] mapNormal = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0 },
			{ 1, 0, 1, 1, 0 }, { 1, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1 } };;
	private int[][] mapHard = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0 }, { 1, 0, 0, 1, 0 }, { 1, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1 } };
	private BufferedImage[] tanks;
	private JTextField hidden;
	private ImageIcon tower, cloud, background, airplane, bombicon,explode;
	private ImageIcon tankleft, tankright;
	private Vector<Bullet> bullets;
	private Vector<Explosion> explod;
	private ImageIcon bullet;

	public GamePanel(int d, String name, Game parnet) {
		explod= new Vector<Explosion>();
		bullet = new ImageIcon("bullet.png");
		explode = new ImageIcon("explosion.gif");
		bullets = new Vector<Bullet>();
		boms = new Vector<Bomb>();
		bombicon = new ImageIcon("bomb.png");
		airplane = new ImageIcon("airplane.png");
		airs = new Vector<Airplane>();
		for (int i = 0; i < 3 + d; i++) {
			airs.add(new Airplane(i * 40, -200 + i * 40, this,(int)(Math.random()*20)+7));
		}
		rightTankdeg = 0;
		leftTankdeg = 0;
		hidden = new JTextField();
		hidden.setSize(0, 90);
		add(hidden);
		hidden.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tanks[1] = rotated(tankright, true);
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					tanks[1] = rotateu(tankright, true);
				}
				if (e.getKeyChar() == 'a') {

					tanks[0] = rotated(tankleft, false);
				}
				if (e.getKeyChar() == 'd') {
					tanks[0] = rotateu(tankleft, false);
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Bullet a = new Bullet(530, 320, -rightTankdeg, true,GamePanel.this);
					a.start();
					bullets.add(a);

				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					Bullet a = new Bullet(50, 320, leftTankdeg, false,GamePanel.this);
					a.start();
					bullets.add(a);
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				

			}
		});
		switch (d) {
		case 0:
			map = mapEasy;
			break;
		case 1:
			map = mapNormal;
			break;
		case 2:
			map = mapHard;
			break;

		default:
			map = mapEasy;
			break;
		}
		tower = new ImageIcon("tower.png");
		cloud = new ImageIcon("cloud.png");
		background = new ImageIcon("sky.jpg");
		tankleft = new ImageIcon("tankleft.png");
		tankright = new ImageIcon("tankright.png");
		setSize(800 - 150 - 40, 600 - 80);
		setLocation(10, 25);
		createTanks();
		tanks[1] = rotated(tankright, true);
		tanks[0] = rotated(tankleft, false);
	}

	private void createTanks() {
		tanks = new BufferedImage[2];
		tanks[0] = new BufferedImage(230, 230, BufferedImage.TYPE_4BYTE_ABGR);
		tanks[1] = new BufferedImage(130, 230, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g1 = tanks[0].createGraphics();
		Graphics2D g2 = tanks[1].createGraphics();
		g1.drawImage(tankleft.getImage(), 0, 0, null);
		g2.drawImage(tankright.getImage(), 0, 0, null);
	}

	private BufferedImage rotated(ImageIcon i, boolean n) {
		BufferedImage r = new BufferedImage(130, 200,
				BufferedImage.TYPE_4BYTE_ABGR);
		AffineTransform e = new AffineTransform();
		if (n) {
			e.translate(15, 25);
			e.translate(65, 32.5);
			if (rightTankdeg <= 65)
				rightTankdeg += 5;
			e.rotate(Math.toRadians(rightTankdeg));
			e.translate(-65, -32.5);
		} else {
			e.translate(0, 25);
			e.translate(65, 32.5);
			if (leftTankdeg >= -65)
				leftTankdeg -= 5;
			e.rotate(Math.toRadians(leftTankdeg));
			e.translate(-65, -32.5);
		}
		Graphics2D g = r.createGraphics();
		g.drawImage(i.getImage(), e, null);
		repaint();
		return r;
	}

	private BufferedImage rotateu(ImageIcon i, boolean n) {
		BufferedImage r = new BufferedImage(130, 200,
				BufferedImage.TYPE_4BYTE_ABGR);
		AffineTransform e = new AffineTransform();
		if (n) {
			e.translate(15, 25);
			e.translate(65, 32.5);
			
			if (rightTankdeg >= 5)
				rightTankdeg -= 5;
			e.rotate(Math.toRadians(rightTankdeg));
			e.translate(-65, -32.5);
		} else {
			e.translate(0, 25);
			e.translate(65, 32.5);
			if (leftTankdeg <= -5)
				leftTankdeg += 5;
			e.rotate(Math.toRadians(leftTankdeg));
			e.translate(-65, -32.5);
		}
		Graphics2D g = r.createGraphics();
		g.drawImage(i.getImage(), e, null);
		return r;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background.getImage(), 0, 0, null);
		g.drawImage(cloud.getImage(), 20, 20, null);
		g.drawImage(cloud.getImage(), 90, 25, null);
		g.drawImage(cloud.getImage(), 30, 70, null);
		g.drawImage(cloud.getImage(), 120, 75, null);
		g.drawImage(cloud.getImage(), 190, 50, null);
		g.drawImage(cloud.getImage(), 250, 80, null);
		g.drawImage(cloud.getImage(), 350, 10, null);
		g.drawImage(cloud.getImage(), 220, 20, null);
		g.drawImage(cloud.getImage(), 290, 25, null);
		g.drawImage(cloud.getImage(), 390, 50, null);
		g.drawImage(cloud.getImage(), 450, 80, null);
		g.drawImage(cloud.getImage(), 480, 10, null);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				if (map[j][i] == 1)
					g.drawImage(tower.getImage(), i * 80 + 105, j * 58 + 172,
							null);
			}
		}
		g.drawImage(tanks[0], 0, 300, null);
		g.drawImage(tanks[1], 480, 300, null);
		for (int i = 0; i < airs.size(); i++) {
			g.drawImage(airplane.getImage(), airs.elementAt(i).getXx(), airs
					.elementAt(i).getYy(), null);
		}
		boolean flag=true;
		if(!bullets.isEmpty())
			for (int i = 0; i < bullets.size(); i++) {
			Bullet bb=bullets.elementAt(i);
			if(bb.getXx()>610 || bb.getYy()>520 || bb.getXx()<0 || bb.getYy()<0){
				bb.setAlive(false);
				bullets.remove(i);
				flag=false;
			}
			else {
				for(int ii =0 ; ii < boms.size();ii++){
					if(bb.getXx()<boms.elementAt(ii).getXx()+15 && bb.getXx()>boms.elementAt(ii).getXx()){
						if(bb.getYy()<boms.elementAt(ii).getYy()+42 && bb.getYy()>boms.elementAt(ii).getYy()){
							boms.elementAt(ii).setStopflag(false);
							explod.add(new Explosion(boms.elementAt(ii).getXx(), boms.elementAt(ii).getYy()));
							boms.remove(ii);
							if(airs.size()==0 && boms.size()==0) {end(true);System.out.println("asad");}
							bb.setAlive(false);
							bullets.remove(i);
							flag=false;
						}
					}
				}
			}
			
			if(flag){
				for(int ii =0 ; ii < airs.size();ii++){
					if(bb.getXx()<airs.elementAt(ii).getXx()+100 && bb.getXx()>airs.elementAt(ii).getXx()){
						if(bb.getYy()<airs.elementAt(ii).getYy()+32 && bb.getYy()>airs.elementAt(ii).getYy()){
							airs.elementAt(ii).setalive(false);
							airs.remove(ii);
							explod.add(new Explosion(bb.getXx(), bb.getYy()));
							if(airs.size()==0 && boms.size()==0) end(true);
							bb.setAlive(false);
							bullets.remove(i);  
							flag=false;
						}
					}
				}
			}
			if (flag && bullets.size()>0 )g.drawImage(bullet.getImage(), bullets.elementAt(i).getXx(),
					bullets.elementAt(i).getYy(), null);
		}
		for (int i = 0; i < boms.size(); i++) {
			if (boms.elementAt(i).getYy() > 490) {
				boms.elementAt(i).setStopflag(false);
				boms.remove(i);
			} else if (boms.elementAt(i).getYy() > 172
					&& boms.elementAt(i).getXx() > 105
					&& map[(boms.elementAt(i).getYy() - 172 + 20) / 58][(boms
							.elementAt(i).getXx() - 105) / 80] == 1) {
				boms.elementAt(i).setStopflag(false);
				map[(boms.elementAt(i).getYy() - 172 + 20) / 58][(boms
						.elementAt(i).getXx() - 105) / 80] = 0;
				boolean endflag=false;
				for(int jj = 0;jj<6;jj++){
					for(int j=0;j<5;j++){
						if(map[jj][j]==1) endflag= true;
					}
				}
				if(!endflag) end(false);
				boms.remove(i);
				if(airs.size()==0 && boms.size()==0) end(true);
			} else
				g.drawImage(bombicon.getImage(), boms.elementAt(i).getXx(),
						boms.elementAt(i).getYy(), null);
		}
		for (int i=0;i<explod.size();i++){
			g.drawImage(explode.getImage(),explod.elementAt(i).getX(),explod.elementAt(i).getY(),50,70, null);
			explod.elementAt(i).decShow();
			if(explod.elementAt(i).getShow()==0) explod.remove(i);
		}
	}

	private void end(boolean f) {
		for (Airplane a :airs) a.setalive(false);
		for (Bomb a :boms) a.setStopflag(true);
		for (Bullet a :bullets) a.setAlive(false);
		if(f){
			JOptionPane.showMessageDialog(this,"You Win");
			System.exit(0);
		}
		else {
			JOptionPane.showMessageDialog(this,"You lose");
			System.exit(0);
		}
		
	}

	@Override
	public void run() {
		for (int i = 0; i < airs.size(); i++)
			airs.elementAt(i).start();
		while (true){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {}
			repaint();
		}
	}
	@Override
	protected void processComponentEvent(ComponentEvent e) {
		if(e.getID()==MyEvent.addBomb){
			addBombEvent r=(addBombEvent)e;
			addb(r.getBomb());
		}
		super.processComponentEvent(e);
	}
	private void addb(Bomb b) {
		b.start();
		boms.add(b);
	}

}
