import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Zone {
	private int dimension;
	// Currently only supports one player.
	private Player player;
	// List of objects/elements to be found on the map.
	private List<Element> elements;

	public Zone(int dimension) {
		this.dimension = dimension;

		elements = new ArrayList<Zone.Element>();
		generateElements(dimension / 5);
	}

	/*
	 * private Element generateElement(){ String str = "name"+1;
	 * 
	 * }
	 */

	private void generateElements(int limit) {
		for (int i = 0; i < limit; i++) {
			int x = GameServer.random(dimension);
			int y = GameServer.random(dimension);
			String name = "Element" + x + "," + y;
			elements.add(new Element(name, x, y));
		}
	}

	private void pickElement() {
		int picked = 0;
		int[] pos = player.getPosition();
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			if (pos[0] == element.x && pos[1] == element.y) {
				player.pickElement(element);
				elements.remove(i);
				picked++;
			}
		}
		generateElements(picked);
	}

	public Player addNewPlayer(String name, String ip, int port) {
		int x, y;
		if (player == null) {
			x = y = (0 + dimension) / 2;
			player = new Player(x, y, name, ip, port);
			player.addElements(elements);
			return player;
		} else if (player.getName().equals(name) && player.getIp().equals(ip)
				&& player.getPort() == port) {
			return player;
		}
		return null;
	}

	public Player getPlayer() {
		return player;
	}

	public int getDimension() {
		return dimension;
	}

	public Iterator<Element> getListIter() {
		return elements.iterator();
	}

	// Front and Back is on Y-Axis.
	// Left and Right is on X-Axis
	public int[] movePlayer(String command) {
		String[] commands = command.split(" ");
		// move <UP/DOWN/LEFT/RIGHT> <steps>
		int steps = Integer.parseInt(commands[2]);
		int[] pos = player.getPosition();
		String direction = commands[1].toLowerCase();

		switch (direction) {
		case "right":
			if ((pos[0] + steps) > dimension)
				steps = dimension - pos[0];
			player.moveRight(steps);
			break;
		case "left":
			if ((pos[0] - steps < 0))
				steps = pos[0] - 0;
			player.moveLeft(steps);
			break;
		case "up":
			if ((pos[1] + steps > dimension))
				steps = dimension - pos[1];
			player.moveUp(steps);
			break;
		case "down":
			if ((pos[1] - steps < 0))
				steps = pos[1] - 0;
			player.moveDown(steps);
			break;
		default:
			break;
		}
		pickElement();
		return player.getPosition();
	}

	public class Element {
		String name;
		int x, y;

		public Element(String name, int x, int y) {
			this.name = name;
			this.x = x;
			this.y = y;
		}
	}

}
