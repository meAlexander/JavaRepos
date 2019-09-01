package items;

public class DrinkItem extends Item {
	private int drinkID;
	private String brand;
	private String quantity;

	public DrinkItem(int drinkID, String name, int amount, String brand, double price, String quantity) {
		super(name, amount, price);
		this.drinkID = drinkID;
		this.brand = brand;
		this.quantity = quantity;
	}

	public DrinkItem(String name, String brand, double price, String quantity) {
		this(0, name, 0, brand, price, quantity);
	}

	public DrinkItem(int drinkID, int amount) {
		this(drinkID, "NULL", amount, "NULL", 0, "NULL");
	}

	public DrinkItem(String name, String brand, String quantity, int amount, double price) {
		this(0, name, amount, brand, price, quantity);
	}

	public DrinkItem(int drinkID) {
		this(drinkID, "NULL", 0, "NULL", 0, "NULL");
	}

	public String getBrand() {
		return brand;
	}

	public String getQuantity() {
		return quantity;
	}

	public int getDrinkID() {
		return drinkID;
	}

	public String toString() {
		return String.format("Drink: %s, Brand: %s, Quantity: %s, Amount: %d", this.getName(), this.getBrand(),
				this.getAmount(), this.getAmount());
	}
}