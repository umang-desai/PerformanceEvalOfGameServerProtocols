import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Random;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class GameServer {

	/**
	 * 
	 */
	private Zone zone;
	private ServerSocket serverSocket;
	private DataInputStream dis;
	private DataOutputStream dos;

	public GameServer() {
		zone = new Zone(10);
	}

	public GameServer(int dimension) {
		zone = new Zone(dimension);
	}

	public void setupServerSocket(int port) throws IOException {
		serverSocket = SocketFactory.createSocketServer(port);
	}

	public void closeResources() throws IOException {
		dis.close();
		dos.close();
		serverSocket.close();
	}

	public String readSocket() throws IOException {
		return dis.readUTF();
	}

	public void sendToSocket(String str) throws IOException {
		dos.writeUTF(str);
	}

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

	public String returnLocation() {
		String output = null;
		int[] pos = zone.getPlayer().getPosition();
		output = "LOCATION:" + pos[0] + "," + pos[1];
		return output;
	}

	public String returnObjects() {
		String output = null;
		Iterator<Zone.Element> iter = zone.getPlayer().getPickups().iterator();
		output = "OBJECTS:";
		output += "[";
		while (iter.hasNext()) {
			Zone.Element element = (Zone.Element) iter.next();
			output += element.name;
			if (iter.hasNext())
				output += ",";
		}
		output += "]";
		return output;
	}

	public String updateState(String state) {
		zone.movePlayer(state);
		int[] pos = zone.getPlayer().getPosition();
		String output = "LOCATION:" + pos[0] + "," + pos[1];
		return output;
	}

	public static void main(String[] args) throws IOException {
		print("SOCKET IMPLEMENTATION");
		
		long start, end, diff;

		int port = Integer.parseInt(args[0]);
		GameServer game = new GameServer();
		game.setupServerSocket(port);
		print("Started");
		Socket socket = game.serverSocket.accept();
		game.setupSocketCommunication(socket);
		while (true) {
			String request = game.readSocket();
			String response = null;
			// join, move, location, elements, quit.
			String[] split = request.split(" ");
			String command = split[0].toLowerCase();
			if (split.length > 1) {
				// move and join belongs here
				if (command.equals("move")) {
					response = game.updateState(request);
				} else if (command.equals("join")) {
					String clientName = split[1];
					String clientIp = split[2];
					int clientPort = Integer.parseInt(split[3]);
					response = game.join(clientName, clientIp, clientPort);
				}
			} else {
				if (command.equals("location")) {
					response = game.returnLocation();
				} else if (command.equals("elements")) {
					response = game.returnObjects();
				} else {
					response = "Wrong Option";
				}
				// location, elements, quit belong here. lenght 1 only.
			}
			game.sendToSocket(response);
		}
	}

	public static long getTime() {
		return System.currentTimeMillis();
	}

	private void setupSocketCommunication(Socket socket) throws IOException {
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	}

	public static int random(int upperBound) {
		Random r = new Random();
		return r.nextInt(upperBound);
	}

	public static void print(String str) {
		System.out.println("SERVER: " + str);
	}
}
