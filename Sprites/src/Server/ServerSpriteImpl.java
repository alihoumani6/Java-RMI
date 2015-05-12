package Server;

import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.cfg.Configuration;

import Interface.SpriteGame;
import Interface.SpriteSession;

public class ServerSpriteImpl implements SpriteGame, Runnable {

	static SessionFactory factory;
	StandardServiceRegistryBuilder sRBuilder;
	org.hibernate.service.ServiceRegistry sR;
	static List<Ball> ballList;
	Random r = new Random();

	int red = r.nextInt(255) + 1;
	int blue = r.nextInt(255) + 1;
	int green = r.nextInt(255) + 1;

	Color col = new Color(red, blue, green);

	
	public ServerSpriteImpl() throws RemoteException {

		try {
			Configuration config = new Configuration().addAnnotatedClass(
					Ball.class).configure("hibernate.cfg.xml");

			new SchemaExport(config).create(true, true);

			sRBuilder = new StandardServiceRegistryBuilder()
					.applySettings(config.getProperties());

			sR = sRBuilder.build();

			factory = config.buildSessionFactory(sR);

			new Thread(this).start();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	public SpriteSession getSession() throws RemoteException {
		SpriteSession session = null;

		try {
			session = new ServerSpriteInner();

			UnicastRemoteObject.exportObject(session, 0);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return session;
	}

	public void run() {

		Session session = null;
		try {
			session = factory.getCurrentSession();

			session.beginTransaction();
			Query q = session.createQuery("From Ball");

			ballList = q.list();

			session.getTransaction().commit();

		} catch (HibernateException e) {
			session.getTransaction().rollback();
		}

		while (true) {
			try {
				session = factory.getCurrentSession();
				session.beginTransaction();

				for (Ball ball : ballList) {
					ball.move();

					session.update(ball);
				}

				session.getTransaction().commit();

				try {
					Thread.sleep(40);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (HibernateException e) {
				session.getTransaction().rollback();
			}

		}
	}
}

class ServerSpriteInner implements SpriteSession {

	private static Ball sprite;

	private final SessionFactory factory;

	Random r = new Random();

	int red = r.nextInt(255) + 1;
	int blue = r.nextInt(255) + 1;
	int green = r.nextInt(255) + 1;

	Color col = new Color(red, blue, green);

	public ServerSpriteInner() throws RemoteException {
		this.factory = ServerSpriteImpl.factory;
	}

	public ArrayList<Ball> getSprites() throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<Ball>) ServerSpriteImpl.ballList;
	}

	public void addSprite() throws RemoteException {
		Session session = null;
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();

			ServerSpriteImpl.ballList.add(sprite = new Ball(col, 400, 400));

			Thread t = new Thread(sprite);
			t.start();
			session.persist(sprite);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
		}

		System.out.println("New sprite created. ");
	}
}
