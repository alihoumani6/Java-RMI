package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.w3c.dom.ranges.RangeException;

public interface SpriteGame extends Remote{
	
	public SpriteSession getSession() throws RemoteException;

}
