package Server;

//%W%	%G%
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity


public class Ball implements Serializable, Runnable{

	@Id
	@GeneratedValue
	private int id;
	public final static Random random = new Random();
	final static int SIZE = 10;
	final static int MAX_SPEED = 5;
	@Column 
	private int panelWidth;
	@Column
	private int panelHeight;
	@Column
	private int x;
	@Column
	private int y;
	@Column
	private int dx;
	@Column
	private int dy;
	@Column
	private Color color;

	public Ball(Color color, int panelWidth, int panelHeight) {
		this.color = color;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;

		x = random.nextInt(panelWidth);
		y = random.nextInt(panelHeight);
		dx = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
		dy = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;

	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, SIZE, SIZE);
	}

	public void move() {
		// check for bounce and make the ball bounce if necessary
		//

		if (x < 0 && dx < 0) {
			// bounce off the left wall
			x = 0;
			dx = -dx;
		}
		if (y < 0 && dy < 0) {
			// bounce off the top wall
			y = 0;
			dy = -dy;
		}
		if (x > panelWidth - SIZE && dx > 0) {
			// bounce off the right wall
			x = panelWidth - SIZE;
			dx = -dx;
		}
		if (y > panelHeight - SIZE && dy > 0) {
			// bounce off the bottom wall
			y = panelHeight - SIZE;
			dy = -dy;
		}

		// make the ball move
		x += dx;
		y += dy;

	}

	public void run() {
		while (true) {
			move();
			try {
				Thread.sleep(40); // wake up roughly 25 frames per second
			} catch (InterruptedException exception) {
				exception.getLocalizedMessage().toString();
			}
		}
	}



}
