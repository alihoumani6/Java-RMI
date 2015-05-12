package Client;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import Interface.SpriteGame;
import Interface.SpriteSession;

public class ClientSprite {

		
	    private JFrame frame;
	    private ClientPanel panel;


	    public ClientSprite(SpriteSession s) {
	        frame = new JFrame("Client Sprite");
	        panel = new ClientPanel(s);
	        frame.setSize(400, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(panel);
	        frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		String myHostName = "localhost";

		try {
			InetAddress myHost = Inet4Address.getLocalHost();
			myHostName = myHost.getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		int port = 1099;
		String serverName = new String("localhost");
		switch (args.length) {
		case 0:
			break;
		case 1:
			serverName = args[0];
			break;
		case 2:
			serverName = args[0];
			port = Integer.parseInt(args[1]);
			break;
		default:
			System.out.println("Usage:ClientSprites");
		}
		SpriteGame s = null;
		try {
			System.out.println("Attempting to connect to rmi://" + serverName
					+ ":" + port + "/SpriteService");
			s = (SpriteGame) Naming.lookup("rmi://" + serverName +"/SpriteService");
		} catch (MalformedURLException murle) {
			System.out.println();
			System.out.println("MalformedURLException");
			System.out.println(murle);
		} catch (RemoteException re) {
			System.out.println();
			System.out.println("RemoteException");
			System.out.println(re);
		} catch (NotBoundException nbe) {
			System.out.println();
			System.out.println("NotBoundException");
			System.out.println(nbe);
		} catch (java.lang.ArithmeticException ae) {
			System.out.println();
			System.out.println("java.lang.ArithmeticException");
			System.out.println(ae);
		}
		try{
			SpriteSession session =  s.getSession();
		new ClientSprite(session).panel.animate();
		}catch (RemoteException e){
			e.printStackTrace();
		}
		
		
	}
}