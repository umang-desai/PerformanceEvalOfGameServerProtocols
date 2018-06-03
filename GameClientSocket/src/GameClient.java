import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class GameClient {

	/**
	 * 
	 */
	private DataInputStream dis;
	private DataOutputStream dos;
	private Socket serverSocket;

	protected GameClient(Socket socket) {
		this.serverSocket = socket;
	}

	private void setupSocketCommunication(Socket socket) throws IOException {
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	}
	
	public void closeResources() throws IOException{
		serverSocket.close();
		dis.close();
		dos.close();
	}

	public String readSocket() throws IOException {
		return dis.readUTF();
	}

	public void sendToSocket(String str) throws IOException {
		dos.writeUTF(str);
	}

	public static void main(String[] args) throws NotBoundException, IOException {
		// SETUP SELF
		// Register self on rmi network.
		print("SOCKET IMPLEMENTATION");
		
		String serverName = "SERVER";
		String serverIp = args[0];
		int serverPort = Integer.parseInt(args[1]);
		String clientName = args[2];
		int clientPort = Integer.parseInt(args[3]);
		String clientIp = InetAddress.getLocalHost().getHostAddress();

		print("Client Started.");
		print("Client Details." + "\nName: " + clientName + "\nPort: "
				+ clientPort);

		// JOIN PROCESS
		// Contact server. Get game object and send a join game request.
		Socket server = SocketFactory.connectSocket(
				InetAddress.getByName(serverIp), serverPort);
		GameClient client = new GameClient(server);
		client.setupSocketCommunication(server);
		client.sendToSocket("join" + " " + clientName + " " + clientIp + " "
				+ clientPort);
		String response = client.readSocket();
		
		// JOINED GAME. NOW PLAY
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
				if (option.equals("move") || option.equals("location")  || option.equals("elements") ) {
					print("ANALYSIS START: " + option);
					long start = getTime();
					client.sendToSocket(input);
					response = client.readSocket();
					long end = getTime();
					long diff = end - start;
					print("ANALYSIS END: " + option);
					print("Time Taken: " + diff);
				} else if (option.equals("quit")) {
					client.closeResources();
					print("Quit.");
					break;
				} else {
					print("Wrong option.");
				}
				print(response);
			} while (true);
		} else {
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
