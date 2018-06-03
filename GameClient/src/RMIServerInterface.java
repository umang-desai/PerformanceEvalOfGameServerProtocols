import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIServerInterface extends Remote{
	public String join(String name, String ip, int port) throws RemoteException;
	public String returnLocation() throws RemoteException;
	public String returnElements() throws RemoteException;
	public String updateState(String state) throws RemoteException;
}
