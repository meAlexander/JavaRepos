package items;

public class Item {
	private String name;
	private int amount;
	private double price;
	
	public Item(String name, int amount, double price) {
		this.name = name;
		this.amount = amount;
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public int getAmount() {
		return amount;
	}
	
	public String toString() {
		return String.format("Name: %s, Amount: %d", this.getName(), this.getAmount());
	}
}