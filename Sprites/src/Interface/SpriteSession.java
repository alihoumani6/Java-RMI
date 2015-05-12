package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Server.Ball;

public interface SpriteSession extends Remote { 

	
	public ArrayList<Ball> getSprites() throws java.rmi.RemoteException;
	
	public void addSprite() throws java.rmi.RemoteException;
	

}
