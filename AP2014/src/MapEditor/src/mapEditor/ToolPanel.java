package mapEditor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import event.getImageToShowEvent;

public class ToolPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private String[] name={"Ground","Grass","Rock","Jungle","Water","CM","CM","CM"};
	private MapMaker parent;
	public ToolPanel(MapMaker parent) {
		this.parent=parent;
		buttons = new JButton[8];
		for (int i = 0; i < 4; i++) {
			buttons[ 2*i] = createButton(name[2*i], 10, i * 87 + 10);
			buttons[2 *i+1 ] = createButton(name[2*i+1], 105, i * 87 + 10);
		}
		setUpButt();
		for (int i = 0; i < 8; i++)
			add(buttons[i]);

		setSize(200, 370);
		setLayout(null);
		setLocation(820, 0);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
		setVisible(true);
	}

	private void setUpButt() {
		buttons[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 0));
			}
		});
		buttons[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 1));			}
		});
		buttons[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 2));			}
		});
		buttons[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 3));			}
		});
		buttons[4].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 4));			}
		});
//		buttons[5].addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//			ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 5));
//			}
//		});
//		buttons[6].addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//			ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 6));
//			}
//		});
//		buttons[7].addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//			ToolPanel.this.parent.dispatchEvent(new getImageToShowEvent(ToolPanel.this, 7));
//			}
//		});
	}
//	private ImageIcon createIcon(int i) {
//		ImageIcon img = new ImageIcon("button" + i + ".jpg");
//		return img;
//	}
	private JButton createButton(String  name, int i, int j) {
		JButton b = new JButton(name);
		b.setSize(85, 85);
		b.setLocation(i, j);
		return b;
	}
}
