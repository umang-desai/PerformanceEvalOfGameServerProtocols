import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RMIFactory {
	public static void registerPort(int port, String id, RMIServerInterface obj) throws RemoteException{
		Registry reg = LocateRegistry.createRegistry(port);
		reg.rebind(id, obj);
	}
	
	public static void registerPort(int port, String id, RMIClientInterface obj) throws RemoteException{
		Registry reg = LocateRegistry.createRegistry(port);
		reg.rebind(id, obj);
	}
	
	public static RMIServerInterface fetchServerInterface(String ip, int port, String id)
			throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry(ip, port);
		RMIServerInterface node = (RMIServerInterface) reg.lookup(id);
		return node;
	}
	
	public static RMIClientInterface fetchClientInterface(String ip, int port, String id)
			throws RemoteException, NotBoundException {
		Registry reg = LocateRegistry.getRegistry(ip, port);
		RMIClientInterface node = (RMIClientInterface) reg.lookup(id);
		return node;
	}
}
