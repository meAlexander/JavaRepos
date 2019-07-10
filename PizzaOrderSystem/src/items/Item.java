package items;

public class Item {
	private String name;
	private int count;
	
	public Item(String name, int count) {
		this.name = name;
		this.count = count;
	}
	
	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}
	
	public String toString() {
		
		return String.format("Name: %s, Count: %d", this.getName(), this.getCount());
	}
}