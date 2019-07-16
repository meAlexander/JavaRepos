package items;

public class DrinkItem extends Item {
	private int drinkID;
	private String brand;
	private int quantity;

	public DrinkItem(int drinkID, String name, int count, String brand, double price, int quantity) {
		super(name, count, price);
		this.drinkID = drinkID;
		this.brand = brand;
		this.quantity = quantity;
	}

	public DrinkItem(String name, String brand, double price, int quantity) {
		this(0, name, 0, brand, price, quantity);
	}

	public DrinkItem(int drinkID, int count) {
		this(drinkID, "NULL", count, "NULL", 0, 0);
	}

	public DrinkItem(String name, String brand, int quantity, int count, double price) {
		this(0, name, count, brand, price, quantity);
	}

	public DrinkItem(int drinkID) {
		this(drinkID, "NULL", 0, "NULL", 0, 0);
	}

	public String getBrand() {
		return brand;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getDrinkID() {
		return drinkID;
	}

	public String toString() {

		return String.format("Drink: %s, Brand: %s, Quantity: %d, Count: %d", this.getName(), this.brand, this.quantity,
				this.getCount());
	}
}