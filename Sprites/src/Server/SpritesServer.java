package Server;

import Interface.SpriteSession;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.security.auth.login.Configuration;

import org.hibernate.HibernateError;
import org.hibernate.HibernateException;

public class SpritesServer {

	public SpritesServer() {

	}

	public void runServer() {

		try {
			ServerSpriteImpl SSI= new ServerSpriteImpl();
			// This server runs the registry on port 8080
			LocateRegistry.createRegistry(1099);
			System.out.println("Registry created");

			// The calculator object will be reached at this server on port 8081
			UnicastRemoteObject.exportObject(SSI, 0);
			System.out.println("Exported");

			// A client will need to look up the calculator remote object at
			// this machine's
			// address, using the service name "SpritesService"
			Naming.rebind("rmi://localhost/SpriteService", SSI);
			System.out.println("Service started");
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}

	public static void main(String args[]) {
		new SpritesServer().runServer();
	}
}
