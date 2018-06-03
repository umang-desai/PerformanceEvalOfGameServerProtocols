import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Random;

public class GameServer extends UnicastRemoteObject implements
		RMIServerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Zone zone;

	public GameServer() throws RemoteException {
		zone = new Zone(10);
	}

	public GameServer(int dimension) throws RemoteException {
		zone = new Zone(dimension);
	}

	@Override
	public String join(String name, String ip, int port) {
		Player player = zone.addNewPlayer(name, ip, port);
		// TODO return player. only if it has the same name/ip/port.
		if (player != null) {
			int[] pos = zone.getPlayer().getPosition();
			String output = "LOCATION:" + pos[0] + "," + pos[1];
			return output;
		}
		return null;
	}

	@Override
	public String returnLocation() {
		String output = null;
		int[] pos = zone.getPlayer().getPosition();
		output = "LOCATION:" + pos[0] + "," + pos[1];
		return output;
	}
	
	@Override
	public String returnElements(){
		String output = null;
		Iterator<Zone.Element> iter = zone.getPlayer().getPickups().iterator();
		output = "OBJECTS:";
		output+="[";
		while(iter.hasNext()){
			Zone.Element element = (Zone.Element) iter.next();
			output+=element.name;
			if(iter.hasNext())
				output+=",";
		}
		output+="]";
		return output;
	}

	@Override
	public String updateState(String state) {
		zone.movePlayer(state);
		int[] pos = zone.getPlayer().getPosition();
		String output = "LOCATION:" + pos[0] + "," + pos[1];
		return output;
	}

	public static void main(String[] args) throws RemoteException {
		print("RMI IMPLEMENTATION");
		String name = "SERVER";
		int port = Integer.parseInt(args[0]);

		GameServer game = new GameServer();
		RMIServerInterface obj = (RMIServerInterface) game;
		RMIFactory.registerPort(port, name, obj);
		print("Started");
	}
	
	public static int random(int upperBound){
		Random r = new Random();
		return r.nextInt(upperBound);
	}

	public static void print(String str) {
		System.out.println("SERVER: " + str);
	}
}
