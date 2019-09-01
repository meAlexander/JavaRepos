package items;

public class PizzaItem extends Item {
	private int pizzaID;
	private String size;
	private String ingredients;

	public PizzaItem(int pizzaID, String name, int amount, String ingredients, String size, double price) {
		super(name, amount, price);
		this.pizzaID = pizzaID;
		this.size = size;
		this.ingredients = ingredients;
	}

	public PizzaItem(String name, String ingredients, String size, double price) {
		this(0, name, 0, ingredients, size, price);
	}

	public PizzaItem(int pizzaID, int amount) {
		this(pizzaID, "NULL", amount, "NULL", "NULL", 0);
	}

	public PizzaItem(String name, String size, int amount, double price) {
		this(0, name, amount, "NULL", size, price);
	}

	public PizzaItem(int pizzaID) {
		this(pizzaID, "NULL", 0, "NULL", "NULL", 0);
	}

	public String getSize() {
		return size;
	}

	public String getIngredients() {
		return ingredients;
	}

	public int getPizzaID() {
		return pizzaID;
	}

	public String toString() {

		return String.format("Pizza: %s, Size: %s, Count: %d", this.getName(), this.size, this.getAmount());
	}
}