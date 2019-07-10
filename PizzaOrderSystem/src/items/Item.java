package items;

public class Item {
	private String name;
	private int count;
	private double price;
	
	public Item(String name, int count, double price) {
		this.name = name;
		this.count = count;
		this.price = price;
	}
	
	public double getPrice() {
		return price;
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