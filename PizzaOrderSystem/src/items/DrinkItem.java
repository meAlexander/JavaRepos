package items;

public class DrinkItem extends Item{
	private String brand;
	private int quantity;
	
	public DrinkItem(String name, int count, String brand, double price, int quantity) {
		super(name, count, price);
		this.brand = brand;
		this.quantity = quantity;
	}
	
	public DrinkItem(String name, String brand, double price, int quantity) {
		this(name, 0, brand, price, quantity);
	}
	
	public DrinkItem(String name, int count, String brand) {
		this(name, count, brand, 0, 0);
	}
	
	public DrinkItem(String name, String brand) {
		this(name, 0, brand, 0, 0);
	}
	
	public String getBrand() {
		return brand;
	}

	public int getQuantity() {
		return quantity;
	}

	public String toString() {
		
		return String.format("Drink: %s, Brand: %s, Count: %d", this.getName(), this.brand, this.getCount());
	}
}