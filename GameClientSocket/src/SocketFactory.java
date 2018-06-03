import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketFactory {
	//Socket Creation methods
	public static ServerSocket createSocketServer(int port) throws IOException{
		ServerSocket server = new ServerSocket(port);
		return server;
	}
	
	public static Socket connectSocket(InetAddress ip, int port) throws IOException{
		Socket socket = new Socket(ip, port);
		return socket;
	}
}
