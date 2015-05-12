package Client;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Server.Ball;

import javax.swing.JPanel;

import Interface.SpriteSession;

@SuppressWarnings("serial")
public class ClientPanel extends JPanel {

	private ArrayList<Ball> ballList;
	private SpriteSession s;

	public ClientPanel(SpriteSession s) {
		this.s = s;
		addMouseListener(new Mouse());
	}

	@SuppressWarnings("unchecked")
	public void animate() {
		while (true) {
			try {
				ballList = s.getSprites();
			} catch (RemoteException e) {
				 System.out.println(e.getLocalizedMessage().toString());
				e.printStackTrace();
			}
		
			repaint();
			// sleep while waiting to display the next frame of the animation
			try {
				Thread.sleep(40); // wake up roughly 25 frames per second
			} catch (InterruptedException exception) {
				exception.getLocalizedMessage().toString();
			}
		}
	}

	private class Mouse extends MouseAdapter {

		@Override
		public void mousePressed(final MouseEvent event) {
			try {
				s.addSprite();
			} catch (RemoteException e) {
				System.err.println(e.getLocalizedMessage().toString());
			}

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (ballList != null) {
			for (Ball sprite : ballList) {
				sprite.draw(g);
			}

		}
	}
}
