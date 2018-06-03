import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class GameClient extends UnicastRemoteObject implements
		RMIClientInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected GameClient() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws RemoteException,
			NotBoundException, UnknownHostException {
		// SETUP SELF
		// Register self on rmi network.
		print("RMI IMPLEMENTATION");
		String serverName = "SERVER";
		String serverIp = args[0];
		int serverPort = Integer.parseInt(args[1]);
		String clientName = args[2];
		int clientPort = Integer.parseInt(args[3]);
		GameClient client = new GameClient();
		RMIClientInterface clientInt = (RMIClientInterface) client;
		RMIFactory.registerPort(clientPort, clientName, clientInt);

		print("Client Started.");
		print("Client Details." + "\nName: " + clientName + "\nPort: " + clientPort);
		
		// JOIN PROCESS
		// Contact server. Get game object and send a join game request.

		
		RMIServerInterface game = RMIFactory.fetchServerInterface(serverIp,
				serverPort, serverName);
		String ip = InetAddress.getLocalHost().getHostAddress();
		String response = game.join(clientName, ip,
				clientPort);
		
		//JOINED GAME. NOW PLAY
		if (response != null) {
			print("Connected to game. " + response);
			String option = null;
			Scanner scan = new Scanner(System.in);
			do {
				print("--------------------------------\n"
						+ "MENU"
						+ "\nOptions: move, location, elements."
						+ "\n Examples"
						+ "\n move <UP/DOWN/LEFT/RIGHT> <steps>. e.g. move DOWN 3"
						+ "\n location." + "\n elements." + "\n quit.");
				String input = scan.nextLine();
				option = input.split(" ")[0].toLowerCase();
				if (option.equals("move")) {
					print("ANALYSIS START:" + option);
					long start = getTime();
					response = game.updateState(input);
					long end = getTime();
					long diff = end - start;
					print("ANALYSIS END:" + option);
					print("Time Taken: " + diff);
				} else if (option.equals("location")) {
					print("ANALYSIS START:" + option);
					long start = getTime();
					response = game.returnLocation();
					long end = getTime();
					long diff = end - start;
					print("ANALYSIS END: move");
					print("Time Taken: " + diff);
				} else if (option.equals("elements")) {
					print("ANALYSIS START:" + option);
					long start = getTime();
					response = game.returnElements();
					long end = getTime();
					long diff = end - start;
					print("ANALYSIS END:" + option);
					print("Time Taken: " + diff);
				} else if (option.equals("quit")) {
					print("Quit.");
					break;
				} else {
					print("Wrong option.");
				}
				print(response);
			} while (true);
			print(game.returnLocation());
		}else
		{
			print("Server is full.");
		}
	}
	
	public static long getTime() {
		return System.nanoTime();
	}

	public static void print(String str) {
		System.out.println(str);
	}
}
