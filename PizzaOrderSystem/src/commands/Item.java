package commands;

public class Item {
	
	private String name;
	private int count;
	
	public Item(String name, int count) {
		this.name = name;
		this.count = count;
	}
	
	public String toString() {
		
		return String.format("Pizza: %s, Count: %d", this.name, this.count);
	}
}