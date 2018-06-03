
public class Zone {
	private int dimension;
	// Currently only supports one player.
	private Player player;

	public Zone(int dimension) {
		this.dimension = dimension;
	}

	public Player getPlayer() {
		return player;
	}
	
	public int getDimension(){
		return dimension;
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
