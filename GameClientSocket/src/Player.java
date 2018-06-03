import java.util.ArrayList;
import java.util.List;

public class Player {
	private int x, y;
	private String name;
	private List<Zone.Element> pickups;
	private String ip;
	private int port;

	public Player(int x, int y, String name, String ip, int port) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.pickups = new ArrayList<>();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}

	public List<Zone.Element> getPickups() {
		return pickups;
	}
	
	public void pickElement(Zone.Element element){
		pickups.add(element);
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void moveRight() {
		x++;
	}

	public void moveLeft() {
		x--;
	}

	public void moveUp() {
		y++;
	}

	public void moveDown() {
		y--;
	}

	public void moveRight(int steps) {
		for (int i = 0; i < steps; i++)
			x++;
	}

	public void moveLeft(int steps) {
		for (int i = 0; i < steps; i++)
			x--;
	}

	public void moveUp(int steps) {
		for (int i = 0; i < steps; i++)
			y++;
	}

	public void moveDown(int steps) {
		for (int i = 0; i < steps; i++)
			y--;
	}
	
	public int[] getPosition(){
		return new int[] {x,y};
	}
}
